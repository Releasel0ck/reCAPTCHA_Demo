package segment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import model.Point;
import tools.ImageUtil;

/**
 *
 * @author {
 * @_@}
 */
public class FilledSeg {

    /**
     * ***
     *
     * @param img 需要拆解的图(java.awt.image.BufferedImage)
     * @param subImgs 用于装填拆解后的图片碎块
     * @throws Exception
     */
    private void getImgCell(BufferedImage img, List<BufferedImage> subImgs) {
        long startTime = System.currentTimeMillis();// 用于查看算法耗时
        int minPix = 100;// 单个图片最小的像素数,下面会抛弃小于这个像素数的小图块
        // 获取图片宽高
        if (img != null) {
            int width = img.getWidth();
            int height = img.getHeight();
            // 用于装填每个图块的点数据
            List<HashMap<Point, Integer>> pointList = new ArrayList<HashMap<Point, Integer>>();
            // 根据宽高轮询图片中的所有点进行计算
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    // 找到一个点
                    label:
                    if (ImageUtil.isBlack(img.getRGB(x, y))) {
                        Point point = new Point(x, y);// java.awt.Point
                        for (HashMap<Point, Integer> pointMap : pointList) {
                            if (pointMap.get(point) != null) {
                                break label;// 跳到标签处,此时不会再执行下面的内容.
                            }
                        }

                        HashMap<Point, Integer> pointMap = new HashMap<Point, Integer>();
                        // 这个用法很关键,根据Map的KEY值不能重复的特点避免重复填充point
                        pointMap.put(point, 1);
                        // 这里就是在流水啦...
                        get4Point(x, y, img, pointMap, 500);
                        pointList.add(pointMap);
                        break;
                    }
                }
            }
            // 根据提取出来的point创建各个碎图块
            for (int i = 0; i < pointList.size(); ++i) {
                HashMap<Point, Integer> pointMap = pointList.get(i);
                // 图片的左,上,右,下边界以及宽,高
                int l = 0, t = 0, r = 0, b = 0, w = 0, h = 0, index = 0;
                for (Point p : pointMap.keySet()) {
                    if (index == 0) {
                        // 用第一个点来初始化碎图的四个边界
                        l = p.x;
                        t = p.y;
                        r = p.x;
                        b = p.y;
                    } else {
                        // 再根据每个点与原有的点进行比较取舍四个边界的值
                        l = Math.min(l, p.x);
                        t = Math.min(t, p.y);
                        r = Math.max(r, p.x);
                        b = Math.max(b, p.y);
                    }
                    index++;
                }
                w = r - l + 1;
                h = b - t + 1;
                // 去除杂点(小于50像素数量的点集不要)
                if (w * h < minPix) {
                    continue;
                }
                // 创建个图片空壳子(里面的所有点值都是0,即黑色)
                BufferedImage imgCell = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                // 先将所有点替换成白色(反正我没有找到new BufferedImage的时候可以初始化像素色值的)
                for (int x = 0; x < w; ++x) {
                    for (int y = 0; y < h; ++y) {
                        imgCell.setRGB(x, y, 0xffffffff);// 图片换成白底
                    }
                }
                // 对应点换成黑色(如果上面换成白底的一步不做的话下面这里可以替换成白色,就变成了黑底白色的碎图了)
                for (Point p : pointMap.keySet()) {
                    imgCell.setRGB(p.x - l, p.y - t, 0);
                }
                // 将切好的图放入上文传入的容器中(不传入容器的话这里可以用于返回)
                subImgs.add(imgCell);
            }
            // 耗时
//          System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
        }
    }

    private static void get4Point(int x, int y, BufferedImage img, HashMap<Point, Integer> pointMap, int diguiTime) {
        diguiTime++;
        //把次数打印出来
//      WriteStringToFile2("/Users/shangzhen/log.txt", "当前执行到第："+diguiTime+"层\n");

//        if (diguiTime >= 500) {
//            throw new StopMsgException();
//        }
        //左边
        Point pl = new Point(x - 1, y);
        if (x - 1 >= 0 && ImageUtil.isBlack(img.getRGB(x - 1, y)) && pointMap.get(pl) == null) {
            pointMap.put(pl, 1);
            get4Point(x - 1, y, img, pointMap, diguiTime);
        }
        //右边
        Point pr = new Point(x + 1, y);
        if (x + 1 < img.getWidth() && ImageUtil.isBlack(img.getRGB(x + 1, y)) && pointMap.get(pr) == null) {
            pointMap.put(pr, 1);
            get4Point(x + 1, y, img, pointMap, diguiTime);
        }
        //上边
        Point pt = new Point(x, y - 1);
        if (y - 1 >= 0 && ImageUtil.isBlack(img.getRGB(x, y - 1)) && pointMap.get(pt) == null) {
            pointMap.put(pt, 1);
            get4Point(x, y - 1, img, pointMap, diguiTime);
        }
        //下边
        Point pb = new Point(x, y + 1);
        if (y + 1 < img.getHeight() && ImageUtil.isBlack(img.getRGB(x, y + 1)) && pointMap.get(pb) == null) {
            pointMap.put(pb, 1);
            get4Point(x, y + 1, img, pointMap, diguiTime);
        }

        diguiTime--;
        //把次数打印出来
//      WriteStringToFile2("/Users/shangzhen/log.txt", "当前执行到第："+diguiTime+"层\n");
    }

    public static void main(String[] args) {
        int[] x = new int[5];
        System.out.println(x[4]);
//        FilledSeg s = new FilledSeg();
//        File dir = new File("test/");
//        //只列出jpg
//        File[] files = dir.listFiles(new FilenameFilter() {
//            
//            public boolean isJpg(String file) {
//                if (file.toLowerCase().endsWith(".jpg")) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//            
//            @Override
//            public boolean accept(File dir, String name) {
//                // TODO Auto-generated method stub
//                return isJpg(name);
//            }
//        });        
//        for (int i = 0; i < files.length; i++) {
//            try {
//                File file = files[i];
//                BufferedImage img = ImageIO.read(file);
////                ArrayList<BufferedImage> list = model.cfs(img);
//                List<BufferedImage> list = new ArrayList<>();
//                
//                s.getImgCell(img,list);
//                
//                //ArrayList<BufferedImage> list=s.getImgCell(img, subImgs)
//                for (int j = 0; j < list.size(); j++) {
//                    BufferedImage subImg = list.get(j);
//                    String prex = file.getName().split("\\.")[0];
//                    String filename = "test2/" + prex + "-" + j + ".jpg";
//                    ImageIO.write(subImg, "JPG", new File(filename));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }        
    }
}
