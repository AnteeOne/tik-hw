package tech.antee.cba.services;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class FrequencyService {

    public HashMap<Character, Double> countProbabilities(String text) {
        long length = text.length();
        HashMap<Character, Integer> frequencies = getSymbolsCounts(text);
        return countProbabilities(frequencies,length);
    }

    public HashMap<Character, Double> countProbabilities(HashMap<Character, Integer> frequencies, long length) {
        HashMap<Character, Double> result = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            result.put(entry.getKey(), entry.getValue() * 1.0 / length);
        }
        return result;
    }

    public String countAlphabet(String text) {
        HashSet<Character> alphabet = new HashSet<>();
        StringBuilder result = new StringBuilder();
        for (char c: text.toCharArray()) {
            if (!alphabet.contains(c)) {
                alphabet.add(c);
                result.append(c);
            }
        }
        return result.toString();
    }

    public HashMap<Character, Integer> getSymbolsCounts(String text) {
        HashMap<Character, Integer> result = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            Character symbol = text.charAt(i);
            result.merge(symbol, 1, Integer::sum);
        }
        return result;
    }

}
