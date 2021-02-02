package cn.maoookai.util;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class ImageStitchUtil {

    public static BufferedImage getBufferedImage(File file)
            throws IOException {
        return ImageIO.read(file);
    }

    @NotNull
    public static File bufferedToFile(File file1, File file2,Boolean isHorizontal) throws IOException {
        Image image = mergeImage(isHorizontal,getBufferedImage(file1), getBufferedImage(file2));
        File outfile = new File("stitchedImage.jpg");
        ImageIO.write((RenderedImage) image, "jpg", outfile);
        return outfile;
    }


    @NotNull
    public static BufferedImage mergeImage(boolean isHorizontal, BufferedImage... images) {
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
