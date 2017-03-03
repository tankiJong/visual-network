package main.algorithm;

import main.Config;
import main.render.Canvas;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

/**
 * Created by tanki on 2017/3/2.
 */
public class SphereNode extends Node {
    public class Cartesian3 extends Coordinate{
        public float x;
        public float y;
        public float z;

        // TODO: http://mathworld.wolfram.com/SpherePointPicking.html
        @Override
        public void randomlyInit() {
            double u = ThreadLocalRandom.current().nextFloat()*2 - 1;
            double theta = ThreadLocalRandom.current().nextFloat()*2*PI;
            this.x = (float) (sqrt(1-u*u)*cos(theta))*Config.X/2f;
            this.y = (float) (sqrt(1-u*u)*sin(theta))*Config.Y/2f;
            this.z = (float) u*Config.Z;
        }
        @Override
        public int compareTo(Coordinate o) {
            Cartesian3 oo = (Cartesian3) o;
            if(oo.x != this.x) return (int) (this.x - oo.x);
            if(oo.y != this.y) return (int) (this.y - oo.y);
            if(oo.z != this.z) return (int) (this.z - oo.z);
            return 0;
        }
    }
    public SphereNode(){
        this.coordinate = new SphereNode.Cartesian3();
    }

    @Override
    public int compareTo(Node o) {
        return this.coordinate.compareTo(o.coordinate);
    }

    @Override
    public void renderOn(Canvas canvas) {
        canvas.noStroke();
        canvas.fill(100);
        Cartesian3 c = (Cartesian3) coordinate;
        System.out.println(c.x + "  " + c.y);
        float halfX = Config.X/2, halfY = Config.Y/2;
        canvas.ellipse(c.x+halfX, c.y+halfY, Config.NODE_SIZE, Config.NODE_SIZE);
    }
}
