package app.noiseviewerjfx.utilities.processing;

import javafx.scene.image.Image;
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

    /*public static BufferedImage toGrayScale(int[][] grayScaleValues) {

        // image size
        final int IMAGE_HEIGHT = grayScaleValues.length;
        final int IMAGE_WIDTH = grayScaleValues[0].length;

        BufferedImage grayScaleImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                int grayScaleValue = grayScaleValues[y][x];
                int color = (grayScaleValue << 16) | (grayScaleValue << 8) | grayScaleValue;
                grayScaleImage.setRGB(x, y, color);
            }
        }

        return grayScaleImage;
    }*/

    public static Image toGrayScale(int[][] grayScaleValues) {

        // image size
        final int IMAGE_HEIGHT = grayScaleValues.length;
        final int IMAGE_WIDTH = grayScaleValues[0].length;

        WritableImage grayScaleImage = new WritableImage(IMAGE_WIDTH, IMAGE_HEIGHT);
        PixelWriter pixelWriter = grayScaleImage.getPixelWriter();

        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                int grayScaleValue = grayScaleValues[y][x];
                int color = (0xFF << 24) | (grayScaleValue << 16) | (grayScaleValue << 8) | grayScaleValue;
                pixelWriter.setArgb(x, y, color);
            }
        }

        return grayScaleImage;
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
