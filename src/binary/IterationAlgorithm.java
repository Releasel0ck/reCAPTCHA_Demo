package binary;

import java.awt.Color;
import java.awt.image.BufferedImage;

//循环阈值算法
public class IterationAlgorithm {

    public static void getBinaryImage(BufferedImage pImg) {
        int width = pImg.getWidth();
        int height = pImg.getHeight();
        int sum = 0;
        int T = 0;
        int k = 0;
        int r = 0;
        int gray1 = 0;
        int gray2 = 0;
        int u1 = 0;
        int u2 = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(pImg.getRGB(x, y));
                int rgb = color.getRed();
                sum += rgb;
            }
        }
        T = sum / (width * height);
        int T1 = 0;
        while (T != T1) {
            T1 = T;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(pImg.getRGB(x, y));
                    int rgb = color.getRed();
                    if (rgb < T) {
                        gray1 += rgb;
                        k++;
                    } else {
                        gray2 += rgb;
                        r++;
                    }
                }
            }
            u1 = gray1 / k;
            u2 = gray2 / r;
            T = (u1 + u2) / 2;
        }
        //二值化
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(pImg.getRGB(x, y));
                int rgb = color.getRed();
                if (rgb > T) {
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
