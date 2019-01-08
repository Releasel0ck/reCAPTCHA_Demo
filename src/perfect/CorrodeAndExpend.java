/*
修补图像包
 */
package perfect;

import java.awt.Color;
import java.awt.image.BufferedImage;
import tools.ImageUtil;

public class CorrodeAndExpend {

    public static boolean[] getRoundPixel(BufferedImage pImg, int x, int y)//返回(x,y)周围像素的情况，为黑色，则设置为true
    {
        boolean[] pixels = new boolean[8];
        int num = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    if (ImageUtil.isBlack(pImg.getRGB(x + i, y + j))) {
                        pixels[num] = true;//为黑色，设置为true
                        num++;
                    } else {
                        pixels[num] = false;//为白色，设置为false
                        num++;
                    }
                }
            }
        }
        return pixels;
    }

    //膨胀算法
    public static void expend(BufferedImage pImg) {
        int width = pImg.getWidth();
        int height = pImg.getHeight();
        int[][] pa = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pa[x][y] = pImg.getRGB(x, y);
            }
        }
        boolean[] pixels;
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (!ImageUtil.isBlack(pImg.getRGB(i, j))) {
                    pixels = getRoundPixel(pImg, i, j);
                    for (int k = 0; k < pixels.length; k++) {
                        if (pixels[k] == true) {
                            //set this piexl's color to black
                            pa[i][j] = new Color(0, 0, 0).getRGB();
                            break;
                        }
                    }
                }
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pImg.setRGB(x, y, pa[x][y]);
            }
        }
    }

    //腐蚀算法
    public static void corrode(BufferedImage pImg) {
        int width = pImg.getWidth();
        int height = pImg.getHeight();
        int[][] pa = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pa[x][y] = pImg.getRGB(x, y);
            }
        }
        boolean[] pixels;
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (ImageUtil.isBlack(pImg.getRGB(i, j))) {
                    pixels = getRoundPixel(pImg, i, j);
                    for (int k = 0; k < pixels.length; k++) {
                        if (pixels[k] == false) {
                            //set this piexl's color to black
                            pa[i][j] = new Color(255, 255, 255).getRGB();
                            break;
                        }
                    }
                }
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pImg.setRGB(x, y, pa[x][y]);
            }
        }
    }
}
