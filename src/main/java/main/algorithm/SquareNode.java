package main.algorithm;

import main.Config;
import main.render.Canvas;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tanki on 2017/3/2.
 */
public class SquareNode extends Node {
    public static class Cartesian extends Coordinate {
        public float x;
        public float y;

        static final String cellFormat = "%d, %d";
        static class Iterator extends CellIterator {
            final int x;
            final int y;

            int idx = 0;

            // put self at the end to make sure at least one iteration
            int[] dx = {-1, 1, 0, 1, 0};
            int[] dy = {-1, 0, 1, 1, 0};

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
                int x = this.x + dx[idx],
                        y = this.y + dy[idx++];
                while (x >= MAX_X || y >= MAX_Y || x < 0 || y < 0) {
                    x = this.x + dx[idx];
                    y = this.y + dy[idx++];
                }
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
            this.x = ThreadLocalRandom.current().nextFloat();
            this.y = ThreadLocalRandom.current().nextFloat();
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
    public void renderOn(Canvas canvas) {
        canvas.noStroke();
        canvas.fill(colors[0]);
        Cartesian c = (Cartesian) coordinate;
        canvas.ellipse(c.x * Config.X + Config.CANVAS_MARGIN, c.y * Config.Y + Config.CANVAS_MARGIN, Config.NODE_SIZE, Config.NODE_SIZE);
    }

    @Override
    public void renderEdgeTo(Canvas canvas, Node other) {
        Cartesian a = (Cartesian) coordinate, o = (Cartesian) ((SquareNode) other).coordinate;
        canvas.line(a.x * Config.X + Config.CANVAS_MARGIN, a.y * Config.Y + Config.CANVAS_MARGIN, o.x * Config.X + Config.CANVAS_MARGIN, o.y * Config.Y + Config.CANVAS_MARGIN);
    }
}
