package trainPredict;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import svmHelper.svm_predict;

public class Predict {

    /**
     * 类标号map，如：a=>1 b=>2
     */
    private HashMap<String, Integer> labelMap = null;
    private HashMap<String, Integer[][]> imageMap = null;
    public String path;

    public Predict(String p) {
        path = p;
        init();
    }

    private void init() {
        labelMap = new HashMap<String, Integer>();
        imageMap = new HashMap<String, Integer[][]>();
        loadImageLabel();
    }

    /**
     * 加载类标号
     */
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
     * 获得类标号
     *
     * @param className
     * @return
     */
    private int getClassLabel(String className) {
        if (labelMap.containsKey(className)) {
            return labelMap.get(className);
        } else {
            return -1;
        }
    }

    /**
     * 将image 转换到 map中
     *
     * @param file
     * @throws IOException
     */
    private void transferToMap(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        Integer[][] imgArr = new Integer[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //黑色点标记为1
                int value = ImageUtil.isBlack(image.getRGB(x, y)) ? 1 : 0;
                imgArr[y][x] = value;
            }
        }

        this.imageMap.put(file.getName(), imgArr);

    }

    /**
     * 转换为svm的格式
     *
     * @param imageList
     */
    private void svmFormat(ArrayList<BufferedImage> imageList) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(path + "/svm.test"));

            for (BufferedImage image : imageList) {
                int width = image.getWidth();
                int height = image.getHeight();
                int index = 1;
                String tmpLine = "-1 ";//默认无标号，则为-1
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        //黑色点标记为1
                        int value = ImageUtil.isBlack(image.getRGB(x, y)) ? 1 : 0;
                        tmpLine += index + ":" + value + " ";
                        index++;
                    }
                }
                writer.write(tmpLine + "\r\n");
                writer.flush();
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
     * 公共接口
     *
     * @param imageList
     * @throws IOException
     */
    public static void run(ArrayList<BufferedImage> imageList, String path) throws IOException {
        Predict model = new Predict(path);
        model.svmFormat(imageList);
        //predict参数

        String test = path + "/svm.test";
        String mode = path + "/svm.model";
        String result = path + "/result.txt";

        //String[] parg = {"svm/svm.test","svm/svm.model","svm/result.txt"};
        String[] parg = {test, mode, result};
        System.out.println("预测开始");
        svm_predict.main(parg);
        System.out.println("预测结束");
    }
}
