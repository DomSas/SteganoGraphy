package application;

import java.awt.image.BufferedImage;

public class TxTProcessing {        //changing text into binary

    BufferedImage loadedImage;
    byte[] bytes;

    public TxTProcessing(BufferedImage loadedImage) {
        this.loadedImage = loadedImage;

    }

    public String changeTextToBinary(String inputText) {
        bytes = inputText.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
//            binary.append(' ');
        }
        System.out.println("'" + inputText + "' to binary: " + binary);

        return binary.toString();

    }   // changing input string to binary

    public boolean controlOfPictureLength(String inputText) { // picture must be longer than whole message + 8 bits for metadata
        return changeTextToBinary(inputText).length() + 8 < ((loadedImage.getHeight() * loadedImage.getHeight()));

    }

}
