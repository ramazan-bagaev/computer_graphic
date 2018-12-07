import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PointFactory {

    public static ArrayList<Point> createPoints(int number){
        ArrayList<Point> points = new ArrayList<>(number);
        for(int i = 0; i < number; i++) points.add(createPoint());
        return points;
    }

    public static Point createPoint(){
        return new Point(ThreadLocalRandom.current().nextDouble(), ThreadLocalRandom.current().nextDouble());
    }
}
