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

        //adding "y" to beginning of file to show there is something encoded + added length of message
        // both in binary

        String yesBinary = "01111001";
        StringBuilder lengthOfEncodedMessage = new StringBuilder(Integer.toBinaryString(binary.length()));

//        if the message is longer than 2^16, it is too long
        if (lengthOfEncodedMessage.length() > 16) {
            return null;
        }
        // make sure the binary length of message is always 8
        while (lengthOfEncodedMessage.length() < 16) {
            lengthOfEncodedMessage.insert(0, "0");
        }

        return yesBinary + lengthOfEncodedMessage + binary.toString();

    }   // changing input string to binary

    public boolean controlOfPictureLength(String inputText) { // picture must be longer than whole message + 24 bits for metadata
        return changeTextToBinary(inputText) != null && changeTextToBinary(inputText).length() + 24 < ((loadedImage.getHeight() * loadedImage.getHeight()));

    }


}
