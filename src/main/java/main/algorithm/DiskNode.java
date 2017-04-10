package main.algorithm;

import main.Config;
import main.render.Canvas;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.PI;

/**
 * Created by tanki on 2017/3/2.
 */
public class DiskNode extends Node {
    public static class Polar2 extends Coordinate {
        @Override
        public String toString() {
            return "Polar2{" +
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
                assert x + 0.5 <=1 && x + 0.5 >= 0;
                assert y + 0.5 <=1 && x + 0.5 >= 0;
                this.x = (int) Math.floor((x+0.5) / Config.LEGAL_DISTANCE);
                this.y = (int) Math.floor((y+0.5) / Config.LEGAL_DISTANCE);
            }

            @Override
            public boolean hasNext() {
                return idx < dx.length;
            }

            @Override
            public String next() {
                int x = this.x + dx[idx],
                        y = this.y + dy[idx++];
//                while (x >= MAX_X || y >= MAX_Y || x < 0 || y < 0) {
//                    x = this.x + dx[idx];
//                    y = this.y + dy[idx++];
//                }
                return String.format(cellFormat, x, y);
            }
        }

        @Override
        float getScreenX() {
            return getX() * Config.R + Config.X / 2 + Config.CANVAS_MARGIN;
        }

        @Override
        float getScreenY() {
            return getY() * Config.R + Config.Y / 2 + Config.CANVAS_MARGIN;
        }

        @Override
        CellIterator getCellIterator() {
            return new Iterator(x, y);
        }

        @Override
        public String getCellIndex() {
            return String.format("%d, %d",
                    (int) Math.floor((x+0.5) / Config.LEGAL_DISTANCE),
                    (int) Math.floor((y+0.5) / Config.LEGAL_DISTANCE));
        }

        @Override
        public void randomlyInit() {
            float rho = (float) Math.sqrt(ThreadLocalRandom.current().nextFloat()),
                    theta = (float) (ThreadLocalRandom.current().nextFloat() * 2 * PI);
            setX((float) (rho * Math.cos(theta)) / 2);
            setY((float) (rho * Math.sin(theta)) / 2);
        }

        @Override
        public double SquaredDistance(Coordinate a, Coordinate b) {
            Polar2 m = (Polar2) a, n = (Polar2) b;
            return Coordinate.eularSquareDistance(m.x, m.y, n.x, n.y);
        }

        @Override
        public int compareTo(Coordinate o) {
            Polar2 oo = (Polar2) o;
            if (oo.x != this.x) return (int) (this.x - oo.x);
            if (oo.y != this.y) return (int) (this.y - oo.y);
            return 0;
        }
    }

    public DiskNode() {
        this.coordinate = new Polar2();
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
