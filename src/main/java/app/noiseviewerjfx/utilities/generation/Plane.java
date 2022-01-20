package app.noiseviewerjfx.utilities.generation;

/**
 * A 2d plane used for storing values
 * <br>
 * <i>see {@link VectorField} and {@link Grid} for working implementations</i>
 */
public interface Plane {
    Object get(int x, int y);

    int getWidth();

    int getHeight();

    int getSize();

    Object valAt(int i);
}
