package tech.antee.cba.utils;

public class Log {

    public enum Tags {
        HUFFMAN,
        ARITHMETICAL,
        BWT,
        MTF,
        BWTMTF,
        HAMMING,
        TERMINAL,
    }

    public static void m(Tags tag,String message) {
        System.out.println("[" + tag.name() + "] " + message);
    }

    public static void e(Tags tag,String message) {
        System.out.println("[" + tag.name() + "] (ERROR) " + message);
    }

}
