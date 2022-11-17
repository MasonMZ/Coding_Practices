/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class BruteCollinearPoints {
    private LineSegment[] lineSegList;
    private int segmentNumber = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        lineSegList = new LineSegment[points.length];
        // if (points.length < 4) {
        //     throw new IllegalArgumentException("The number of points should be larger than 4!");
        // }
        // if (points == null) {
        //     throw new IllegalArgumentException("The input points should NOT be null!");
        // }
        for (int i = 0; i < points.length; i += 1) {
            if (points[i] == null) {
                throw new IllegalArgumentException("The point should NOT be null!");
            }
            for (int j = i + 1; j < points.length; j += 1) {
                if (points[j] == null) {
                    throw new IllegalArgumentException("The point should NOT be null!");
                }
                if (points[j].slopeTo(points[i]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException(
                            "There should NOT have two identical points!");
                }
                for (int p = j + 1; p < points.length; p += 1) {
                    if (points[p] == null) {
                        throw new IllegalArgumentException("The point should NOT be null!");
                    }
                    if (points[p].slopeTo(points[i]) == Double.NEGATIVE_INFINITY
                            || points[p].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException(
                                "There should NOT have two identical points!");
                    }
                    if (points[p].slopeTo(points[i]) == points[j].slopeTo(points[i])) {
                        for (int q = p + 1; q < points.length; q += 1) {
                            if (points[q] == null) {
                                throw new IllegalArgumentException("The point should NOT be null!");
                            }
                            if (points[q].slopeTo(points[i]) == Double.NEGATIVE_INFINITY
                                    || points[q].slopeTo(points[j]) == Double.NEGATIVE_INFINITY
                                    || points[q].slopeTo(points[p]) == Double.NEGATIVE_INFINITY) {
                                throw new IllegalArgumentException(
                                        "There should NOT have two identical points!");
                            }
                            if (points[q].slopeTo(points[i]) == points[j].slopeTo(points[i])) {
                                lineSegList[segmentNumber] = lsGenerator(points[i], points[j],
                                                                         points[p], points[q]);
                                segmentNumber += 1;
                            }
                        }
                    }
                }
            }

        }
    }

    // generate a line segment containing 4 collinear points
    private LineSegment lsGenerator(Point a, Point b, Point c, Point d) {
        Point[] pList = new Point[] { a, b, c, d };
        Point max = a;
        Point min = a;
        for (int i = 1; i < 4; i += 1) {
            if (pList[i].compareTo(max) > 0) {
                max = pList[i];
            }
            if (pList[i].compareTo(min) < 0) {
                min = pList[i];
            }
        }
        return new LineSegment(max, min);
    }


    // the number of line segments
    public int numberOfSegments() {
        return segmentNumber;
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segmentNumber];
        System.arraycopy(lineSegList, 0, result, 0, segmentNumber);
        return result;
    }

    public static void main(String[] args) {
        // Point p1 = new Point(10000, 0);
        // Point p2 = new Point(0, 10000);
        // Point p3 = new Point(3000, 7000);
        // Point p4 = new Point(7000, 3000);
        // Point p5 = new Point(20000, 21000);
        // Point p6 = new Point(3000, 4000);
        // Point p7 = new Point(14000, 15000);
        // Point p8 = new Point(6000, 7000);
        // Point[] inputPoints = new Point[] { p1, p2, p3, p4, p5, p6, p7, p8 };
        // BruteCollinearPoints collinear = new BruteCollinearPoints(inputPoints);


    }
}
