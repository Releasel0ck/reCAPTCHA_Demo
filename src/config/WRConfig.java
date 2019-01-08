package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class WRConfig {

    /*
1 简单裁剪  
2 分量法灰度化R
3 分量法灰度化G
4 分量法灰度化B
5 最大值法灰度化
6 平均值灰度化
7 加权平均值法灰度化
8 大津算法
9 循环阈值算法
10 NiBlack算法二值化
11 Bersen算法二值化 
12 Sauvola算法二值化
13 均值滤波器
14 中值滤波器
15 去除干扰线1
16 膨胀算法
17 腐蚀算法
18 CFS连通域切割
19 FCM模糊C均值
20 对称近邻均值滤波
     */
    //将方法写入配置文件
    public void writeConfig(String path, int n, ArrayList<ConfigData> nMethod) {
        File p = new File(path + "/config.txt");

        if (p.exists()) {
            int x = JOptionPane.showConfirmDialog(null, "已经存在配置文件，是否覆盖?", "提示", JOptionPane.YES_NO_OPTION);
            if (x != 0) {
                return;
            }
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(p);
            writer.write(n + "\r\n");
            for (int i = 0; i < nMethod.size(); i++) {
                String str = nMethod.get(i).method + " " + nMethod.get(i).value + "\r\n";
                writer.write(str);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        JOptionPane.showMessageDialog(null, "写入配置文件成功！");
    }

    //从配置文件中读入处理流程
    public int readConfig(String path, ArrayList<ConfigData> nMethod) {
        BufferedReader reader = null;
        int n = 0;
        try {
            File p;
            reader = new BufferedReader(new FileReader(new File(path + "/config.txt")));

            String buff = null;
            int f = 0;
            while ((buff = reader.readLine()) != null) {
                if (f == 0) {
                    n = Integer.parseInt(buff);;
                    f = 1;
                } else {
                    String[] arr = buff.split(" ");
                    if (arr.length == 1) {
                        ConfigData c = new ConfigData(Integer.parseInt(arr[0]), "");
                        nMethod.add(c);
                    } else if (arr.length == 2) {
                        ConfigData c = new ConfigData(Integer.parseInt(arr[0]), arr[1]);
                        nMethod.add(c);
                    }

                }
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
        return n;
    }
}
