/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] lineSegList;
    private int segmentNumber = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        lineSegList = new LineSegment[points.length / 2];
        for (int i = 0; i < points.length; i += 1) {
            if (points[i] == null) {
                throw new IllegalArgumentException("The point should NOT be null!");
            }
            Point[] orderedPointList = makeSlopeInOrder(points, i);
            for (int p = 0; p < orderedPointList.length - 2; p += 1) {
                if (orderedPointList[p].slopeTo(points[i]) == Double.NEGATIVE_INFINITY
                        || orderedPointList[orderedPointList.length - 2].slopeTo(points[i])
                        == Double.NEGATIVE_INFINITY
                        || orderedPointList[orderedPointList.length - 1].slopeTo(points[i])
                        == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException(
                            "There should NOT have two identical points!");
                }
                if (orderedPointList[p].slopeTo(points[i]) == orderedPointList[p + 1].slopeTo(
                        points[i])
                        && orderedPointList[p + 2].slopeTo(points[i]) == orderedPointList[p
                        + 1].slopeTo(points[i])) {
                    if (p + 3 >= orderedPointList.length
                            || orderedPointList[p + 3].slopeTo(points[i])
                            != orderedPointList[p].slopeTo(points[i])) {
                        Point[] candidatePoints = new Point[] {
                                points[i], orderedPointList[p], orderedPointList[p + 1],
                                orderedPointList[p + 2]
                        };
                        LineSegment temp = lsGenerator(candidatePoints);
                        boolean flag = true;
                        for (LineSegment ls : this.segments()) {
                            if (ls.toString().equals(temp.toString())) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            lineSegList[segmentNumber] = temp;
                            segmentNumber += 1;
                            p += 2;
                        }
                    }
                    else {
                        int numOfPoints = 3;
                        for (int k = 3; k < orderedPointList.length - p; k += 1) {
                            if (orderedPointList[p + k].slopeTo(points[i])
                                    != orderedPointList[p].slopeTo(points[i])) {
                                numOfPoints += 1;
                            }
                            else {
                                break;
                            }
                        }
                        Point[] candidatePoints = new Point[numOfPoints];
                        candidatePoints[0] = points[i];
                        for (int n = 1; n <= numOfPoints; n += 1) {
                            candidatePoints[n] = orderedPointList[p + n - 1];
                        }
                        LineSegment temp = lsGenerator(candidatePoints);
                        boolean flag = true;
                        for (LineSegment ls : this.segments()) {
                            if (ls.toString().equals(temp.toString())) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            lineSegList[segmentNumber] = temp;
                            segmentNumber += 1;
                            p += numOfPoints - 1;
                        }
                    }
                }
            }
        }
    }

    private Point[] makeSlopeInOrder(Point[] pointList, int index) {
        Point[] points = new Point[pointList.length - 1];
        System.arraycopy(pointList, 0, points, 0, index);
        if (index < pointList.length - 1) {
            System.arraycopy(pointList, index + 1, points, index, pointList.length - index - 1);
        }
        Comparator<Point> c = pointList[index].slopeOrder();
        Arrays.sort(points, c);
        return points;
    }

    private LineSegment lsGenerator(Point[] a) {
        Point max = a[0];
        Point min = a[0];
        for (int i = 1; i < a.length; i += 1) {
            if (a[i].compareTo(max) > 0) {
                max = a[i];
            }
            if (a[i].compareTo(min) < 0) {
                min = a[i];
            }
        }
        return new LineSegment(min, max);
    }


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
        // FastCollinearPoints collinear = new FastCollinearPoints(inputPoints);
        // collinear.segments();


    }
}
