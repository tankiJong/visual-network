## Visual Network
![network-showcase](https://github.com/tankiJong/visual-network/blob/master/network-showcase.png?raw=true)

*Random geometric Graph* (RGG) is a kind of network, constructed by randomly plac- ing N nodes in some metric space (according to a specified probability distribu- tion) and connecting two nodes by a link if and only if their distance is in a given range[1].

In this project, certain algorithms of some aspect about RGG are implemented:
* building RGG
* coloring nodes according some specific requirement
* finding the best backbone of the network.

The core computation and visualization are based on Java combined with the Processing library. In addition, Mathematica is utilized to render data plots. 

Through adding additional JVM flags `-Xmx4200m -Xms4200m -Xmn4g`, the program execution can be speedup.

The benchmarks indicates that the program cost linear time to do all works. Specifically, it cost about 20 seconds to execute when there are 128000 vertices and the average degree is 128.

### Show cases:
![statistic-showcase](https://github.com/tankiJong/visual-network/blob/master/statistic-showcase.png?raw=true)

left to right, top to bottom: backbone 1,2; node position visualization; node degree distribution; color class distribution; smallest last ordering process statistics - degree when deleted VS original degree

[1] Wikipedia. 2016. Random geometric graph — Wikipedia, The Free Encyclopedia. (2016). https:// en.wikipedia.org/w/index.php?title=Random geometric graph&oldid=729563640 [Online; accessed 14- March-2017].
