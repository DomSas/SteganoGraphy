# `Steganography`

This program encrypts message to PNG image and decrypts it back. It uses JAVAFX with JDK 1.8.

**How it works – encoding:**

1. Message which is being encoded is changed to binary form.
2. Program uses LSB (least significant bit) from PNG image and
substitutes those bits with user's message.
3. First 8 bits will change to letter "Y" in binary after successful
 encryption. Next 16 will store the value of the length of encrypted
 message.

 **How it works – decoding:**

1. Program checks if the first 8 bits contain letter "Y" in binary.
2. If yes, it reads next 16 bits to see how long the message is.
3. Then it reads the bits and changes them back from binary.

**Possible problems:**

- The length of message can be up to 16^2 in binary (due to 16 bits representing the
length of message). It can be solved by changing this value to more bits.
- Program can work just with PNG. Other formats use compressions so the
values of bits would be changed.
