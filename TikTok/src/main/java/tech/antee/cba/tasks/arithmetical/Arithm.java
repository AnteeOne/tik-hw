package tech.antee.cba.tasks.arithmetical;

import tech.antee.cba.services.FrequencyService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Arithm {

    private HashMap<Character, Segment> defineSegments(HashMap<Character, Double> probabilities) {
        HashMap<Character, Segment> result = new HashMap<>();
        double l = 0;
        for (Entry<Character, Double> entry : probabilities.entrySet()) {
            Character character = entry.getKey();
            Double probability = entry.getValue();
            Segment segment = new Segment(
                    l,
                    l + probability
            );
            result.put(character, segment);
            l = segment.right;
        }
        return result;
    }

    public double arithmeticTrivialCoding(String text, HashMap<Character, Double> probabilities) {
        HashMap<Character, Segment> segments = defineSegments(probabilities);
        double left = 0;
        double right = 1;
        for (int i = 0; i < text.length(); i++) {
            char symb = text.charAt(i);
            double newRight = left + (right - left) * segments.get(symb).right;
            double newLeft = left + (right - left) * segments.get(symb).left;
            left = newLeft;
            right = newRight;
        }
        return (left + right) / 2;
    }

    public String arithmeticTrivialDecoding(double code, HashMap<Character, Double> probabilities, long textSize) {
        HashMap<Character, Segment> segments = defineSegments(probabilities);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < textSize; i++) {
            for (Map.Entry<Character, Segment> entry : segments.entrySet()) {
                Character character = entry.getKey();
                Segment segment = entry.getValue();
                if (code >= segment.left && code < segment.right) {
                    stringBuilder.append(character);
                    code = (code - segment.left) / (segment.right - segment.left);
                    break;
                }
            }
        }
        return stringBuilder.toString();
    }

    public BigDecimal arithmeticCoding(String text, HashMap<Character, Double> probabilities) {
        HashMap<Character, Segment> segments = defineSegments(probabilities);
        BigDecimal left = new BigDecimal(0);
        BigDecimal right = new BigDecimal(1);
        for (int i = 0; i < text.length(); i++) {
            char symb = text.charAt(i);
            BigDecimal newRight = left.add((right.subtract(left)).multiply(BigDecimal.valueOf(segments.get(symb).right)));
            BigDecimal newLeft = left.add((right.subtract(left)).multiply(BigDecimal.valueOf(segments.get(symb).left)));
            left = newLeft;
            right = newRight;
        }
        return (left.add(right)).divide(BigDecimal.valueOf(2));
    }

    public String arithmeticDecoding(BigDecimal code, HashMap<Character, Double> probabilities, long textSize) {
        HashMap<Character, Segment> segments = defineSegments(probabilities);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < textSize; i++) {
            for (Map.Entry<Character, Segment> entry : segments.entrySet()) {
                Character character = entry.getKey();
                Segment segment = entry.getValue();
                if (code.compareTo(BigDecimal.valueOf(segment.left)) >= 0 && code.compareTo(BigDecimal.valueOf(segment.right)) < 0) {
                    stringBuilder.append(character);
                    code = code.subtract(BigDecimal.valueOf(segment.left))
                            .divide(BigDecimal.valueOf(segment.right).subtract(BigDecimal.valueOf(segment.left)));
                    break;
                }
            }
        }
        return stringBuilder.toString();
    }

    //todo: adaptive arithmetics
//    public String arithmeticAdaptiveCoding(String text, HashMap<Character, Double> probabilities) {
//        HashMap<Character, Segment> segments = defineSegments(probabilities);
//        StringBuilder result = new StringBuilder();
//        int left = 0;
//        int right = 9999;
//        for (int i = 0; i < text.length(); i++) {
//            if(isPrefixesEquals(left,right)) {
//                shiftInt(new IntPair(left,right),result);
//            }
//            char symb = text.charAt(i);
//            int newLeft = (int)(left + (right - left + 1) * segments.get(symb).left);
//            int newRight = (int)(left + (right - left + 1) * segments.get(symb).right) - 1;
//            left = newLeft;
//            right = newRight;
//        }
//        result.append(left);
//        return result.toString();
//    }
//
//    public IntPair shiftInt(IntPair pair, StringBuilder stringBuilder) {
//        System.out.println(pair.toString());
//        StringBuilder leftBuilder = new StringBuilder(String.valueOf(pair.left));
//        StringBuilder rightBuilder = new StringBuilder(String.valueOf(pair.right));
//        char commonInt = leftBuilder.charAt(0);
//        System.out.println("Commom int = " + commonInt);
//        stringBuilder.append(commonInt);
//        leftBuilder.deleteCharAt(0);
//        leftBuilder.append(0);
//        rightBuilder.deleteCharAt(0);
//        rightBuilder.append(9);
//        IntPair result = new IntPair(Integer.parseInt(leftBuilder.toString()), Integer.parseInt(rightBuilder.toString()));
//        System.out.println("result = " + result.toString());
//        return result;
//    }
//
//    public class IntPair {
//        int left;
//        int right;
//
//        public IntPair(int left, int right) {
//            this.left = left;
//            this.right = right;
//        }
//
//        @Override
//        public String toString() {
//            return "IntPair{" +
//                    "left=" + left +
//                    ", right=" + right +
//                    '}';
//        }
//    }
//
//    public boolean isPrefixesEquals(int left,int right) {
//        return String.valueOf(left).charAt(0) == String.valueOf(right).charAt(0);
//    }

}

