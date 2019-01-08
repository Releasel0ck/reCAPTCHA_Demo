/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainPredict;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import svmHelper.svm_scale;
import svmHelper.svm_train;

public class Train {

    /**
     * 类标号map，如：a=>1 b=>2
     */
    private HashMap<String, Integer> labelMap = null;
    public String path;
    public char trainChar;
    public BufferedImage trainImg;

    /**
     * 所有图像分类的map，key为当前类标号， value为对应的图片，图片以二维数组的形式保存
     */
    private HashMap<String, ArrayList<Integer[][]>> imageMap = null;

    public Train(String p, char c, BufferedImage i) {
        path = p;
        trainImg = i;
        trainChar = c;
        init();
    }

    private void init() {
        labelMap = new HashMap<String, Integer>();
        imageMap = new HashMap<String, ArrayList<Integer[][]>>();
        loadImageLabel();
        try {
            transferToMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        svmFormat();
    }

    private void loadImageLabel() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(path + "/label.txt")));

            String buff = null;
            while ((buff = reader.readLine()) != null) {
                String[] arr = buff.split(" ");
                labelMap.put(arr[0], Integer.parseInt(arr[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将image 转换到 map中
     *
     * @param file
     * @throws IOException
     */
    private void transferToMap() throws IOException {
        //  BufferedImage image = ImageIO.read(file);
        int width = trainImg.getWidth();
        int height = trainImg.getHeight();
        Integer[][] imgArr = new Integer[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //黑色点标记为1
                int value = ImageUtil.isBlack(trainImg.getRGB(x, y)) ? 1 : 0;
                imgArr[y][x] = value;
            }
        }
        ArrayList<Integer[][]> imgList = null;
//        String className = ImageUtil.getImgClass(file.getName());
        if (imageMap.containsKey(String.valueOf(trainChar))) {
            imgList = imageMap.get(String.valueOf(trainChar));
            imgList.add(imgArr);
        } else {
            imgList = new ArrayList<Integer[][]>();
            imgList.add(imgArr);
            imageMap.put(String.valueOf(trainChar), imgList);
        }
    }

    /**
     * 转换成svm预料的格式
     */
    public void svmFormat() {
        PrintWriter writer = null;
        try {
            FileWriter fw = new FileWriter(path + "/svm.train", true);

            writer = new PrintWriter(fw);
            Iterator<String> iterator = this.imageMap.keySet().iterator();
            while (iterator.hasNext()) {
                String className = iterator.next();
                int classLabel = labelMap.get(className);
                ArrayList<Integer[][]> list = imageMap.get(className);
                System.out.println(className);
                for (Integer[][] img : list) {
                    String tmpLine = classLabel + " ";
                    int index = 1;
                    for (int i = 0; i < img.length; i++) {
                        for (int j = 0; j < img[i].length; j++) {
                            tmpLine += index + ":" + img[i][j] + " ";
                            index++;
                        }
                    }
//					System.out.println(tmpLine);
                    writer.write(tmpLine + "\r\n");
                    writer.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 生成模型
     *
     * @throws IOException
     */
    public static void run(String p) throws IOException {
        //train参数    

        String t = p + "/svm.train";
        String m = p + "/svm.model";

        String[] arg = {"-t", "0", t, m};
        //predict参数
        // String[] parg = {"svm/svmscale.test", "svm/svm.model", "svm/result.txt"};

        System.out.println("训练开始");
        svm_train.main(arg);
        System.out.println("训练结束");
    }
}
