package trainPredict;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Identy {

    private HashMap<Integer, String> labelMap = null;
    public String path;

    public Identy(String p) {
        path = p;
        loadLabelMap();
    }

    private void loadLabelMap() {
        labelMap = new HashMap<Integer, String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(path + "/label.txt")));

            String buff = null;
            while ((buff = reader.readLine()) != null) {
                String[] arr = buff.split(" ");
                labelMap.put(Integer.parseInt(arr[1]), arr[0]);
            }
            //System.out.println("load image label finish!");

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

    private String getClassName(int label) {
        return labelMap.get(label);
    }

    public String predict(ArrayList<BufferedImage> imageList) throws IOException {
        Predict.run(imageList, path);
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(path + "/result.txt")));

            String buff = "";
            while ((buff = reader.readLine()) != null) {
                int label = (int) Double.parseDouble(buff);
                String className = getClassName(label);
                result += className;
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }
}
