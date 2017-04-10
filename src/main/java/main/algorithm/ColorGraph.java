package main.algorithm;

import main.Config;
import main.render.Executable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by tanki on 2017/3/6.
 */
public class ColorGraph extends Graph {
    public final int total;
    int maxColor;
    int maxDegWhenDeleted;

    public ColorGraph(int size, Class<? extends Node> type, Executable... renderTarget) {
        super(size, type, renderTarget);
        this.total = coloring(smallestOrdering(nodes));
        System.out.println("color finished");
//        this.renderTarget.push(this);
//        this.renderTarget.push(c -> {
//            c.save("node-colored.jpg");
//        });
    }

    static public void main(String[] passedArgs) {
        boolean open = false;
        assert open = true;
        System.out.println(open);
        long startMili = System.currentTimeMillis();
        ColorGraph graph = new ColorGraph(Config.NODE_AMOUNT, SquareNode.class);
        int total = Math.min(graph.total, 4);
        for (int i = 0; i < total; i++) {
            graph.renderAccordingTo(i);
        }
        for (int i = 0; i < total; i++) {
            for (int j = i; j < total; j++) {
                if (j == i) continue;
                graph.renderBipartiteOf(i, j);
            }
        }
        long endMili = System.currentTimeMillis();
        System.out.println("total: " + (endMili - startMili) / 1000f);
        graph.exportDistribution(Config.exportPath + "distribution.csv");
        graph.exportStatistic(Config.exportPath + "statistics.txt");
    }

    private ColorGraph() {
        super();
        this.total = -1;
    }

    public void exportDistribution(String nameWithPath) {
        try {
            FileWriter fw = new FileWriter(nameWithPath);
            fw.write("id,degree,color, deleteIndex, degreeWhenRemoved\n");
            for (Node node : nodes) {
                fw.write(String.format("%d,%d,%d,%d,%d\n", node.getInnerIndex(), node.getConnected().size(), node.getColorIndex(), Config.NODE_AMOUNT - node.getColoringIndex() - 1, node.degreeWhenRemoved));
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportStatistic(String nameWithPath) {
        try {
            FileWriter fw = new FileWriter(nameWithPath);
            fw.write("n,r,e,mindeg,maxdeg,degdeleted,color, moscolorsiz,clique\n");
            fw.write(Config.NODE_AMOUNT + ",");
            fw.write(Config.LEGAL_DISTANCE + ",");
            fw.write(E + ",");
            fw.write(minDegNode.getConnected().size() + ",");
            fw.write(maxDegNode.getConnected().size() + ",");
            fw.write(",");
            fw.write(maxColor + ",,");
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void renderAccordingTo(int i) {
        this.renderTarget.push(c -> {
            c.clear();
            c.background(255);
        });
        for (Node node : nodes) {
            if (node.getColorIndex() != i) continue;
            this.renderTarget.push(node);
        }
        String name = "color-" + i + ".jpg";
        this.renderTarget.push(c -> {
            c.save(name);
        });
    }

    public void renderBipartiteOf(int i, int j) {
        System.out.println("bipartite-" + i + "-" + j);
        this.renderTarget.push(c -> {
            c.clear();
            c.background(255);
//            this.renderGraph(c, false);
        });
        int V = 0;
        int E = 0;
        for (Node node : nodes) {
            if (node.getColorIndex() != i) continue;
            V++;
            this.renderTarget.push(node);
            for (Node connected : node.getConnected()) {
                if (connected.getColorIndex() == i) {
                    int xx = 1;
                }
                assert connected.getColorIndex() != i;
                assert !connected.equals(node);
                if (connected.getColorIndex() != j) continue;
                this.renderTarget.push(connected);
                this.renderTarget.push(c -> {
                    node.renderEdgeTo(c, connected);
                });
                E++;
            }
        }
        E = E / 2;
        String name = "bipartite-" + i + "-" + j + " V-" + V + "E-" + E + ".jpg";
        this.renderTarget.push(c -> {
            c.save(name);
        });
    }

    public int coloring(Node[] nodes) {
//        for (Node n : nodes) {
//            this.renderTarget.push(c -> {
//                n.renderOn(c, 0xEEEEEE);
//            });
//        }
        int maxColor = 1;
        for (int idx = nodes.length - 1; idx >= 0; idx--) {
            Node now = nodes[idx];
            int[] usedColor = new int[maxColor];
            for (Node connected : now.getConnected()) {
                int color = connected.getColorIndex();
                if (color != -1) {
                    usedColor[color] = 1;
                }
            }
            for (int i = 0; i < maxColor; i++) {
                if (usedColor[i] == 0) {
                    now.setColorIndex(i);
                    break;
                }
            }
            if (now.getColorIndex() == -1) {
                now.setColorIndex(maxColor++);
            }
//            for (Node node: nodes)
//                for(Node c: node.getConnected())
//                    if(node.getColorIndex() == c.getColorIndex() && node.getColorIndex() != -1){
//                        int ser =12;
//                    }
//            for (int i = 0; i < 100; i++) {
//                this.renderTarget.push(now);
//            }
        }
        System.out.println("total colors: " + maxColor);
        this.maxColor = maxColor;
        return maxColor;
    }

    public Node[] smallestOrdering(ArrayList<Node> nodes) {
        Node[] wrappers = new Node[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            wrappers[i] = nodes.get(i);
        }
        Arrays.sort(wrappers, Comparator.comparingInt(a -> a.getConnected().size()));
        int[] degs = new int[wrappers.length];
        for (int i = 0; i < wrappers.length; i++) {
            degs[i] = wrappers[i].getConnected().size();
            wrappers[i].setColoringIndex(i);
        }
        int q = 0, end = wrappers.length;
        while (q < end) {
            Node now = wrappers[q];
            now.degreeWhenRemoved = degs[q];
            if (degs[q] > maxDegWhenDeleted) maxDegWhenDeleted = degs[q];
            assert q == now.coloringIndex : q + " " + now.coloringIndex;
            ArrayList<Node> connected = now.getConnected();
//            renderTarget.push(c -> {
//                now.renderOn(c, 0xFFFFFF);
//            });
            int i = q + 1;
            if (i == end) break;
            // all connected degree -1,
            for (Node adjcent : connected) {
                i = adjcent.getColoringIndex();
                if (i < q) continue;

                // TODO: in sequence: ... x, x+i ..., which i>=0, only when i = 0. we need swap.(x<=x+i && x+i-1 < x) That means we need to swap it with the value x with smallest index.
                int j = i - 1;
                if (degs[i] == degs[j]) {
//                    j--;
                    while (j > q) {
                        if (degs[j] != degs[i]) {
                            break;
                        }
                        j--;
                    }

                    degs[i]--;
                    int t = degs[j + 1];
                    degs[j + 1] = degs[i];
                    degs[i] = t;

                    wrappers[j + 1].setColoringIndex(i);
                    wrappers[i].setColoringIndex(j + 1);
                    Node tt = wrappers[j + 1];
                    wrappers[j + 1] = wrappers[i];
                    wrappers[i] = tt;
                } else {
                    degs[i]--;
                }

//                renderTarget.push(c -> {
//                    now.renderEdgeTo(c, adjcent, 255);
//                });
            }
//            for (int j = q + 1; j < end - 1; j++) {
//                assert degs[j] <= degs[j + 1] : "q: " + q + ", " + j + ": " + degs[j] + ", " + (j + 1) + ": " + degs[j + 1];
//            }
            q++;
        }

        // and wrappers is now sorted sequence
        return wrappers;
    }

}
