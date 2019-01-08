package lines;

import java.awt.Color;
import java.awt.image.BufferedImage;
import tools.ImageUtil;

public class CleanLines1 {

    public static void cleanLinesInImage(BufferedImage sImg) {
        int h = sImg.getHeight();
        int w = sImg.getWidth();
        //BufferedImage rImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        //去除干扰线条
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                boolean flag = false;
                if (ImageUtil.isBlack(sImg.getRGB(x, y))) {
                    //左右均为空时，去掉此点
                    if (ImageUtil.isWhite(sImg.getRGB(x - 1, y)) && ImageUtil.isWhite(sImg.getRGB(x + 1, y))) {
                        flag = true;
                    }
                    //上下均为空时，去掉此点
                    if (ImageUtil.isWhite(sImg.getRGB(x, y + 1)) && ImageUtil.isWhite(sImg.getRGB(x, y - 1))) {
                        flag = true;
                    }
                    //斜上下为空时，去掉此点
                    if (ImageUtil.isWhite(sImg.getRGB(x - 1, y + 1)) && ImageUtil.isWhite(sImg.getRGB(x + 1, y - 1))) {
                        flag = true;
                    }
                    if (ImageUtil.isWhite(sImg.getRGB(x + 1, y + 1)) && ImageUtil.isWhite(sImg.getRGB(x - 1, y - 1))) {
                        flag = true;
                    }
                    if (flag) {
                        sImg.setRGB(x, y, -1);
                    } else {
                        sImg.setRGB(x, y, sImg.getRGB(x, y));
                    }
                }
            }
        }
    }
}
