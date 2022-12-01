/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;

public class KdTree {


    private class kdBST {
        private class Node {
            private Point2D point;
            private Node left, right;
            private boolean isKeyX;

            public Node(Point2D p, Boolean preKeyIsX) {
                point = p;
                isKeyX = !preKeyIsX;
            }
        }

        private Node root;
    }


    public static void main(String[] args) {

    }
}
