package cn.maoookai.util;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

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
    public static File bufferedToFile(File file1, File file2) throws IOException {
        Image image = mergeImage(getBufferedImage(file1), getBufferedImage(file2), true);
        File outfile = new File("stitchedImage.jpg");
        ImageIO.write((RenderedImage) image, "jpg", outfile);
        return outfile;
    }


    @NotNull
    public static BufferedImage mergeImage(@NotNull BufferedImage img1,
                                           @NotNull BufferedImage img2, boolean isHorizontal) {
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();

        int[] ImageArrayOne = new int[w1 * h1];
        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
        int[] ImageArrayTwo = new int[w2 * h2];
        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);
        BufferedImage DestImage;
        if (isHorizontal) {
            DestImage = new BufferedImage(w1 + w2, h1, BufferedImage.TYPE_INT_RGB);
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
            DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
        } else {
            DestImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
            DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2);
        }
        return DestImage;
    }


}
