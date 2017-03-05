package main.algorithm;

import main.Config;
import main.render.Canvas;
import main.render.Drawable;

import main.render.Executable;
import java.util.*;

/**
 * Created by tanki on 2017/2/25.
 */
public class Graph implements Drawable{
    private final boolean renderable;
    private final Executable renderTarget;

    private Graph(){
        this.renderable = false;
        renderTarget = e -> {};
    }
    private HashSet<Node> nodes;

    public Graph(int size, Class<? extends Node> type, Executable... renderTarget){
        this.renderable = renderTarget.length != 0;
        this.renderTarget = renderTarget.length == 0 ? e -> {} : renderTarget[0];
        this.nodes = new HashSet<>(size);
        Hashtable<String, HashSet<Node>> cells = new Hashtable<>(Config.UNIT_AMOUNT * Config.UNIT_AMOUNT * Config.UNIT_AMOUNT);
        for (int i = 0; i < size; i++) {
            Node node = null;
            try {
                node = type.newInstance();
                HashSet<Node> cell = cells.computeIfAbsent(node.coordinate.getCellIndex(),
                        k -> new HashSet(Config.AVG_DEGREE * 2));
                cell.add(node);
                this.renderTarget.push(node);
                nodes.add(node);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            assert node != null;
        }
        this.renderTarget.push(c -> {
            c.save("node.jpg");
        });
        this.rebuildEdges(cells);
    }

    private void rebuildEdges(Hashtable<String, HashSet<Node>> cells) {
        nodes.forEach(node -> {
            node.connect(cells, renderTarget);
        });
        System.out.println("edge connect finished");
        this.renderTarget.push(this);
        this.renderTarget.push(c -> {
            c.save("node-with-edge.jpg");
        });
    }

    @Override
    public void renderOn(Canvas canvas) {
        this.nodes.forEach(e -> {
            canvas.queue.add(e);
        });
    }
}
