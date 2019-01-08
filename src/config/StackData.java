package config;

import java.awt.image.BufferedImage;

//图片队列
public class StackData {

    public StackData(String nmethod, BufferedImage nimg) {
        img = nimg; //存放处理后图片
        method = nmethod;//存放处理的方法
    }
    public BufferedImage img = null;
    public String method = null;
}
