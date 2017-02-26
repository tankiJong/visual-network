/**
 * Created by tanki on 2017/2/23.
 */
package main.render;

import processing.core.PApplet;

public class Canvas extends PApplet
{
    /* main method of sketch */
    static public void main(String[] passedArgs)
    {

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

    /* sketch initial setup */
    public void setup()
    {
        background(0);
    }

    @Override
    public void settings() {
        size(300, 200);
    }

    /* put something on the stage */
    public void draw()
    {
        // Draw gray box

        int d = 20;
        int p1 = d;
        int p2 = p1 + d;
        int p3 = p2 + d;
        int p4 = p3 + d;

        stroke(153);
        line(p3, p3, p2, p3);
        line(p2, p3, p2, p2);
        line(p2, p2, p3, p2);
        line(p3, p2, p3, p3);
    }
}
