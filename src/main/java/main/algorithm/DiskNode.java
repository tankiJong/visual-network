package main.algorithm;

import main.Config;
import main.render.Canvas;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.PI;

/**
 * Created by tanki on 2017/3/2.
 */
public class DiskNode extends Node{
    public class Polar2 extends Coordinate{
        public float theta;
        public float rho;

        public float getX(){
            return (float) (this.rho* Math.cos(this.theta));
        }

        public float getY(){
            return (float) (this.rho* Math.sin(this.theta));
        }
        @Override
        public void randomlyInit() {
            float doubleR = Config.R * Config.R;
            this.rho = (float) Math.sqrt(ThreadLocalRandom.current().nextFloat()*doubleR);
            this.theta = (float) (ThreadLocalRandom.current().nextFloat()*2*PI);
        }
        @Override
        public int compareTo(Coordinate o) {
            Polar2 oo = (Polar2) o;
            if(oo.theta != this.theta) return (int) (this.theta - oo.theta);
            if(oo.rho != this.rho) return (int) (this.rho - oo.rho);
            return 0;
        }
    }
    public DiskNode(){
        this.coordinate = new Polar2();
    }

    @Override
    public int compareTo(Node o) {
        return this.coordinate.compareTo(o.coordinate);
    }

    @Override
    public void renderOn(Canvas canvas) {
        canvas.noStroke();
        canvas.fill(100);
        Polar2 c = (Polar2)coordinate;
        System.out.println(c.getX() + "  " + c.getY());
        float halfX = Config.X/2, halfY = Config.Y/2;
        canvas.ellipse(c.getX() + halfX, c.getY() + halfY, Config.NODE_SIZE, Config.NODE_SIZE);
    }
}
