/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ScanCut {

    public static BufferedImage cut(BufferedImage pImg, int pix) {
        int w = pImg.getWidth();
        int h = pImg.getHeight();
        int xStart = 0;
        int xEnd = w;
        int yStart = 0;
        int yEnd = h;
        int[] xCount = new int[w];
        int[] yCount = new int[h];
        int newW = 0;
        int newH = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (ImageUtil.isBlack(pImg.getRGB(i, j))) {
                    xCount[i]++;
                }
            }
        }
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                if (ImageUtil.isBlack(pImg.getRGB(i, j))) {
                    yCount[j]++;
                }
            }
        }
        for (int i = 0; i < w; i++) {
            if (xCount[i] > pix) {
                xStart = i;
                break;
            }
        }
        for (int i = w - 1; i >= 0; i--) {
            if (xCount[i] > pix) {
                xEnd = i;
                break;
            }
        }
        for (int j = 0; j < h; j++) {
            if (yCount[j] > pix) {
                yStart = j;
                break;
            }
        }
        for (int j = h - 1; j >= 0; j--) {
            if (yCount[j] > pix) {
                yEnd = j;
                break;
            }
        }
        newW = xEnd - xStart + 1;
        newH = yEnd - yStart + 1;
        BufferedImage image = new BufferedImage(newW, newH, BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < newW; x++) {
            for (int y = 0; y < newH; y++) {
                if (ImageUtil.isBlack(pImg.getRGB(x + xStart, y + yStart))) {
                    image.setRGB(x, y, new Color(0, 0, 0).getRGB());
                } else {
                    image.setRGB(x, y, new Color(255, 255, 255).getRGB());
                }

            }
        }
        return image;
    }

    public static void main(String[] args) {
        File dir = new File("test2/");
        //只列出jpg
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean isJpg(String file) {
                if (file.toLowerCase().endsWith(".jpg")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean accept(File dir, String name) {
                // TODO Auto-generated method stub
                return isJpg(name);
            }
        });
        for (int i = 0; i < files.length; i++) {
            try {
                File file = files[i];
                BufferedImage img = ImageIO.read(file);
                BufferedImage out = cut(img, 3);
                String filename = "test2/" + "c" + "-" + i + ".jpg";
                ImageIO.write(out, "JPG", new File(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
