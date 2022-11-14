/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class FastCollinearPoints {

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        for (int i = 0; i < points.length; i += 1) {
            Point[] orderedPointList = makeSlopeInOrder(points, i);
        }

    }

    private Point[] makeSlopeInOrder(Point[] pointList, int index) {
        Point[] inputPoints = new Point[pointList.length - 1];
        System.arraycopy(pointList, 0, inputPoints, 0, index - 1);
        System.arraycopy(pointList, index + 1, inputPoints, index, pointList.length - index - 1);
        Arrays.sort()
    }

    public static void main(String[] args) {

    }
}
