package app.noiseviewerjfx.utilities;

import javafx.geometry.Point2D;

public class Vector2D implements Cloneable{

    public final double x;
    public final double y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2D sub(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D sub(double X, double Y) {
        return new Vector2D(this.x - X, this.y - Y);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D add(double X, double Y) {
        return new Vector2D(this.x + X, this.y + Y);
    }

    public Vector2D mult(double d) {
        return new Vector2D(x * d, y * d);
    }

    public Vector2D div(double d) {
        return new Vector2D(x / d, y / d);
    }

    public Vector2D floor() {
        return new Vector2D(Math.floor(x), Math.floor(y));
    }

    public double dot(Vector2D other) {
        return x * other.x + y * other.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    public Point2D toPoint2D() {
        return new Point2D(x, y);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "X=" + x +
                ", Y=" + y +
                '}';
    }
}
