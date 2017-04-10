package main.algorithm;

import main.Config;
import main.render.Canvas;
import main.render.Drawable;
import main.render.Executable;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tanki on 2017/2/25.
 */
public abstract class Node implements Comparable<Node>, Drawable {
    Coordinate coordinate;
    private static int innerIndex = 0;

    public int getInnerIndex() {
        return _index;
    }

    private int _index = innerIndex++;
    static int[] colors;
    static {
        int color = 255, total = 200, step = 255 / total;
        colors = new int[total];
        for (int i = 0; i < total; i++) {
            colors[i] = ThreadLocalRandom.current().nextInt(0x30, 0xde)
                    + (ThreadLocalRandom.current().nextInt(0x30, 0xde) << 8)
                    + (ThreadLocalRandom.current().nextInt(0x30, 0xde) << 16);
        }
    }

    int coloringIndex;
    public int degreeWhenRemoved;
    public int getColoringIndex() {
        return coloringIndex;
    }

    public void setColoringIndex(int coloringIndex) {
        this.coloringIndex = coloringIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public int getColor() {
        if (this.colorIndex == -1) return 0x909090;
        return colors[colorIndex];
    }

    int colorIndex = -1;
    final int EdgeColor = 210;

    public ArrayList<Node> getConnected() {
        return connected;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;
        return this._index == ((Node) obj)._index;
    }

    private ArrayList<Node> connected = new ArrayList<>(Config.AVG_DEGREE * 2);

    public void renderOn(Canvas canvas) {
        renderOn(canvas, getColor());
    }

    ;

    abstract void renderOn(Canvas canvas, int color);
    abstract void renderEdgeTo(Canvas canvas, Node other, int color);

    public void renderEdgeTo(Canvas canvas, Node other) {
        renderEdgeTo(canvas, other, EdgeColor);
    }

    // TODO: Contains operation of the Set Data Structure spends too much time
    public void connect(Hashtable<String, ArrayList<Node>> cells, Executable target) {
        Coordinate.CellIterator itr = coordinate.getCellIterator();
        while (itr.hasNext()) {
            String idx = itr.next();
            ArrayList<Node> nodes = cells.get(idx);
            if (nodes == null) continue;
            for (Node node : nodes) {
                if (coordinate.SquaredDistance(node.coordinate, coordinate) > Config.LEGAL_SQUARE_DISTANCE || node.equals(this)) {
//                    System.out.println(coordinate.toString() + " "
//                            + node.coordinate.toString() + "" + coordinate.SquaredDistance(node.coordinate, coordinate));
                    continue;
                }
                connected.add(node);
                node.getConnected().add(this);
//                if (Config.WILL_DRAW) {
//                    target.push(c -> {
//                        renderEdgeTo(c, node);
//                    });
//                }
            }
        }
    }
}
