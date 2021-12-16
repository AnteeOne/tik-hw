package tech.antee.cba.services;

import tech.antee.cba.utils.BinaryList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileService {

    public String initialFolder = "initial\\";
    public String compressedFolder = "compressed\\";
    public String decompressedFolder = "uncompressed\\";
    public String blankFile = "blank.txt";
    public String shakespeare = "shakespeare.txt";
    private final String filesPath = "src\\main\\java\\tech\\antee\\cba\\files\\";

    public String getTextFromFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public void saveEncodedTextToFile(String fileName, Map<Character, Integer> frequencies, String bits) {
        try {
            File output = new File(fileName);
            DataOutputStream os = new DataOutputStream(new FileOutputStream(output));
            os.writeInt(frequencies.size());
            for (Character character : frequencies.keySet()) {
                os.writeChar(character);
                os.writeInt(frequencies.get(character));
            }
            int compressedSizeBits = bits.length();
            BinaryList binaryList = new BinaryList(compressedSizeBits);
            for (int i = 0; i < bits.length(); i++) {
                if (bits.charAt(i) != '0') binaryList.setBitToPosition(i, 1);
                else binaryList.setBitToPosition(i, 0);
            }

            os.writeInt(compressedSizeBits);
            os.write(binaryList.arrayOfBytes, 0, binaryList.getSizeInBytes());
            os.flush();
            os.close();

        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public void saveEncodedTextToFile(String fileName, String bits) {
        try {
            File output = new File(fileName);
            DataOutputStream os = new DataOutputStream(new FileOutputStream(output));
            int compressedSizeBits = bits.length();
            BinaryList binaryList = new BinaryList(compressedSizeBits);
            for (int i = 0; i < bits.length(); i++) {
                if (bits.charAt(i) != '0') binaryList.setBitToPosition(i, 1);
                else binaryList.setBitToPosition(i, 0);
            }

            os.writeInt(compressedSizeBits);
            os.write(binaryList.arrayOfBytes, 0, binaryList.getSizeInBytes());
            os.flush();
            os.close();

        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public void getEncodedTextFromFile(String fileName, Map<Character, Integer> frequencies, StringBuilder bits) {
        try {
            File input = new File(fileName);
            DataInputStream os = new DataInputStream(new FileInputStream(input));
            int frequencyTableSize = os.readInt();
            for (int i = 0; i < frequencyTableSize; i++) {
                frequencies.put(os.readChar(), os.readInt());
            }
            int dataSizeBits = os.readInt();
            BinaryList binaryList = new BinaryList(dataSizeBits);
            os.read(binaryList.arrayOfBytes, 0, binaryList.getSizeInBytes());
            os.close();

            for (int i = 0; i < binaryList.len; i++) {
                if (binaryList.getBitFromPosition(i) != 0) bits.append((Object) "1");
                else bits.append((Object) 0);
            }

        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public void getEncodedTextFromFile(String fileName, StringBuilder bits) {
        try {
            File input = new File(fileName);
            DataInputStream os = new DataInputStream(new FileInputStream(input));
            int dataSizeBits = os.readInt();
            BinaryList binaryList = new BinaryList(dataSizeBits);
            os.read(binaryList.arrayOfBytes, 0, binaryList.getSizeInBytes());
            os.close();

            for (int i = 0; i < binaryList.len; i++) {
                if (binaryList.getBitFromPosition(i) != 0) bits.append((Object) "1");
                else bits.append((Object) 0);
            }

        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public void saveDecodedToFile(String fileName, String decoded) throws IOException {
        Files.write(Paths.get(fileName), decoded.getBytes());
    }

    public void saveDecodedBitsToFile(String fileName, String bits) {
        try {
            File output = new File(fileName);
            DataOutputStream os = new DataOutputStream(new FileOutputStream(output));
            int compressedSizeBits = bits.length();
            BinaryList binaryList = new BinaryList(compressedSizeBits);
            for (int i = 0; i < bits.length(); i++) {
                if (bits.charAt(i) != '0') binaryList.setBitToPosition(i, 1);
                else binaryList.setBitToPosition(i, 0);
            }
            os.write(binaryList.arrayOfBytes, 0, binaryList.getSizeInBytes());
            os.flush();
            os.close();

        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

}
