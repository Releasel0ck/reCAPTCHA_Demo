package noise;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * 均值滤波器
 */
public class MeanFilter {

    /**
     * 均值滤波
     *
     * @return 处理成功的图片
     */
    public static void avrFiltering(BufferedImage sImg) {
        int w = sImg.getWidth();
        int h = sImg.getHeight();
        int[] pix = new int[w * h];
        sImg.getRGB(0, 0, w, h, pix, 0, w);
        int newpix[] = avrFiltering(pix, w, h);
        sImg.setRGB(0, 0, w, h, newpix, 0, w);
    }

    /**
     * 均值滤波
     *
     * @param pix 像素矩阵数组
     * @param w 矩阵的宽
     * @param h 矩阵的高
     * @return 处理后的数组
     */
    public static int[] avrFiltering(int pix[], int w, int h) {
        int newpix[] = new int[w * h];
        ColorModel cm = ColorModel.getRGBdefault();
        int r = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (x != 0 && x != w - 1 && y != 0 && y != h - 1) {
                    //g = (f(x-1,y-1) + f(x,y-1)+ f(x+1,y-1)  
                    //  + f(x-1,y) + f(x,y) + f(x+1,y)  
                    //  + f(x-1,y+1) + f(x,y+1) + f(x+1,y+1))/9  
                    r = (cm.getRed(pix[x - 1 + (y - 1) * w]) + cm.getRed(pix[x + (y - 1) * w]) + cm.getRed(pix[x + 1 + (y - 1) * w])
                            + cm.getRed(pix[x - 1 + (y) * w]) + cm.getRed(pix[x + (y) * w]) + cm.getRed(pix[x + 1 + (y) * w])
                            + cm.getRed(pix[x - 1 + (y + 1) * w]) + cm.getRed(pix[x + (y + 1) * w]) + cm.getRed(pix[x + 1 + (y + 1) * w])) / 9;
                    newpix[y * w + x] = 255 << 24 | r << 16 | r << 8 | r;

                } else {
                    newpix[y * w + x] = pix[y * w + x];
                }
            }
        }
        return newpix;
    }
}
