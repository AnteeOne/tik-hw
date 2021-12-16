package tech.antee.cba.tasks.hamming;

import tech.antee.cba.utils.BinaryList;
import tech.antee.cba.utils.BinaryList.SevenBits;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Hamming {

    public String encodeHamming(String text, boolean makeError) {
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        int bitsCount = textBytes.length * 8;
        int hammingIterationsCount = bitsCount / 4;

        StringBuilder result = new StringBuilder();
        BinaryList binaryList = new BinaryList(bitsCount, textBytes);

        for (int i = 0; i < hammingIterationsCount; i++) {
            int startIndex = i * 4;
            BinaryList.FourBits fourBits = binaryList.getFourBits(startIndex);
            result.append(encodeFourBits(fourBits));
        }

        if (makeError) {
            return makeError(result.toString());
        }

        return result.toString();
    }

    public String encodeFourBits(BinaryList.FourBits fourBits) {
        StringBuilder result = new StringBuilder();
        int p1 = (fourBits.d1 + fourBits.d2 + fourBits.d4) % 2;
        int p2 = (fourBits.d1 + fourBits.d3 + fourBits.d4) % 2;
        int p3 = (fourBits.d2 + fourBits.d3 + fourBits.d4) % 2;

        result.append(p1);
        result.append(p2);
        result.append(fourBits.d1);
        result.append(p3);
        result.append(fourBits.d2);
        result.append(fourBits.d3);
        result.append(fourBits.d4);
        return result.toString();

    }

    public String makeError(String result) {
        // TryCatch block for empty files handling
        try {
            char[] characters = result.toCharArray();
            int bound = characters.length - 1;
            int randomPosition = new Random(System.currentTimeMillis()).nextInt(bound);
            char randomChar = characters[randomPosition];
            if (randomChar == '0') {
                characters[randomPosition] = '1';
            } else {
                characters[randomPosition] = '0';
            }
            return new String(characters);
        } catch (Exception e) {
            return result;
        }

    }

    public String decodeHamming(String bits) {
        StringBuilder result = new StringBuilder();

        int bitsCount = bits.length();
        int iterations = bitsCount / 7;
        for (int i = 0; i < iterations; i++) {
            int startIndex = i * 7;
            int endIndex = (i+1) * 7;
            result.append(decodeSevenBits(bits.substring(startIndex,endIndex)));
        }
        return result.toString();
    }

    public String decodeSevenBits(String subString) {

        SevenBits bits = new SevenBits(
                getInt(subString.charAt(0)),
                getInt(subString.charAt(1)),
                getInt(subString.charAt(2)),
                getInt(subString.charAt(3)),
                getInt(subString.charAt(4)),
                getInt(subString.charAt(5)),
                getInt(subString.charAt(6))
        );
        return bits.getDecoded();
    }

    public int getInt(char bit) {
        return Integer.parseInt(String.valueOf(bit));
    }


}
