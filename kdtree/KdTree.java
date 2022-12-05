/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

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
        root = put(root, p, null, null);
        size += 1;
    }

    private Node put(Node n, Point2D p, Node pre, RectHV box) {
        if (n == null) {
            if (root == null) {
                RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
                return new Node(p, false, null, rect);
            }
            else {
                return new Node(p, pre.isKeyX, pre, box);
            }
        }
        if (n.isKeyX) {
            if (p.x() < n.point.x()) {
                RectHV rect = new RectHV(pre.box.xmin(), pre.box.ymin(), pre.point.x(),
                                         pre.box.ymax());
                put(n.left, p, n, rect);
            }
            else if (p.x() > n.point.x()) {
                RectHV rect = new RectHV(pre.point.x(), pre.box.ymin(), pre.box.xmax(),
                                         pre.box.ymax());
                put(n.right, p, n, rect);
            }
            else {
                if (p.y() != n.point.y()) {
                    RectHV rect = new RectHV(pre.point.x(), pre.box.ymin(), pre.box.xmax(),
                                             pre.box.ymax());
                    put(n.right, p, n, rect);
                }
            }
        }
        else {
            if (p.y() < n.point.y()) {
                RectHV rect = new RectHV(pre.box.xmin(), pre.box.ymin(), pre.box.xmax(),
                                         pre.point.y());
                put(n.left, p, n, rect);
            }
            else if (p.y() > n.point.y()) {
                RectHV rect = new RectHV(pre.box.xmin(), pre.point.y(), pre.box.xmax(),
                                         pre.box.ymax());
                put(n.right, p, n, rect);
            }
            else if (p.x() != n.point.x()) {
                RectHV rect = new RectHV(pre.box.xmin(), pre.point.y(), pre.box.xmax(),
                                         pre.box.ymax());
                put(n.right, p, n, rect);
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

    public static void main(String[] args) {

    }
}
