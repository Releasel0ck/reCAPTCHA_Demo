package gray;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * 平均值法灰度化
 */
public class AverageGray {

    public static void gray(BufferedImage pImg) {
        int width = pImg.getWidth();
        int height = pImg.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(pImg.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int a = (int) ((r + g + b) / 3);
                int rcolor = new Color(a, a, a).getRGB();
                pImg.setRGB(x, y, rcolor);
            }
        }
    }
}
