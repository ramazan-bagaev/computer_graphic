import java.util.ArrayList;

public class Main {

    public static void main(String[] argv){
        ArrayList<Point> points = PointFactory.createPoints(1000);
        Solver solver = new Solver();
        //for(Point point: points) System.out.println(point.toString());
        System.out.println("dumb solution");
        DumbSolver dumbSolver = new DumbSolver();
        Pair closest = dumbSolver.findClosest(points);
        System.out.println("First point: index = " + points.indexOf(closest.first) + "; " + closest.first.toString());
        System.out.println("Second point: index = " + points.indexOf(closest.second) + "; " + closest.second.toString());
        System.out.println("distance = " + closest.dist);
        System.out.println("number of distance computations = " + Point.counter);

        Point.renewCounter();

        System.out.println("");
        System.out.println("smart solution");
        closest = solver.findClosest(points);

        System.out.println("First point: index = " + points.indexOf(closest.first) + "; " + closest.first.toString());
        System.out.println("Second point: index = " + points.indexOf(closest.second) + "; " + closest.second.toString());
        System.out.println("distance = " + closest.dist);
        System.out.println("number of distance computations = " + Point.counter);

    }
}
