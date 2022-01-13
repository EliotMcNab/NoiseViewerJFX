package app.noiseviewerjfx.utilities;

public class Vector2D {

    private final double X;
    private final double Y;

    public Vector2D(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    public Vector2D(Vector2D other) {
        this.X = other.X;
        this.Y = other.Y;
    }

    public Vector2D sub(Vector2D other) {
        return new Vector2D(X - other.X, Y - other.Y);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(X + other.X, Y + other.Y);
    }

    public Vector2D div(double d) {
        return new Vector2D(X / d, Y / d);
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
