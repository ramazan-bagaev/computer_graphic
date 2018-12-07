import java.util.ArrayList;

public class Solver {

    private ArrayList<Point> points;
    private Point[] sortedX;

    private ArrayList<Point> leftSide;
    private ArrayList<Point> rightSide;

    private ArrayList<Point> temp;

    public Pair findClosest(ArrayList<Point> points) {
        this.points = points;
        leftSide = new ArrayList<>();
        rightSide = new ArrayList<>();
        temp = new ArrayList<>();
        sortedX = Sorter.sortByX(points);
        return getClosest(0, points.size() - 1);
    }

    private Pair getClosest(int from, int to){
        if (to - from == 1){
            Point a = sortedX[from];
            Point b = sortedX[to];
            return new Pair(a, b, Point.distance(a, b));
        }
        int middle = (to + from)/2;
        Pair left = getClosest(from, middle);
        Pair right = getClosest(middle, to);
        double delta = Math.min(Math.min(left.dist, right.dist), Pair.minDist);
        int leftEdge = findLeftEdge(middle, delta);
        int rightEdge = findRightEdge(middle, delta);
        Pair middlePair = getClosestInBetween(leftEdge, middle, rightEdge, delta);
        if (middlePair.dist < delta) return middlePair;
        if (left.dist > right.dist) return right;
        return left;
    }

    private int findRightEdge(int middle, double delta){
        int index = middle;
        double center = sortedX[middle].x;
        while (true){
            if (sortedX[index].x - center > delta) return index - 1;
            if (index == sortedX.length - 1) return index;
            index++;
        }
    }

    private int findLeftEdge(int middle, double delta){
        int index = middle;
        double center = sortedX[middle].x;
        while (true){
            if (sortedX[index].x - center > delta) return index - 1;
            if (index == 0) return index;
            index--;
        }
    }


    private Pair getClosestInBetween(int left, int middle, int right, double delta){
        leftSide.clear();
        rightSide.clear();
        for(int i = left; i <= middle; i++){
            leftSide.add(sortedX[i]);
        }
        for(int i = middle + 1; i <= right; i++){
            rightSide.add(sortedX[i]);
        }
        Pair res = new Pair(null, null, Double.MAX_VALUE);
        for(Point point: leftSide){
            initForComp(point.y, delta);
            for(Point p: temp){
                if (point == p) continue;
                double dist = Point.distance(point, p);
                if (res.dist > dist){
                    res.first = point;
                    res.second = p;
                    res.dist = dist;
                }
            }
        }
        return res;
    }

    private void initForComp(double y, double delta){
        temp.clear();
        for(Point point: rightSide){
            if (Math.abs(point.y - y) < delta) temp.add(point);
        }
    }
}
