import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Sorter {

    public static Point[] sortByX(ArrayList<Point> points){
        Point[] sorted = new Point[points.size()];

        for(int i = 0; i < points.size(); i++) sorted[i] = points.get(i);
        Arrays.sort(sorted, Comparator.comparingDouble(point -> point.x));

        return sorted;
    }

    public static Point[] sortByY(ArrayList<Point> points){
        Point[] sorted = new Point[points.size()];

        for(int i = 0; i < points.size(); i++) sorted[i] = points.get(i);
        Arrays.sort(sorted, Comparator.comparingDouble(point -> point.y));

        return sorted;
    }
}
