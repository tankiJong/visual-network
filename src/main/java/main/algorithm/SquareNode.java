package main.algorithm;

import main.Config;
import main.render.Canvas;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tanki on 2017/3/2.
 */
public class SquareNode extends Node{
    public class Cartesian extends Coordinate{
        public int x;
        public int y;
        @Override
        public void randomlyInit() {
            this.x = ThreadLocalRandom.current().nextInt(0, Config.X + 1);
            this.y = ThreadLocalRandom.current().nextInt(0, Config.Y + 1);
        }
        @Override
        public int compareTo(Coordinate o) {
            Cartesian oo = (Cartesian) o;
            if(oo.x != this.x) return this.x - oo.x;
            if(oo.y != this.y) return this.y - oo.y;
            return 0;
        }
    }
    public SquareNode(){
        this.coordinate = new Cartesian();
    }

    @Override
    public int compareTo(Node o) {
        return this.coordinate.compareTo(o.coordinate);
    }

    @Override
    public void renderOn(Canvas canvas) {
        canvas.noStroke();
        canvas.fill(100);
        Cartesian c = (Cartesian)coordinate;
        canvas.ellipse(c.x, c.y, Config.NODE_SIZE, Config.NODE_SIZE);
    }
}
