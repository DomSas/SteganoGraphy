package application;

public class TxTProcessing {        //changing text into binary

    byte[] bytes;

    public void changeTextToBinary(String inputText) {
        bytes = inputText.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        System.out.println("'" + inputText + "' to binary: " + binary);

    }

    //TODO chceck if the string is not longer than picture


}
