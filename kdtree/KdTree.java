/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;

public class KdTree {
    private Node root;
    private int size;


    private static class Node {
        private Point2D point;
        private Node left, right;
        private Node parent;
        private boolean isKeyX;

        public Node(Point2D p, Boolean preKeyIsX, Node pre) {
            point = p;
            isKeyX = !preKeyIsX;
            parent = pre;
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
        root = put(root, p, null);
    }

    private Node put(Node n, Point2D p, Node pre) {
        if (n == null) {
            if (root == null) {
                return new Node(p, false, null);
            }
            else {
                return new Node(p, pre.isKeyX, pre);
            }
        }
        if (n.isKeyX) {
            if (p.x() < n.point.x()) {
                put(n.left, p, n);
            }
            else if (p.x() > n.point.x()) {
                put(n.right, p, n);
            }
            else {
                if (p.y() != n.point.y()) {
                    put(n.right, p, n);
                }
            }
        }
        else {
            if (p.y() < n.point.y()) {
                put(n.left, p, n);
            }
            else if (p.y() > n.point.y()) {
                put(n.right, p, n);
            }
            else if (p.x() != n.point.x()) {
                put(n.right, p, n);
            }
        }
        return n;
    }


    public static void main(String[] args) {

    }
}
