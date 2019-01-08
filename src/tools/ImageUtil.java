package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.jhlabs.image.ScaleFilter;

public class ImageUtil {

    /**
     * 判断是否位黑色像素
     *
     * @param rgb
     * @return
     */
    public static boolean isBlack(int rgb) {
        Color color = new Color(rgb);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
            return true;
        }
        return false;
    }

    /**
     * 缩放图片,默认16x16
     *
     * @param img
     * @return
     */
    public static BufferedImage scaleImage(BufferedImage img) {
        return scaleImage(img, 21, 28);
    }

    /**
     * 缩放图片
     *
     * @param img
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage scaleImage(BufferedImage img, int width, int height) {
        ScaleFilter sf = new ScaleFilter(width, height);
        BufferedImage imgdest = new BufferedImage(width, height, img.getType());
        return sf.filter(img, imgdest);
    }

    /**
     * 获得训练集图片的分类，如a-12.jpg，返回a
     *
     * @param filename
     * @return
     */
    public static String getImgClass(String filename) {
        String[] arr = filename.split("-");
        if (arr != null) {
            return arr[0];
        } else {
            return "";
        }
    }

    public static boolean isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
            return true;
        }
        return false;
    }
}
