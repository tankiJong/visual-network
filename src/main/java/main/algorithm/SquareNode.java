package main.algorithm;

import main.Config;
import main.render.Canvas;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tanki on 2017/3/2.
 */
public class SquareNode extends Node {
    public static class Cartesian extends Coordinate {
        float getScreenX() {
            return getX() * Config.R + Config.CANVAS_MARGIN;
        }

        @Override
        float getScreenY() {
            return getY() * Config.R + Config.CANVAS_MARGIN;
        }

        @Override
        public String toString() {
            return "Cartesian{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        static final String cellFormat = "%d, %d";
        static class Iterator extends CellIterator {
            final int x;
            final int y;

            int idx = 0;

            // put self at the end to make sure at least one iteration
            int[] dx = {1, 1, 0, 1};
            int[] dy = {-1, 0, 1, 1};

            public Iterator(float x, float y) {
                this.x = (int) Math.floor(x / Config.LEGAL_DISTANCE);
                this.y = (int) Math.floor(y / Config.LEGAL_DISTANCE);
            }

            @Override
            public boolean hasNext() {
                return idx < dx.length;
            }

            @Override
            public String next() {
                int x = this.x + dx[idx], y = this.y + dy[idx++];
//                while (x >= MAX_X || y >= MAX_Y || x < 0 || y < 0) {
//                    x = this.x + dx[idx];
//                    y = this.y + dy[idx++];
//                }
                return String.format(cellFormat, x, y);
            }
        }

        @Override
        CellIterator getCellIterator() {
            return new Iterator(x, y);
        }

        @Override
        public String getCellIndex() {
            return String.format("%d, %d",
                    (int) Math.floor(x / Config.LEGAL_DISTANCE),
                    (int) Math.floor(y / Config.LEGAL_DISTANCE));
        }

        @Override
        public void randomlyInit() {
            setX(ThreadLocalRandom.current().nextFloat());
            ;
            setY(ThreadLocalRandom.current().nextFloat());
        }

        @Override
        public double SquaredDistance(Coordinate a, Coordinate b) {
            Cartesian m = (Cartesian) a, n = (Cartesian) b;
            return Coordinate.eularSquareDistance(m.x, m.y, n.x, n.y);
        }

        @Override
        public int compareTo(Coordinate o) {
            Cartesian oo = (Cartesian) o;
            if (oo.x != this.x) return (this.x - oo.x > 0) ? 1 : -1;
            if (oo.y != this.y) return (this.y - oo.y > 0) ? 1 : -1;
            return 0;
        }
    }

    public SquareNode() {
        this.coordinate = new Cartesian();
    }

    @Override
    public int compareTo(Node o) {
        return this.coordinate.compareTo(o.coordinate);
    }

    @Override
    void renderOn(Canvas canvas, int color) {
        canvas.noStroke();
        int r = (color & 0xFF0000) >> 16, g = (color & 0x00FF00) >> 8, b = (color & 0x0000FF);
        canvas.fill(r, g, b);
        Coordinate c = coordinate;
        float halfX = Config.X / 2, halfY = Config.Y / 2;
        canvas.ellipse(c.getScreenX(), c.getScreenY(), Config.NODE_SIZE, Config.NODE_SIZE);
    }

    @Override
    void renderEdgeTo(Canvas canvas, Node other, int color) {
        canvas.stroke(color);
        Coordinate a = coordinate, o = other.coordinate;
        canvas.line(a.getScreenX(),
                a.getScreenY(),
                o.getScreenX(),
                o.getScreenY());
    }
}
