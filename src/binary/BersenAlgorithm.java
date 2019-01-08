//二进制算法包
package binary;

import java.awt.Color;
import java.awt.image.BufferedImage;

//Bersen算法
public class BersenAlgorithm {

    public static void getBinaryImage(BufferedImage pImg, int w) {
        int width = pImg.getWidth();
        int height = pImg.getHeight();
        int[][] imageT = new int[width][height];
        for (int x = w; x < width - w; x++) {
            for (int y = w; y < height - w; y++) {
                Color color0 = new Color(pImg.getRGB(x, y));
                int rgb0 = color0.getRed();
                int max = rgb0;
                int min = rgb0;
                for (int n = 0; n < 2 * w + 1; n++) {
                    for (int m = 0; m < 2 * w + 1; m++) {
                        Color color = new Color(pImg.getRGB(x + w - n, y + w - m));
                        int rgb = color.getRed();
                        if (max < rgb) {
                            max = rgb;
                        }
                        if (min > rgb) {
                            min = rgb;
                        }
                    }
                }

                imageT[x][y] = (int) (0.5 * (max + min));
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
