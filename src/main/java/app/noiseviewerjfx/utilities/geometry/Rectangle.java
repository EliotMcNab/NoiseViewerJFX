package app.noiseviewerjfx.utilities.geometry;

import app.noiseviewerjfx.utilities.Vector2D;

import java.awt.geom.Point2D;
import java.util.Arrays;

public class Rectangle {

    public final int w;
    public final int h;
    public final Vector2D position;
    private final Vector2D minPosition;
    private final Vector2D maxPosition;

    public Rectangle(final int w, final int h) {
        this(w, h, new Vector2D());
    }

    public Rectangle(final int w, final int h, final Vector2D position) {
        this.w = w;
        this.h = h;
        this.position = position;

        minPosition = position;
        maxPosition = position.add(w, h);
    }

    public double closestDistanceTo(Vector2D point) {
        final double dx = Arrays.stream(new double[]{minPosition.x - point.x, 0, point.x - maxPosition.x}).max().getAsDouble();
        final double dy = Arrays.stream(new double[]{minPosition.y - point.y, 0, point.y - maxPosition.y}).max().getAsDouble();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double closestDistanceTo(double x, double y) {
        final double dx = Arrays.stream(new double[]{minPosition.x -x, 0, x - maxPosition.x}).max().getAsDouble();
        final double dy = Arrays.stream(new double[]{minPosition.y -y, 0, y - maxPosition.y}).max().getAsDouble();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Rectangle moveTo(Vector2D newPosition) {
        return new Rectangle(w, h, newPosition);
    }

}
