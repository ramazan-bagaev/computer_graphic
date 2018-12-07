public class Pair {

    public Point first;
    public Point second;
    public double dist;

    public static double minDist = Double.MAX_VALUE;

    public Pair(Point first, Point second, double dist){
        this.first = first;
        this.second = second;
        this.dist = dist;
        if (minDist > dist) minDist = dist;
    }

    public static void renewMinDist() {
        minDist = Double.MAX_VALUE;
    }
}
