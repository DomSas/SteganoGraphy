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
    StringBuilder valuesAsString = new StringBuilder();

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
                redValuesOfPicture[i][j] = b;        // taking just red binary value and add to array
            }
        }
//        System.out.println(Arrays.deepToString(redValuesOfPicture));           // printing binary array
    }

    // create a final image with message encoded inside
    public BufferedImage setRedValuesOfPicture(String msgToEncode) {

        for (int i = 0; i < widthOfPic; i++) {
            for (int j = 0; j < heightOfPic; j++) {
                int r = outputChangedPixel(i, j, msgToEncode);
                int g = new Color(processedImg.getRGB(i, j)).getGreen();
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

        if (numOfPixel > msgToEncode.length() - 1) {                      // checks if we are not in the end of message to encrypt
            return a;
        }
        if (!b.endsWith(String.valueOf(msgToEncode.charAt(numOfPixel)))) {      // check if LSB is not equal to current encoded bit, if yes, it changes it
            b = b.substring(0, b.length() - 1) + msgToEncode.charAt(numOfPixel);
        }


        int returnedPixelValue = Integer.parseInt(b, 2);             // changing binary back to decimal

        numOfPixel++;                   // increasing position in encrypted message to right
        return returnedPixelValue;
    }


    public void showRedValuesAsString() {

        StringBuilder valuesAsString = new StringBuilder();

        for (int i = 0; i < widthOfPic; i++) {
            for (int j = 0; j < heightOfPic; j++) {
                int a = new Color(processedImg.getRGB(i, j)).getRed();
                String b = Integer.toString(a, 2);
                this.valuesAsString = valuesAsString.append(String.valueOf(b.charAt(b.length() - 1)));
            }
        }
//        System.out.println(valuesAsString);
    }

    public int containsMessage() {

        if (valuesAsString.substring(0, 8).equals("01111001")) { // if message contains y in the beginning, read how long is message
//            System.out.println(valuesAsString.substring(8, 24));
            return Integer.parseInt(valuesAsString.substring(8, 24), 2);
        }
        return 0;
    }

    public String decodeText(int lenghtOfMessage) {

        String s = valuesAsString.substring(24, lenghtOfMessage + 25);
//        System.out.println(s);
        String str = "";

        for (int i = 0; i < s.length() / 8; i++) {

            int a = Integer.parseInt(s.substring(8 * i, (i + 1) * 8), 2);
            str += (char) (a);
        }

//        System.out.println(str);


        return str;
    }

}
