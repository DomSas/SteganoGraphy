package application;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImageProcessing {

    BufferedImage processedImg;
    int width;
    int height;

    public ImageProcessing(BufferedImage processedImg) {
        super();
        this.processedImg = processedImg;
        this.width = processedImg.getWidth();
        this.height = processedImg.getHeight();
    }


    public String showRGBValues() {

        Color c = new Color(processedImg.getRGB(101, 103));

        int[] pole = new int[3];
        pole[0] = c.getRed();
        pole[1] = c.getGreen();
        pole[2] = c.getBlue();


        return Arrays.toString(pole);
//		System.out.println(Arrays.toString(pole));
    }
}
