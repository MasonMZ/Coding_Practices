/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class KdTree {
    private Node root;
    private int size;


    private static class Node {
        private Point2D point;
        private Node left, right;
        private Node parent;
        private boolean isKeyX;
        private RectHV box;

        public Node(Point2D p, Boolean preKeyIsX, Node pre, RectHV rect) {
            point = p;
            isKeyX = !preKeyIsX;
            parent = pre;
            box = rect;
        }
    }

    /* construct an empty set of points **/
    public KdTree() {
        root = null;
        size = 0;
    }

    /* is the set empty?  **/
    public boolean isEmpty() {
        return size == 0;
    }

    /* number of points in the set  **/
    public int size() {
        return size;
    }

    /* add the point to the set (if it is not already in the set)  **/
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("The point to be inserted can NOT be null!");
        }
        root = put(root, p, null);
        size += 1;
    }

    private Node put(Node n, Point2D p, Node pre) {
        if (n == null) {
            if (root == null) {
                RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
                return new Node(p, false, null, rect);
            }
            else {
                RectHV box;
                if (pre.isKeyX) {
                    if (p.x() < pre.point.x()) {
                        box = new RectHV(pre.box.xmin(), pre.box.ymin(), pre.point.x(),
                                         pre.box.ymax());
                    }
                    else {
                        box = new RectHV(pre.point.x(), pre.box.ymin(), pre.box.xmax(),
                                         pre.box.ymax());
                    }
                }
                else {
                    if (p.y() < pre.point.y()) {
                        box = new RectHV(pre.box.xmin(), pre.box.ymin(), pre.box.xmax(),
                                         pre.point.y());
                    }
                    else {
                        box = new RectHV(pre.box.xmin(), pre.point.y(), pre.box.xmax(),
                                         pre.box.ymax());
                    }
                }
                return new Node(p, pre.isKeyX, pre, box);
            }
        }
        else if (n.isKeyX) {
            if (p.x() < n.point.x()) {
                n.left = put(n.left, p, n);
            }
            else if (p.x() > n.point.x()) {
                n.right = put(n.right, p, n);
            }
            else {
                if (p.y() != n.point.y()) {
                    n.right = put(n.right, p, n);
                }
            }
        }
        else {
            if (p.y() < n.point.y()) {
                n.left = put(n.left, p, n);
            }
            else if (p.y() > n.point.y()) {
                n.right = put(n.right, p, n);
            }
            else if (p.x() != n.point.x()) {
                n.right = put(n.right, p, n);
            }
        }
        return n;
    }

    /* does the set contain point p?  **/
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("The point to be examined can NOT be null!");
        }
        Node curNode = root;
        while (curNode != null) {
            if (curNode.point.equals(p)) {
                return true;
            }
            if ((curNode.isKeyX && p.x() < curNode.point.x()) || (!curNode.isKeyX
                    && p.y() < curNode.point.y())) {
                curNode = curNode.left;
            }
            if ((curNode.isKeyX && p.x() >= curNode.point.x()) || (!curNode.isKeyX
                    && p.y() >= curNode.point.y())) {
                curNode = curNode.right;
            }
        }
        return false;
    }

    /* draw all points to standard draw  **/
    public void draw() {
        drawOut(root);
    }

    private void drawOut(Node n) {
        if (n == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(n.point.x(), n.point.y());
        if (n.isKeyX) {
            if (n.parent == null) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(n.point.x(), 0.0, n.point.x(), 1.0);
            }
            else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(n.point.x(), n.box.ymin(), n.point.x(), n.box.ymax());
            }
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(n.box.xmin(), n.point.y(), n.box.xmax(), n.point.y());
        }
        drawOut(n.left);
        drawOut(n.right);
    }

    /* all points that are inside the rectangle (or on the boundary)  **/
    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> res = new TreeSet<>();
        if (rect == null) {
            throw new IllegalArgumentException("The rectangle can NOT be null!");
        }
        if (root == null) {
            return null;
        }
        recRange(rect, root, res);
        return res;
    }

    private void recRange(RectHV rect, Node n, TreeSet<Point2D> res) {
        if (n == null) {
            return;
        }
        if (rect.contains(n.point)) {
            res.add(n.point);
            recRange(rect, n.left, res);
            recRange(rect, n.right, res);
        }
        if (n.isKeyX) {
            if (rect.xmax() < n.point.x()) {
                recRange(rect, n.left, res);
            }
            else if (rect.xmin() > n.point.x()) {
                recRange(rect, n.right, res);
            }
            else {
                recRange(rect, n.left, res);
                recRange(rect, n.right, res);
            }
        }
        else {
            if (rect.ymax() < n.point.y()) {
                recRange(rect, n.left, res);
            }
            else if (rect.ymin() > n.point.y()) {
                recRange(rect, n.right, res);
            }
            else {
                recRange(rect, n.left, res);
                recRange(rect, n.right, res);
            }
        }
    }

    /* a nearest neighbor in the set to point p; null if the set is empty  **/
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("The given point can NOT be null!");
        }
        if (root == null) {
            return null;
        }
        return (nearestPoint(root, p));
    }

    private Point2D nearestPoint(Node n, Point2D p) {
        double distance = n.point.distanceTo(p);
        if (n.left == null && n.right == null) {
            return n.point;
        }
        else if (n.left == null) {
            Point2D rightCandidate = nearestPoint(n.right, p);
            double shortestRightDistance = shortest(rightCandidate, p);
            if (distance < shortestRightDistance) {
                return n.point;
            }
            else {
                return rightCandidate;
            }
        }
        else if (n.right == null) {
            Point2D leftCandidate = nearestPoint(n.left, p);
            double shortestLeftDistance = shortest(leftCandidate, p);
            if (distance < shortestLeftDistance) {
                return n.point;
            }
            else {
                return leftCandidate;
            }
        }
        else if (n.isKeyX) {
            if (p.x() < n.point.x()) {
                if (n.left.point.distanceTo(p) < n.right.box.distanceTo(p)) {
                    return nearestPoint(n.left, p);
                }
                else {
                    Point2D leftCandidate = nearestPoint(n.left, p);
                    double shortestLeftDistance = shortest(leftCandidate, p);
                    Point2D rightCandidate = nearestPoint(n.right, p);
                    double shortestRightDistance = shortest(rightCandidate, p);
                    if (distance <= shortestLeftDistance && distance <= shortestRightDistance) {
                        return n.point;
                    }
                    else if (shortestLeftDistance < shortestRightDistance) {
                        return leftCandidate;
                    }
                    else {
                        return rightCandidate;
                    }
                }
            }
            else {
                if (n.right.point.distanceTo(p) < n.left.box.distanceTo(p)) {
                    return nearestPoint(n.right, p);
                }
                else {
                    Point2D leftCandidate = nearestPoint(n.left, p);
                    double shortestLeftDistance = shortest(leftCandidate, p);
                    Point2D rightCandidate = nearestPoint(n.right, p);
                    double shortestRightDistance = shortest(rightCandidate, p);
                    if (distance <= shortestLeftDistance && distance <= shortestRightDistance) {
                        return n.point;
                    }
                    else if (shortestLeftDistance < shortestRightDistance) {
                        return leftCandidate;
                    }
                    else {
                        return rightCandidate;
                    }
                }

            }

        }
        else {
            if (p.y() < n.point.y()) {
                if (n.left.point.distanceTo(p) < n.right.box.distanceTo(p)) {
                    return nearestPoint(n.left, p);
                }
                else {
                    Point2D leftCandidate = nearestPoint(n.left, p);
                    double shortestLeftDistance = shortest(leftCandidate, p);
                    Point2D rightCandidate = nearestPoint(n.right, p);
                    double shortestRightDistance = shortest(rightCandidate, p);
                    if (distance <= shortestLeftDistance && distance <= shortestRightDistance) {
                        return n.point;
                    }
                    else if (shortestLeftDistance < shortestRightDistance) {
                        return leftCandidate;
                    }
                    else {
                        return rightCandidate;
                    }
                }
            }
            else {
                if (n.right.point.distanceTo(p) < n.left.box.distanceTo(p)) {
                    return nearestPoint(n.right, p);
                }
                else {
                    Point2D leftCandidate = nearestPoint(n.left, p);
                    double shortestLeftDistance = shortest(leftCandidate, p);
                    Point2D rightCandidate = nearestPoint(n.right, p);
                    double shortestRightDistance = shortest(rightCandidate, p);
                    if (distance <= shortestLeftDistance && distance <= shortestRightDistance) {
                        return n.point;
                    }
                    else if (shortestLeftDistance < shortestRightDistance) {
                        return leftCandidate;
                    }
                    else {
                        return rightCandidate;
                    }
                }
            }
        }
    }

    private double shortest(Point2D n, Point2D p) {
        if (n == null) {
            return Double.POSITIVE_INFINITY;
        }
        else {
            return n.distanceTo(p);
        }
    }

    public static void main(String[] args) {
        KdTree t1 = new KdTree();
        t1.insert(new Point2D(0.7, 0.2));
        t1.insert(new Point2D(0.5, 0.4));
        t1.insert(new Point2D(0.2, 0.3));
        t1.insert(new Point2D(0.4, 0.7));
        t1.insert(new Point2D(0.9, 0.6));
        // t1.draw();
        // System.out.println(t1.size);
        // Iterable<Point2D> x1 = t1.range(new RectHV(0.4, 0.0, 0.8, 0.5));
        // for (Point2D p : x1) {
        //     System.out.println(p);
        // }
        System.out.println(t1.nearest(new Point2D(0.3, 0.4)));
    }
}
