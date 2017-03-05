/**
 * Created by tanki on 2017/2/23.
 */
package main.render;

import main.Config;
import main.algorithm.DiskNode;
import main.algorithm.Graph;
import main.algorithm.SphereNode;
import main.algorithm.SquareNode;
import processing.core.PApplet;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Canvas extends PApplet implements Executable
{
    public LinkedBlockingQueue<Drawable> queue;
    /* main method of sketch */
    static public void main(String[] passedArgs)
    {
        boolean open =false;
        assert  open = true;
        System.out.println(open);
        String[] appletArgs = new String[]{"main.render.Canvas"};
        if (passedArgs != null)
        {
            PApplet.main(concat(appletArgs, passedArgs));
        }
        else
        {
            PApplet.main(appletArgs);
        }
    }

    public Canvas(){
        this.queue = new LinkedBlockingQueue<>();
    }

    /* sketch initial setup */
    public void setup()
    {
        background(255);
        frameRate(60);
        new Thread(()->{
            Graph graph = new Graph(Config.NODE_AMOUNT, SphereNode.class, Canvas.this);
        }).start();

    }

    @Override
    public void settings() {
        size(Config.X + 2*Config.CANVAS_MARGIN, Config.Y+ 2*Config.CANVAS_MARGIN);
    }

    /* put something on the stage */
    public void draw()
    {
        //this.updateCamera();
        if(this.queue.isEmpty()) return;
        for (int i = 0; i < 100 && !this.queue.isEmpty(); i++) {
            Drawable d = this.queue.poll();
            d.renderOn(this);
        }
    }

    @Override
    public void push(Drawable task) {
        this.queue.add(task);
    }
}
