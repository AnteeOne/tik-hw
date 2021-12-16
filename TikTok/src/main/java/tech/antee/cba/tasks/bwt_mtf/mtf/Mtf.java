package tech.antee.cba.tasks.bwt_mtf.mtf;

import java.util.LinkedList;
import java.util.List;

public class Mtf {

    public List<Integer> encode(String text, String textCharacters){
        List<Integer> encoded = new LinkedList<>();
        StringBuilder alphabet = new StringBuilder(textCharacters);
        for(char c : text.toCharArray()){
            int i = alphabet.indexOf("" + c);
            encoded.add(i);
            alphabet.deleteCharAt(i).insert(0, c);
        }
        return encoded;
    }

    public String decode(List<Integer> encoded, String textCharacters){
        StringBuilder output = new StringBuilder();
        StringBuilder alphabet = new StringBuilder(textCharacters);
        for(int i : encoded){
            char c = alphabet.charAt(i);
            output.append(c);
            alphabet.deleteCharAt(i).insert(0, c);
        }
        return output.toString();
    }

}
