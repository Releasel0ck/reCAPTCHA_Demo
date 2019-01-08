package gray;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * 分量法灰度化
 */
public class RgbGray {

    public static void gray(BufferedImage pImg, int flag) {
        int width = pImg.getWidth();
        int height = pImg.getHeight();
        int[][] gray = new int[width][height];
        if (flag == 1) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(pImg.getRGB(x, y));
                    int r = color.getRed();
                    int rcolor = new Color(r, r, r).getRGB();
                    pImg.setRGB(x, y, rcolor);

                }
            }
        } else if (flag == 2) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(pImg.getRGB(x, y));
                    int g = color.getGreen();
                    int rcolor = new Color(g, g, g).getRGB();
                    pImg.setRGB(x, y, rcolor);
                }
            }
        } else if (flag == 3) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(pImg.getRGB(x, y));
                    int b = color.getBlue();
                    int rcolor = new Color(b, b, b).getRGB();
                    pImg.setRGB(x, y, rcolor);
                }
            }
        }
    }
}
