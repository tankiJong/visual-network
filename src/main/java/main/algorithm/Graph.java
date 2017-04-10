package main.algorithm;

import main.Config;
import main.render.Canvas;
import main.render.Drawable;
import main.render.Executable;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tanki on 2017/2/25.
 */
public class Graph implements Drawable {
    private final boolean renderable;
    final Executable renderTarget;
    Node minDegNode = null;
    Node maxDegNode = null;
    int E;

    static public void main(String[] passedArgs) {
        boolean open = false;
        assert open = true;
        System.out.println(open);
        long startMili = System.currentTimeMillis();
        Graph graph = new Graph(Config.NODE_AMOUNT, SquareNode.class);
        long endMili = System.currentTimeMillis();
        System.out.println("total: " + (endMili - startMili) / 1000f);
    }

    Graph() {
        this.renderable = false;
        renderTarget = e -> {
        };
    }

    ArrayList<Node> nodes;

    public Graph(int size, Class<? extends Node> type, Executable... renderTarget) {
        this.renderable = renderTarget.length != 0;
        this.renderTarget = renderTarget.length == 0 ? e -> {
        } : renderTarget[0];
        this.nodes = new ArrayList<Node>(size);
        Hashtable<String, ArrayList<Node>> cells = new Hashtable<>(Config.UNIT_AMOUNT * Config.UNIT_AMOUNT);
        for (int i = 0; i < size; i++) {
            try {
                Node node = type.newInstance();
                ArrayList<Node> cell = cells.computeIfAbsent(node.coordinate.getCellIndex(),
                        k -> new ArrayList<>(Config.AVG_DEGREE * 2));
                cell.add(node);
                this.renderTarget.push(c -> {
                    node.renderOn(c, 0x906060);
                });
                nodes.add(node);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        this.renderTarget.push(c -> {
            c.save("node.jpg");
        });
        this.rebuildEdges(cells);
    }

    private void rebuildEdges(Hashtable<String, ArrayList<Node>> cells) {
//        int i = 0;
        for (Node node : nodes) {
            node.connect(cells, renderTarget);
//            this.renderTarget.push(node);
//            int j = i++;
//            this.renderTarget.push(c -> {
//                c.save("./render/node-with-edge-"+j+".jpg");
//            });
        }
        for (ArrayList<Node> cell : cells.values()) {
            for (Node node : cell) {
                cell.forEach(n -> {
                    if (!n.equals(node)) {
                        node.getConnected().add(n);
                    }
                });
            }
        }
        for (Node node : nodes) {
            int size = node.getConnected().size();
            E += size;
            if (maxDegNode == null) {
                maxDegNode = node;
                continue;
            }
            if (minDegNode == null) {
                minDegNode = node;
                continue;
            }
            if (size > maxDegNode.getConnected().size()) {
                maxDegNode = node;
            }
            if (size < minDegNode.getConnected().size()) {
                minDegNode = node;
            }
        }
        E /= 2;
        System.out.println("edge connect finished");
//        this.renderTarget.push(this);
        this.renderTarget.push(canvas -> {
            Config.NODE_SIZE *= 2;
            Coordinate c = this.maxDegNode.coordinate;
            int color = 0xcd1212;
            int r = (color & 0xFF0000) >> 16, g = (color & 0x00FF00) >> 8, b = (color & 0x0000FF);
            canvas.noFill();
            canvas.stroke(r, g, b);
            float halfX = Config.X / 2, halfY = Config.Y / 2;
            canvas.ellipse(c.getScreenX(), c.getScreenY(),
                    Config.LEGAL_DISTANCE * Config.X, Config.LEGAL_DISTANCE * Config.Y);

            this.maxDegNode.renderOn(canvas, 0xcd1212);

            c = this.minDegNode.coordinate;
            color = 0x0e0eea;
            r = (color & 0xFF0000) >> 16;
            g = (color & 0x00FF00) >> 8;
            b = (color & 0x0000FF);
            canvas.noFill();
            canvas.stroke(r, g, b);
            canvas.ellipse(c.getScreenX(), c.getScreenY(),
                    Config.LEGAL_DISTANCE * Config.X, Config.LEGAL_DISTANCE * Config.Y);
            this.minDegNode.renderOn(canvas, 0x0e0eea);
            Config.NODE_SIZE /= 2;
        });
        this.renderTarget.push(c -> {
            c.save("node-with-edge.jpg");
        });
    }

    @Override
    public void renderOn(Canvas canvas) {
        this.nodes.forEach(e -> {
            e.renderOn(canvas);
        });
    }

    public void renderGraph(Canvas canvas, boolean withNode) {
        for (Node node : nodes) {
            if (withNode) {
                node.renderOn(canvas, 0xefefef);
            }
            for (Node connected : node.getConnected()) node.renderEdgeTo(canvas, connected, 250);
        }
    }
}
