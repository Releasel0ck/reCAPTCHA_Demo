package binary;

import java.awt.Color;
import java.awt.image.BufferedImage;

//Sauvola算法
public class SauvolaAlgorithm {

    public static void getBinaryImage(BufferedImage pImg, double k, int w) {

        int width = pImg.getWidth();
        int height = pImg.getHeight();
        int[][] imageT = new int[width][height];
        int area = (2 * w + 1) * (2 * w + 1);

        for (int x = w; x < width - w; x++) {
            for (int y = w; y < height - w; y++) {
                double sum = 0;
                for (int n = 0; n < 2 * w + 1; n++) {
                    for (int m = 0; m < 2 * w + 1; m++) {
                        Color color = new Color(pImg.getRGB(x + w - n, y + w - m));
                        int rgb = color.getRed();
                        sum += rgb;
                    }
                }
                double average = sum / area;

                sum = 0;
                for (int n = 0; n < 2 * w + 1; n++) {
                    for (int m = 0; m < 2 * w + 1; m++) {
                        Color color = new Color(pImg.getRGB(x + w - n, y + w - m));
                        int rgb = color.getRed();
                        sum += (rgb - average) * (rgb - average);
                    }
                }
                sum = sum / area;
                double v = Math.sqrt(sum);

                imageT[x][y] = (int) (average * (1 + k * (v / 128 - 1)));
            }
        }

        for (int x = w; x < width - w; x++) {
            for (int y = w; y < height - w; y++) {
                Color color = new Color(pImg.getRGB(x, y));
                int rgb = color.getRed();
                if (rgb > imageT[x][y]) {
                    int max = new Color(255, 255, 255).getRGB();
                    pImg.setRGB(x, y, max);
                } else {
                    int min = new Color(0, 0, 0).getRGB();
                    pImg.setRGB(x, y, min);
                }
            }
        }
    }
}
