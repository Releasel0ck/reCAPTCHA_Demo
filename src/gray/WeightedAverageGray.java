package gray;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * 加权平均值法灰度化
 */
public class WeightedAverageGray {

    public static void gray(BufferedImage pImg, double Wr, double Wg, double Wb) {
        int width = pImg.getWidth();
        int height = pImg.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(pImg.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int rgb = (int) ((r * Wr + g * Wg + b * Wb) / 3);
                int rcolor = new Color(rgb, rgb, rgb).getRGB();
                pImg.setRGB(x, y, rcolor);
            }
        }
    }
}
