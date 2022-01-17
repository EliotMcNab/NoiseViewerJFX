package app.noiseviewerjfx.utilities.processing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessing {

    public static void saveImage(BufferedImage image, String path) {

        File file = new File(path);
        try {
            ImageIO.write(image, "PNG", file);
        } catch (IOException e) {
            System.out.printf("Error saving image at %s", path);
        }

    }

    /*public static Image overlay(Image background, Image foreground) {

        // the dimensions of the image
        final int IMAGE_HEIGHT  = (int) background.getHeight();
        final int IMAGE_WIDTH   = (int) background.getWidth();

        PixelReader backgroundPixelReader = background.getPixelReader();
        PixelReader foregroundPixelReader = foreground.getPixelReader();
        WritableImage layeredImage = new WritableImage(IMAGE_WIDTH, IMAGE_HEIGHT);



    }*/

    public static Image upScale(Image originalImage, final int scale) {

        final int IMAGE_WIDTH = (int) originalImage.getWidth();
        final int IMAGE_HEIGHT = (int) originalImage.getHeight();

        WritableImage scaledImage = new WritableImage(IMAGE_WIDTH * scale, IMAGE_HEIGHT * scale);

        PixelReader reader = originalImage.getPixelReader();
        PixelWriter writer = scaledImage.getPixelWriter();

        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                int pixelColor = reader.getArgb(x, y);
                for (int dx = 0; dx < scale; dx++) {
                    for (int dy = 0; dy < scale; dy++) {
                        writer.setArgb(x * scale + dx, y * scale + dy, pixelColor);
                    }
                }
            }
        }

        return scaledImage;
    }

    public static BufferedImage terrainGeneration(int[][] noiseMap, int shallows1Height, int shallows2Height, int plainsHeight, int forestHeight, int mountainHeight, float scale) {

        // image size
        final int IMAGE_HEIGHT = noiseMap.length;
        final int IMAGE_WIDTH = noiseMap[0].length;

        BufferedImage terrainImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                int noiseValue = (int) (noiseMap[y][x] * scale);

                if (noiseValue > shallows1Height) {
                    if (noiseValue > shallows2Height) {
                        if (noiseValue > plainsHeight) {
                            if (noiseValue > forestHeight) {
                                if (noiseValue > mountainHeight) {
                                    terrainImage.setRGB(x, y, 0xcf8c4e);    // mountains
                                    continue;
                                }
                                terrainImage.setRGB(x, y, 0x3b7266);        // forests
                                continue;
                            }
                            terrainImage.setRGB(x, y, 0x589740);            // plains
                            continue;
                        }
                        terrainImage.setRGB(x, y, 0x5ccec5);                // shallows 2
                        continue;

                    }
                    terrainImage.setRGB(x, y, 0x5a81dd);                    // shallows1
                    continue;
                }
                terrainImage.setRGB(x, y, 0x4e4cca);                        // oceans
            }
        }

        return terrainImage;

    }

}
