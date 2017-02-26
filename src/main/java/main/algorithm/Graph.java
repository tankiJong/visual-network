package main.algorithm;

import main.Config;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by tanki on 2017/2/25.
 */
public class Graph {
    private Graph(){}
    private HashSet<Node> nodes;
    static public void main(String[] args){
        new Graph(100);
    }
    public Graph(int size){
        this.nodes = new HashSet<>(size);
        ArrayList<Node> nodeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Node node = new Node();
            nodes.add(node);
            nodeList.add(node);
        }
        this.rebuildEdges(nodeList);
    }

    private void rebuildEdges(List<Node> nodeList) {
        BiFunction<Node, Node, Integer> cellDivider= (Node a, Node b) ->{  // divide into cell
            int dx = a.x - b.x;
            int dy = a.y - b.y;
            boolean $x = dx < Config.MAX_DISTANCE && dx > -Config.MAX_DISTANCE;
            boolean $y = dy < Config.MAX_DISTANCE && dy > -Config.MAX_DISTANCE;
            if($x && $y) return 0;
            if($x || $y) return 1;
            return -1;
        };
        nodeList.sort(cellDivider::apply);
        nodeList.stream().reduce((Node pre, Node now) -> {
            
           return now;
        });
    }
}
