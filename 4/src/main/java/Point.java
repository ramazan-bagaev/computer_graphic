public class Point {

    public double x;
    public double y;

    public static int counter;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static double distance(Point a, Point b){
        counter++;
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static void renewCounter(){
        counter = 0;
    }
}
