package main.algorithm;

import main.Config;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Created by tanki on 2017/2/25.
 */
public class Node implements Comparable<Node>{
    public final int x;
    public final int y;
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Node(){
        this.x = ThreadLocalRandom.current().nextInt(0, Config.X + 1);
        this.y = ThreadLocalRandom.current().nextInt(0, Config.Y + 1);
    }

    @Override
    public int compareTo(Node o) {
        if(this.x != o.x) return this.x - o.x;
        if(this.y != o.y) return this.y - o.y;
        return 0;
    }
}
