package app.noiseviewerjfx.utilities;

public class Math {

    public static int maptoInt(float val, float minA, float maxA, int minB, int maxB) {
        return (int) ((1 - ((val - minA) / (maxA - minA))) * minB + ((val - minA) / (maxA - minA)) * maxB);
    }

    public static float euclideanDistance(float x1, float y1, float x2, float y2) {
        return (float) java.lang.Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    /**
     * Calculates the shortest distance between a point and the edge of a circle
     * @param x (float): x-coordinates of the point
     * @param y (float): y-coordinates of the point
     * @param h (float): x-coordinates of the center of the circle
     * @param i (float): y-coordinates of the center of the circle
     * @param r (float): circle radius
     * @return (float): shortest distance to the edge of the circle
     * @implNote <a href="https://www.varsitytutors.com/hotmath/hotmath_help/topics/shortest-distance-between-a-point-and-a-circle">
     *     click for more info concerning the formula being used</a>
     */
    public static float distanceToCircle(float x, float y, float h, float i, float r) {
        return (float) java.lang.Math.abs(java.lang.Math.sqrt((x - h) * (x - h) + (y - i) * (y - i)) - r);
    }

}
