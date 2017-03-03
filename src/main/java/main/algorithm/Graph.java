package main.algorithm;

import main.Config;
import main.render.Canvas;
import main.render.Drawable;
import processing.core.PApplet;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by tanki on 2017/2/25.
 */
public class Graph implements Drawable{
    private Graph(){}
    private HashSet<Node> nodes;
    public Graph(int size){
        this.nodes = new HashSet<>(size);
        ArrayList<Node> nodeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Node node = new SquareNode();
            nodes.add(node);
            nodeList.add(node);
        }
        //this.rebuildEdges(nodeList);
    }
    public Graph(int size, Class<? extends Node> type){
        this.nodes = new HashSet<>(size);
        ArrayList<Node> nodeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Node node = null;
            try {
                node = type.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            nodes.add(node);
            nodeList.add(node);
        }
        //this.rebuildEdges(nodeList);
    }
//    private void rebuildEdges(List<Node> nodeList) {
//        BiFunction<Node, Node, Integer> cellDivider = (Node a, Node b) -> {  // divide into cell
//            int dx = a.x - b.x;
//            int dy = a.y - b.y;
//            boolean $x = dx < Config.MAX_DISTANCE && dx > -Config.MAX_DISTANCE;
//            boolean $y = dy < Config.MAX_DISTANCE && dy > -Config.MAX_DISTANCE;
//            if($x && $y) return 0;
//            if($x || $y) return 1;
//            return -1;
//        };
//        nodeList.sort(cellDivider::apply);
//        nodeList.stream().reduce((Node pre, Node now) -> {
//            if(cellDivider.apply(pre, now)!=0) this.addEdge(pre, now);
//            return now;
//        });
//    }
    private void addEdge(Node from, Node to){

    }

    @Override
    public void renderOn(Canvas canvas) {
        this.nodes.forEach(e -> {
            canvas.queue.add(e);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
    }
}
