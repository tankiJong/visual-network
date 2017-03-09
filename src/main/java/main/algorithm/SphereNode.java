package main.algorithm;

import main.Config;
import main.render.Canvas;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

/**
 * Created by tanki on 2017/3/2.
 */
public class SphereNode extends Node {
    public static class Cartesian3 extends Coordinate {
        public float x;
        public float y;
        public float z;

        static final String cellFormat = "%d, %d, %d";

        @Override
        CellIterator getCellIterator() {
            return new Iterator(x, y, z);
        }

        static class Iterator extends CellIterator {
            final int x;
            final int y;
            final int z;
            int idx = 0;

            // put self at the end to make sure at least one iteration
            int[] dx = {1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0};
            int[] dy = {1, 1, 1, 1, 0, 0, 0, -1, -1, -1, 0};
            int[] dz = {1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0};

            public Iterator(float x, float y, float z) {
                assert x + 0.5 <= 1 && x + 0.5 >= 0;
                assert y + 0.5 <= 1 && x + 0.5 >= 0;
                assert z + 0.5 <= 1 && z + 0.5 >= 0;
                this.x = (int) Math.floor((x + 0.5) / Config.LEGAL_DISTANCE);
                this.y = (int) Math.floor((y + 0.5) / Config.LEGAL_DISTANCE);
                this.z = (int) Math.floor((z + 0.5) / Config.LEGAL_DISTANCE);
            }

            @Override
            public boolean hasNext() {
                return idx < dx.length;
            }

            @Override
            public String next() {
                int x = this.x + dx[idx],
                        y = this.y + dy[idx],
                        z = this.z + dz[idx++];
                while (x >= MAX_X || y >= MAX_Y || z >= MAX_Z || x < 0 || y < 0 || z < 0) {
                    x = this.x + dx[idx];
                    y = this.y + dy[idx];
                    z = this.z + dz[idx++];
                }
                return String.format(cellFormat, x, y, z);
            }
        }

        @Override
        public String getCellIndex() {
            return String.format(cellFormat,
                    (int) Math.floor((x + 0.5) / Config.LEGAL_DISTANCE),
                    (int) Math.floor((y + 0.5) / Config.LEGAL_DISTANCE),
                    (int) Math.floor((z + 0.5) / Config.LEGAL_DISTANCE));
        }

        // TODO: http://mathworld.wolfram.com/SpherePointPicking.html
        @Override
        public void randomlyInit() {
            double u = ThreadLocalRandom.current().nextFloat() * 2 - 1;
            double theta = ThreadLocalRandom.current().nextFloat() * 2 * PI;
            this.x = (float) (sqrt(1 - u * u) * cos(theta)) / 2;
            this.y = (float) (sqrt(1 - u * u) * sin(theta)) / 2;
            this.z = (float) u / 2;
        }

        @Override
        public double SquaredDistance(Coordinate a, Coordinate b) {
            Cartesian3 m = (Cartesian3) a, n = (Cartesian3) b;
            return Coordinate.eularSquareDistance(m.x, m.y, m.z, n.x, n.y, n.z);
        }

        @Override
        public int compareTo(Coordinate o) {
            Cartesian3 oo = (Cartesian3) o;
            if (oo.x != this.x) return (int) (this.x - oo.x);
            if (oo.y != this.y) return (int) (this.y - oo.y);
            if (oo.z != this.z) return (int) (this.z - oo.z);
            return 0;
        }
    }

    public SphereNode() {
        this.coordinate = new SphereNode.Cartesian3();
    }

    @Override
    public int compareTo(Node o) {
        return this.coordinate.compareTo(o.coordinate);
    }

    @Override
    public void renderOn(Canvas canvas, int color) {
        canvas.noStroke();
        Cartesian3 c = (Cartesian3) coordinate;
        int r = (color & 0xFF0000) >> 16, g = (color & 0x00FF00) >> 8, b = (color & 0x0000FF);
        canvas.fill(r, g, b);
        float halfX = Config.X / 2, halfY = Config.Y / 2;
        canvas.ellipse(c.x * Config.X + halfX + +Config.CANVAS_MARGIN, c.y * Config.Y + halfY + Config.CANVAS_MARGIN, Config.NODE_SIZE, Config.NODE_SIZE);
    }

    @Override
    void renderEdgeTo(Canvas canvas, Node other, int color) {
        canvas.stroke(color);
        Cartesian3 a = (Cartesian3) coordinate, o = (Cartesian3) ((SphereNode) other).coordinate;
        float halfX = Config.X / 2, halfY = Config.Y / 2;
        canvas.line(a.x * Config.R + halfX + Config.CANVAS_MARGIN,
                a.y * Config.R + halfY + Config.CANVAS_MARGIN,
                o.x * Config.R + halfX + Config.CANVAS_MARGIN,
                o.y * Config.R + halfY + Config.CANVAS_MARGIN);
    }
}
