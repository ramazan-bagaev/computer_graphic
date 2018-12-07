import java.util.ArrayList;

public class DumbSolver {

    private ArrayList<Point> points;

    public Pair findClosest(ArrayList<Point> points) {
        this.points = points;
        return getClosest();
    }

    private Pair getClosest(){
        Pair pair = new Pair(null, null, Double.MAX_VALUE);
        for(Point point: points){
            for(Point point1: points){
                if (point == point1) continue;
                double dist = Point.distance(point, point1);
                if (dist < pair.dist){
                    pair.first = point;
                    pair.second = point1;
                    pair.dist = dist;
                }
            }
        }
        return pair;
    }
}
