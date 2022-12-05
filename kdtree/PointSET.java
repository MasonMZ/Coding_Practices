/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> ptSet;

    /* construct an empty set of points **/
    public PointSET() {
        ptSet = new TreeSet<>();
    }

    /* is the set empty?  **/
    public boolean isEmpty() {
        return (ptSet.size() == 0);
    }

    /* number of points in the set  **/
    public int size() {
        return ptSet.size();
    }

    /* add the point to the set (if it is not already in the set)  **/
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("The point to be inserted can NOT be null!");
        }
        if (ptSet.contains(p)) {
            return;
        }
        ptSet.add(p);
    }

    /* does the set contain point p?  **/
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("The point to be examined can NOT be null!");
        }
        return ptSet.contains(p);
    }

    /* draw all points to standard draw  **/
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        for (Point2D p : ptSet) {
            StdDraw.point(p.x(), p.y());
        }
    }

    /* all points that are inside the rectangle (or on the boundary)  **/
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("The rectangle can NOT be null!");
        }
        TreeSet<Point2D> res = new TreeSet<>();
        for (Point2D p : ptSet) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin()
                    && p.y() <= rect.ymax()) {
                res.add(p);
            }
        }
        return res;
    }

    /* a nearest neighbor in the set to point p; null if the set is empty  **/
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("The given point can NOT be null!");
        }
        if (ptSet.isEmpty()) {
            return null;
        }
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D pointCandidate = p;
        for (Point2D q : ptSet) {
            if (p.equals(q)) {
                continue;
            }
            if (p.distanceSquaredTo(q) < minDistance) {
                minDistance = p.distanceSquaredTo(q);
                pointCandidate = q;
            }
        }
        return pointCandidate;

    }


    public static void main(String[] args) {

    }
}
