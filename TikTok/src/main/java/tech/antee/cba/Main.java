package tech.antee.cba;

import tech.antee.cba.services.FileService;
import tech.antee.cba.services.FrequencyService;
import tech.antee.cba.utils.Log;
import tech.antee.cba.tasks.arithmetical.Arithm;
import tech.antee.cba.tasks.bwt_mtf.bwt.Bwt;
import tech.antee.cba.tasks.bwt_mtf.bwt.BwtResult;
import tech.antee.cba.tasks.bwt_mtf.mtf.Mtf;
import tech.antee.cba.tasks.hamming.Hamming;
import tech.antee.cba.tasks.huffman.Huffman;
import tech.antee.cba.tasks.huffman.Node;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        runApplication();
    }

    public static void runApplication() {
        Scanner scanner = new Scanner(System.in);
        printLogo();
        Log.m(Log.Tags.TERMINAL,"Welcome to CBA 1.0 archiver.");
        Log.m(Log.Tags.TERMINAL,"Write /help to see available commands");
        while (true) {
            try {
                String[] command = scanner.nextLine().split(" ");
                switch (command[0]) {
                    case "/huf" : {
                        Log.m(Log.Tags.HUFFMAN,"Starting algorithm...");
                        huffman(command[1]);
                        Log.m(Log.Tags.HUFFMAN,"Done. Please check your generated files");
                        break;
                    }
                    case "/ari" : {
                        Log.m(Log.Tags.ARITHMETICAL,"Starting algorithm...");
                        arithmetical(command[1]);
                        Log.m(Log.Tags.ARITHMETICAL,"Done. Please check your generated files");
                        break;
                    }
                    case "/bwtmtf" : {
                        Log.m(Log.Tags.BWTMTF,"Starting algorithm...");
                        bwtWithMtf(command[1]);
                        Log.m(Log.Tags.BWTMTF,"Done. Please check your generated files");
                        break;
                    }
                    case "/ham" : {
                        Log.m(Log.Tags.HAMMING,"Starting algorithm...");
                        hamming(command[1]);
                        Log.m(Log.Tags.HAMMING,"Done. Please check your generated files");
                        break;
                    }
                    case "/help" : {
                        Log.m(Log.Tags.TERMINAL,"Getting documentation...");
                        help();
                        break;
                    }
                    case "/exit" : {
                        System.exit(0);
                        break;
                    }
                    case "\n" : {
                        break;
                    }
                    default: {
                        Log.m(Log.Tags.TERMINAL,"Invalid command. Write /help to see available commands");
                        break;
                    }
                }
            }
            catch (NoSuchFileException | AccessDeniedException e) {
                Log.m(Log.Tags.TERMINAL,"File error!. Please check path to your file");
            }
            catch (Exception e) {
                Log.m(Log.Tags.TERMINAL,"Invalid command. Write /help to see available commands");
            }
        }
    }

    public static void help() {
        System.out.println("-------------------------------------");
        System.out.println("|           Documentation           |");
        System.out.println("-------------------------------------");
        System.out.println("|  Commands: |      Description:    |");
        System.out.println("-------------------------------------");
        System.out.println("|   /huf     | Huffman impl         |");
        System.out.println("-------------------------------------");
        System.out.println("|   /ari     | Arithmetical with    |");
        System.out.println("|            | BigDecimal impl      |");
        System.out.println("-------------------------------------");
        System.out.println("|   /bwtmtf  | BWT with MTF impl    |");
        System.out.println("-------------------------------------");
        System.out.println("|   /ham     | Hamming algorithm    |");
        System.out.println("-------------------------------------");
        System.out.println("|   /exit    | Close program        |");
        System.out.println("-------------------------------------");
        System.out.println("| Usage:    /COMMAND FILE_PATH      | ");
        System.out.println("| Like: /huf usr/bin/files/file.txt |");
        System.out.println("-------------------------------------");
    }

    public static void printLogo() {
        System.out.println("          ───▄▄▄\n" +
                           "          ─▄▀░▄░▀▄\n" +
                           "          ─█░█▄▀░█\n" +
                           "          ─█░▀▄▄▀█▄█▄▀\n" +
                           "          ▄▄█▄▄▄▄███▀\n");
        System.out.println(
                           "________________________________");
        System.out.println("|                              |");
        System.out.println("|      CBA ARCHIVER V 1.0      |");
        System.out.println("|      Made by Antee (c)       |");
        System.out.println("|            2021              |");
        System.out.println("________________________________");
        System.out.println("\n");
    }

    public static void huffman(String filePath) throws IOException {
        FileService fileService = new FileService();
        Huffman huf = new Huffman();
        String text = fileService.getTextFromFile(filePath);

        FrequencyService frequencyService = new FrequencyService();
        HashMap<Character, Integer> symbolCounts = frequencyService.getSymbolsCounts(text);
        ArrayList<Node> encodedCodeNodes = symbolCounts
                .keySet()
                .stream()
                .map(c -> new Node(c, symbolCounts.get(c)))
                .collect(Collectors.toCollection(ArrayList::new));

        Node tree =  huf.encodeText(encodedCodeNodes);

        HashMap<Character, String> nodesCodes = symbolCounts
                .keySet()
                .stream()
                .collect(Collectors.toMap(c -> c, c -> tree.nodeCode(c, ""), (a, b) -> b, HashMap::new));

        StringBuilder result = new StringBuilder();

        int characterPosition = 0;
        while (characterPosition < text.length()) {
            result.append(nodesCodes.get(text.charAt(characterPosition))); // Getting from hashMap O(c) .. O(n)
            characterPosition++;
        }
        fileService.saveEncodedTextToFile(filePath + ".huf.encoded", symbolCounts, result.toString());



        HashMap<Character, Integer> encodedSymbolCounts = new HashMap<>();
        result = new StringBuilder();

        ArrayList<Node> decodedTreeNodes;
        fileService.getEncodedTextFromFile(filePath + ".huf.encoded", encodedSymbolCounts, result);

        decodedTreeNodes = encodedSymbolCounts
                .keySet()
                .stream()
                .map(c -> new Node(c, encodedSymbolCounts.get(c)))
                .collect(Collectors.toCollection(ArrayList::new));

        Node deocdedTree = huf.encodeText(decodedTreeNodes);
        String decodedText = huf.decodeText(result.toString(), deocdedTree);
        fileService.saveDecodedToFile(filePath + ".huf.decoded", decodedText);
    }

    public static void arithmetical(String filePath) throws IOException {
        Arithm arithm = new Arithm();
        FileService fileService = new FileService();
        FrequencyService frequencyService = new FrequencyService();

        // read text from file
        String text = fileService.getTextFromFile(filePath);
        HashMap<Character, Double> probabilities = frequencyService.countProbabilities(text);

        // save to file encoded text
        BigDecimal encodedText = arithm.arithmeticCoding(text, probabilities);
        fileService.saveDecodedToFile(filePath + ".ari.encoded", encodedText.toString());
        encodedText = null;
        // read from file encoded text
        encodedText = new BigDecimal(fileService.getTextFromFile(filePath + ".ari.encoded"));

        // save to file decoded text
        String decodedText = arithm.arithmeticDecoding(encodedText, probabilities, text.length());
        fileService.saveDecodedToFile(filePath + ".ari.decoded", decodedText);
    }

    public static void bwtWithMtf(String filePath) throws IOException {
        Bwt bwt = new Bwt();
        Mtf mtf = new Mtf();
        FileService fileService = new FileService();
        FrequencyService freqService = new FrequencyService();

        //IO
        String initialText = fileService.getTextFromFile(filePath);

        // Coding, at the beginning BWT, then - MTF
        BwtResult bwtResult = bwt.encode(initialText);
        String bwtText = bwtResult.text;
        String alphabet = freqService.countAlphabet(bwtText);
        List<Integer> mtfList = mtf.encode(bwtText, alphabet); //todo: add getting file from .decoded file
        StringBuilder encoded = new StringBuilder();
        for (Integer integer : mtfList) {
            encoded.append(integer).append(" ");
        }
        fileService.saveDecodedToFile(filePath + ".bwtmtf.encoded",encoded.toString());
        mtfList.clear();

        //IO
        String encodedText = fileService.getTextFromFile(filePath + ".bwtmtf.encoded");
        Scanner scanner = new Scanner(encodedText);
        while (scanner.hasNextInt()) {
            mtfList.add(scanner.nextInt());
        }

        // Decoding, begin with MTF, end with BWT
        String mtfDecoded = mtf.decode(mtfList, alphabet);
        String bwtDecoded = bwt.decode(new BwtResult(mtfDecoded, bwtResult.initialPosition));
        fileService.saveDecodedToFile(filePath + ".bwtmtf.decoded",bwtDecoded);
    }

    public static void hamming(String filePath) throws IOException {
        try {
            FileService fileService = new FileService();
            String text = fileService.getTextFromFile(filePath);
            Hamming hamming = new Hamming();
            fileService.saveEncodedTextToFile(filePath + ".hamming.encoded", hamming.encodeHamming(text,true));

            StringBuilder bitsBuilder = new StringBuilder();
            fileService.getEncodedTextFromFile(filePath + ".hamming.encoded", bitsBuilder);
            String decodedBits = hamming.decodeHamming(bitsBuilder.toString());
            fileService.saveDecodedBitsToFile(filePath + ".hamming.decoded",decodedBits);
        } catch (Exception e) {
            Log.m(Log.Tags.HAMMING,"File error! Please check your path to the file");
        }

    }

}
