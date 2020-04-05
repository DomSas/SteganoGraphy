package application;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImageProcessing {

    BufferedImage processedImg;
    int widthOfPic;
    int heightOfPic;
    String msgToEncode;
    String[][] redValuesOfPicture;
    int numOfPixel = 0;

    public ImageProcessing(BufferedImage processedImg) {
        super();
        this.processedImg = processedImg;
        this.widthOfPic = processedImg.getWidth();
        this.heightOfPic = processedImg.getHeight();

    }

    // printing array of red LSB in picture
    public void showRedValues() {

        redValuesOfPicture = new String[widthOfPic][heightOfPic];              // create array with size of picture

        for (int i = 0; i < widthOfPic; i++) {
            for (int j = 0; j < heightOfPic; j++) {
                int a = new Color(processedImg.getRGB(i, j)).getRed();
                String b = Integer.toString(a, 2);                               // changing hexadecimal to binary
                redValuesOfPicture[i][j] = b;        // taking just last bit of red value and add to array
            }
        }
        System.out.println(Arrays.deepToString(redValuesOfPicture));           // printing binary array
    }

    // create a final image with message encoded inside
    public BufferedImage setRedValuesOfPicture(String msgToEncode) {

        for (int i = 0; i < widthOfPic; i++) {
            for (int j = 0; j < heightOfPic; j++) {
                int r = outputChangedPixel(i, j, msgToEncode);
//                int g = new Color(processedImg.getRGB(i, j)).getGreen();
                int g = 0;
                int b = new Color(processedImg.getRGB(i, j)).getBlue();
                int a = new Color(processedImg.getRGB(i, j)).getAlpha();
                int col = (a << 24) | (r << 16) | (g << 8) | b;
                processedImg.setRGB(i, j, col);

            }

        }
        return processedImg;
    }

    // gets two strings, compares LSB with message to be encoded and returns new value of red pixel
    public int outputChangedPixel(int x, int y, String msgToEncode) {

        int a = new Color(processedImg.getRGB(x, y)).getRed();
        String b = Integer.toString(a, 2);                          // changing decimal pixel value to binary
//        System.out.println(b);
//        System.out.println(msgToEncode.charAt(numOfPixel));

        if (numOfPixel == msgToEncode.length() - 1) {                      // checks if we are not in the end of message to encrypt
            return a;
        }
        if (!b.endsWith(String.valueOf(msgToEncode.charAt(numOfPixel)))) {      // check if LSB is not equal to current encoded bit, if yes, it changes it
            b = b.substring(0, b.length() - 1) + msgToEncode.charAt(numOfPixel);
        }


        int returnedPixelValue = Integer.parseInt(b, 2);             // changing binary back to decimal

        numOfPixel++;                   // increasing position in encrypted message to right
        //TODO osetrit koniec stringu ktory sa ma zakodovat
        return returnedPixelValue;
    }


}
