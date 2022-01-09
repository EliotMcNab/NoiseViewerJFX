
package app.noiseviewerjfx.utilities.processing;

import static app.noiseviewerjfx.utilities.Math.maptoInt;
import static app.noiseviewerjfx.utilities.Math.euclideanDistance;
import static app.noiseviewerjfx.utilities.Math.distanceToCircle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class NoiseProcessing {

    public static Image debugGeneration(int imageSize, int border, int red, int green, int blue) {

        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < imageSize; y++) {
            for (int x = 0; x < imageSize; x++) {
                if (x <= border || x >= imageSize - border - 1 || y <= border || y >= imageSize - border - 1) {
                    image.setRGB(x, y, 0x242729);
                    continue;
                }
                int color = (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, color);
            }
        }

        return image;
    }

    /**
     * Subtracts a height map from another
     * @param map1 (float[][]): the map to subtract from
     * @param map2 (float[][]): the map that will be subtacted onto map1
     * @return
     */
    public static int[][] subtract(int[][] map1, int[][] map2, int strength) {

        // handles the case where both maps are not the same size
        if (map1.length != map2.length || map1[0].length != map2[0].length) {
            System.out.printf("Impossible to add maps of mismatching size s1=(%s, %s) and s2=(%s, %s)",
                    map1.length, map1[0].length, map2.length, map2[0].length);
        }

        // copies the contents of the first map
        int[][] map1Copy = new int[map1.length][map1[0].length];
        for (int i = 0; i < map1.length; i++) {
            map1Copy[i] = Arrays.copyOf(map1[i], map1[i].length);
        }

        // System.out.println("+++++++++++++++++++");
        // System.out.println("original height:" + map1.length);
        // System.out.println("original width:" + map1[0].length);
        // System.out.println("addition height:" + map1Copy.length);
        // System.out.println("addition width:" + map1Copy[0].length);
        // System.out.println("+++++++++++++++++++");

        // loops through every value of each map
        for (int y = 0; y < map1Copy.length; y++) {
            for (int x = 0; x < map1Copy[0].length; x++) {
                map1Copy[y][x] -= map2[y][x] * strength;
                // clamps the value to 0, 255
                if (map1Copy[y][x] < 0) map1Copy[y][x] = 0;
            }
        }

        return map1Copy;
    }

    /**
     * For detailed info and implementation see: <a href="http://devmag.org.za/2009/04/25/perlin-noise/">Perlin-Noise</a>
     */
    public class PerlinNoise {

        /**
         * @param width       width of noise array
         * @param height      height of noise array
         * @param octaveCount numbers of layers used for blending noise
         * @param persistence value of impact each layer get while blending
         * @param seed        used for randomizer
         * @return float array containing calculated "Perlin-Noise" values
         * @implNote Author: <a href="http://java.algorithmexamples.com/web/Others/PerlinNoise.html">java.algorithmexamples.com/</a>
         */
        public static int[][] generatePerlinNoise(int width, int height, int octaveCount, float persistence, long seed) {

            final float[][] base = new float[height][width];
            final float[][] perlinNoise = new float[height][width];
            final int[][] alphaPerlinNoise = new int[height][width];
            final float[][][] noiseLayers = new float[octaveCount][][];

            float maxNoiseValue = 0;
            float minNoiseValue = 1;

            Random random = new Random(seed);
            //fill base array with random values as base for noise
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    base[y][x] = random.nextFloat();
                }
            }

            //calculate octaves with different roughness
            for (int octave = 0; octave < octaveCount; octave++) {
                noiseLayers[octave] = generatePerlinNoiseLayer(base, width, height, octave);
            }

            float amplitude = 1f;
            float totalAmplitude = 0f;

            //calculate perlin noise by blending each layer together with specific persistence
            for (int octave = octaveCount - 1; octave >= 0; octave--) {
                amplitude *= persistence;
                totalAmplitude += amplitude;

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        //adding each value of the noise layer to the noise
                        //by increasing amplitude the rougher noises will have more impact
                        perlinNoise[y][x] += noiseLayers[octave][y][x] * amplitude;

                        if (perlinNoise[y][x] > maxNoiseValue) {
                            maxNoiseValue = perlinNoise[y][x];
                        } else if (perlinNoise[y][x] < minNoiseValue) {
                            minNoiseValue = perlinNoise[y][x];
                        }
                    }
                }
            }

            //normalize values so that they stay between 0..255
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    alphaPerlinNoise[y][x] = maptoInt(perlinNoise[y][x], minNoiseValue, maxNoiseValue, 0, 255);
                }
            }

            return alphaPerlinNoise;
        }

        /**
         * @param base   base random float array
         * @param width  width of noise array
         * @param height height of noise array
         * @param octave current layer
         * @return float array containing calculated "Perlin-Noise-Layer" values
         */
        private static float[][] generatePerlinNoiseLayer(float[][] base, int width, int height, int octave) {
            float[][] perlinNoiseLayer = new float[height][width];

            //calculate period (wavelength) for different shapes
            int period = 1 << octave; //2^k
            float frequency = 1f / period; // 1/2^k

            for (int y = 0; y < height; y++) {
                //calculates the horizontal sampling indices
                int y0 = (y / period) * period;
                int y1 = (y0 + period) % height;
                float verticalBlend = (y - y0) * frequency;

                for (int x = 0; x < width; x++) {
                    //calculates the vertical sampling indices
                    int x0 = (x / period) * period;
                    int x1 = (x0 + period) % width;
                    float horizintalBlend = (x - x0) * frequency;

                    //blend top corners
                    float top = interpolate(base[y0][x0], base[y0][x1], horizintalBlend);

                    //blend bottom corners
                    float bottom = interpolate(base[y1][x0], base[y1][x1], horizintalBlend);

                    //blend top and bottom interpolation to get the final blend value for this cell
                    perlinNoiseLayer[y][x] = interpolate(top, bottom, verticalBlend);
                }
            }

            return perlinNoiseLayer;
        }

        /**
         * @param a     value of point a
         * @param b     value of point b
         * @param alpha determine which value has more impact (closer to 0 -> a, closer to 1 -> b)
         * @return interpolated value
         */
        private static float interpolate(float a, float b, float alpha) {
            return a * (1 - alpha) + alpha * b;
        }
    }

    public class Mask {

        /**
         * Generates a gradient circular mask used in procedural island generation
         * @param width (int): width of the map the mask will be applied to
         * @param height (int): height of the map the mask will be applied to
         * @param maskWidth (float): percentage of the map the mask will cover
         * @return (float[height][width]): mask used to create falloff at the end of a map
         */
        public static int[][] generateRoundedSquareMask(int width, int height, float maskWidth, float maskHeight) {

            final float[][] floatMask = new float[height][width];
            final int[][] clampedMask = new int[height][width];

            float maxVal = 0;
            float minVal = Float.MAX_VALUE;

            float centralX = width / 2f;
            float centralY = height / 2f;

            float halfWidth = maskWidth / 2;
            float halfHeight = maskHeight / 2;

            float maxDistanceToSquare = euclideanDistance((centralX - halfWidth), (centralX - halfHeight), 0, 0);

            // generates the mask
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    float xDistance = Math.abs(x - (centralX - halfWidth));
                    float yDistance = Math.abs(y - (centralY - halfHeight));

                    if (x > centralX) xDistance = Math.abs(x - (centralX + halfWidth));
                    if (y > centralY) yDistance = Math.abs(y - (centralY + halfHeight));

                    if (x > (centralX - halfWidth) && x < (centralX + halfWidth)) xDistance = 0;
                    if (y > (centralY - halfHeight) && y < (centralY + halfHeight)) yDistance = 0;

                    float centerDistance = euclideanDistance(xDistance, yDistance, 0, 0);

                    float delta = centerDistance / maxDistanceToSquare;

                    float gradient = delta * delta;

                    floatMask[y][x] = gradient;

                    if (gradient < minVal) {
                        minVal = gradient;
                    } else if (gradient > maxVal) {
                        maxVal = gradient;
                    }
                }
            }

            // clamps the value of the mask between 0 and 255 for display
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    clampedMask[y][x] = maptoInt(floatMask[y][x], minVal, maxVal, 0, 255);
                }
            }

            return clampedMask;
        }

        public static int[][] generateCircularMask(int width, int height, float maskSize) {

            final float[][] floatMask = new float[height][width];
            final int[][] clampedMask = new int[height][width];

            float maxVal = 0;
            float minVal = Float.MAX_VALUE;

            float centralX = width / 2f;
            float centralY = height / 2f;

            float radius = maskSize / 2;

            float maxDistanceToCircle = euclideanDistance((centralX - radius), (centralX - radius), 0, 0);

            // generates the mask
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    // calculates the distance to the center of the circle
                    float centerDistance = distanceToCircle(x, y, centralX, centralY, maskSize / 2) ;

                    if (euclideanDistance(x, y, centralX, centralY) < radius) centerDistance = 0;

                    float delta = centerDistance / maxDistanceToCircle;

                    float gradient = delta * delta;

                    floatMask[y][x] = gradient;

                    if (gradient < minVal) {
                        minVal = gradient;
                    } else if (gradient > maxVal) {
                        maxVal = gradient;
                    }
                }
            }

            // clamps the value of the mask between 0 and 255 for display
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    clampedMask[y][x] = maptoInt(floatMask[y][x], minVal, maxVal, 0, 255);
                }
            }

            return clampedMask;
        }

    }
}