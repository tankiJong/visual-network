/**
 * Created by tanki on 2017/2/23.
 */
package main.render;

import main.Config;
import main.algorithm.ColorGraph;
import main.algorithm.SquareNode;
import processing.core.PApplet;

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
            long startMili = System.currentTimeMillis();
            ColorGraph graph = new ColorGraph(Config.NODE_AMOUNT, SquareNode.class, this);
            int total = Math.min(graph.total, 4);
            for (int i = 0; i < total; i++) {
                graph.renderAccordingTo(i);
            }
            for (int i = 0; i < total; i++) {
                for (int j = i; j < total; j++) {
                    if (j == i) continue;
                    graph.renderBipartiteOf(i, j);
                }
            }
            long endMili = System.currentTimeMillis();
            System.out.println("total: " + (endMili - startMili) / 1000f);
            this.push(c -> {
                System.exit(0);
            });
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
        for (int i = 0; i < 1000 && !this.queue.isEmpty(); i++) {
            Drawable d = this.queue.poll();
            d.renderOn(this);
        }
    }

    @Override
    public void mouseClicked() {
        System.out.println(String.format("cell index: %d, %d",
                (int) Math.floor((mouseX * Config.LEGAL_DISTANCE)),
                (int) Math.floor((mouseY * Config.LEGAL_DISTANCE))));
        super.mouseClicked();
    }

    @Override
    public void push(Drawable task) {
        this.queue.add(task);
    }

    @Override
    public void save(String filename) {
        super.save("./render/" + filename);
    }
}
