package app.noiseviewerjfx.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ComplementaryMath {

    public static int mapToInt(float val, float minA, float maxA, int minB, int maxB) {
        return (int) ((1 - ((val - minA) / (maxA - minA))) * minB + ((val - minA) / (maxA - minA)) * maxB);
    }

    public static float mapToFloat(float val, float minA, float maxA, float minB, float maxB) {
        return (1 - ((val - minA) / (maxA - minA))) * minB + ((val - minA) / (maxA - minA)) * maxB;
    }

    public static double mapToDouble(double val, double minA, double maxA, double minB, double maxB) {
        return (1 - ((val - minA) / (maxA - minA))) * minB + ((val - minA) / (maxA - minA)) * maxB;
    }

    public static float euclideanDistance(float x1, float y1, float x2, float y2) {
        return (float) java.lang.Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    /**
     * Determines whether a point is inside a rectangle
     * @param x (int): x-coordinates of the point
     * @param y (int): y-coordinates of the point
     * @param dx (int): x-coordinates of the top-left corner of the rectangle
     * @param dy (int): y-coordinates of the top-left corner of the rectangle
     * @param w (int): the rectangle's width
     * @param h (int): the rectangle's height
     * @return (boolean): whether the point is inside the rectangle
     */
    public static boolean isInRectangle(int x, int y, int dx, int dy, int w, int h) {
        return (x >= dx && x <= dx + w) && (y >= dy && y <= dy + h);
    }

    /**
     * Determines whether a point is inside a circle
     * @param x (int): x-coordinates of the point
     * @param y (int): y-coordinates of the point
     * @param dX (int): x-coordinates of the center of the circle
     * @param dY (int): y-coordinates of the center of the circle
     * @param r (int): circle radius
     * @return (boolean): whether the point is inside the circle
     */
    public static boolean isInCircle(int x, int y, int dX, int dY, int r) {
        return distanceToCircle(x, y, dX, dY, r) < 0;
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
        return (float) java.lang.Math.sqrt((x - h) * (x - h) + (y - i) * (y - i)) - r;
    }

    public static double roundToPrecision(double value, int precision) {
        return BigDecimal.valueOf(value).setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }

    public static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static double smoothStep(double t) {
        return t * t * (3 - 2 * t);
    }

    public static double quintic(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    public static double lerp(double val1, double val2, double t) {
        return val1 + (val2 - val1) * t;
    }

}
