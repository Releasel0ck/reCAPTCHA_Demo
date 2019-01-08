package gray;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * 最大值法灰度化
 */
public class MaxGray {

    public static void gray(BufferedImage pImg) {
        int width = pImg.getWidth();
        int height = pImg.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(pImg.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int m = max(r, g, b);
                int rcolor = new Color(m, m, m).getRGB();
                pImg.setRGB(x, y, rcolor);
            }
        }
    }

    public static int max(int a, int b, int c) {
        if (a > b) {
            if (a > c) {
                return a;
            } else {
                return c;
            }
        } else {
            if (b > c) {
                return b;
            } else {
                return c;
            }
        }
    }
}
