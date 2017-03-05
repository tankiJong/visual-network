package main.algorithm;

import main.Config;
import main.render.Canvas;
import main.render.Drawable;
import main.render.Executable;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tanki on 2017/2/25.
 */
public abstract class Node implements Comparable<Node>, Drawable {
    Coordinate coordinate;
    int[] colors = {100};
    final int EdgeColor = 200;

    public HashSet<Node> getConnected() {
        return connected;
    }

    private HashSet<Node> connected = new HashSet<>(Config.AVG_DEGREE * 2);

    public abstract void renderOn(Canvas canvas);

    public abstract void renderEdgeTo(Canvas canvas, Node other);

    public void connect(Hashtable<String, HashSet<Node>> cells, Executable target) {
        Coordinate.CellIterator itr = coordinate.getCellIterator();
        String idx = itr.next();
        while (itr.hasNext()) {
            HashSet<Node> nodes = cells.get(idx);
            if(nodes == null) return;
            assert nodes != null;
            nodes.forEach(node -> {
                if (coordinate.SquaredDistance(node.coordinate, coordinate) > Config.LEGAL_SQUARE_DISTANCE)
                    connected.add(node);
                node.connected.add(this);
                if (Config.WILL_DRAW) {
                    target.push(c -> {
                        c.stroke(EdgeColor);
                        renderEdgeTo(c, node);
                    });
                }
            });
            idx = itr.next();
        }
    }
}
