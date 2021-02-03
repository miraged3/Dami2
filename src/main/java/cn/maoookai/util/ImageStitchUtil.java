package cn.maoookai.util;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageStitchUtil {

    public static BufferedImage getBufferedImage(File file)
            throws IOException {
        return ImageIO.read(file);
    }

    @NotNull
    public static File bufferedToFile(@NotNull ArrayList<File> file) throws IOException {
        BufferedImage image1 = ImageIO.read(file.get(0));
        BufferedImage image2 = ImageIO.read(file.get(4));
        BufferedImage image3 = ImageIO.read(new File("res/null.jpg"));
        for (int i = 1; i < 4; i++) {
            image1 = mergeImage(true, image1, ImageIO.read(file.get(i)));
        }
        for (int i = 5; i < 8; i++) {
            image2 = mergeImage(true, image2, ImageIO.read(file.get(i)));
        }
        image3 = mergeImage(true, image3, ImageIO.read(file.get(8)));
        image3 = mergeImage(true, image3, ImageIO.read(file.get(9)));
        image3 = mergeImage(true, image3, ImageIO.read(new File("res/null.jpg")));
        image2 = mergeImage(false, image1, image2);
        image3 = mergeImage(false, image2, image3);

        File outfile = new File("stitchedImage.jpg");
        ImageIO.write(image3, "jpg", outfile);
        return outfile;
    }


    @NotNull
    public static BufferedImage mergeImage(boolean isHorizontal, @NotNull BufferedImage... images) {
        // 生成新图片
        BufferedImage destImage;
        // 计算新图片的长和高
        int allWidth = 0, allHeight = 0, allWidthMax = 0, allHeightMax = 0;
        // 获取总长、总宽、最长、最宽
        for (BufferedImage img : images) {
            allWidth += img.getWidth();
            allHeight += img.getHeight();
            if (img.getWidth() > allWidthMax) {
                allWidthMax = img.getWidth();
            }
            if (img.getHeight() > allHeightMax) {
                allHeightMax = img.getHeight();
            }
        }
        // 创建新图片
        if (isHorizontal) {
            destImage = new BufferedImage(allWidth, allHeightMax, BufferedImage.TYPE_INT_RGB);
        } else {
            destImage = new BufferedImage(allWidthMax, allHeight, BufferedImage.TYPE_INT_RGB);
        }
        // 合并所有子图片到新图片
        int wx = 0, wy = 0;
        for (BufferedImage img : images) {
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // 从图片中读取RGB
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
            if (isHorizontal) { // 水平方向合并
                destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            } else { // 垂直方向合并
                destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            }
            wx += w1;
            wy += h1;
        }
        return destImage;
    }

}
