package app.noiseviewerjfx.utilities.generation.ImageModel;

import app.noiseviewerjfx.utilities.generation.Grid;
import app.noiseviewerjfx.utilities.processing.ColorProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Opacity implements ImageModel{

    private final double OPACITY;

    public Opacity(double opacity) {
        this.OPACITY = opacity;
    }

    @Override
    public Image toImage(Grid plane) {

        WritableImage maskImage = new WritableImage(plane.getWidth(), plane.getHeight());
        PixelWriter maskPixelWriter = maskImage.getPixelWriter();

        for (int y = 0; y < plane.getHeight(); y++) {
            for (int x = 0; x < plane.getWidth(); x++) {
                maskPixelWriter.setArgb(x, y, ColorProcessing.argb((int) (plane.get(x, y) * 255 * OPACITY), 0, 0, 0));
            }
        }

        return maskImage;
    }
}
