package tech.antee.cba.tasks.bwt_mtf.bwt;

import java.util.ArrayList;
import java.util.Collections;

public class BwtDecodingTable {

    private final ArrayList<TableVector> table;
    private final String text;
    private final int length;

    public BwtDecodingTable(String text) {
        this.text = text;
        this.length = text.length();
        this.table = new ArrayList<>();
        initTable();
    }

    public void buildTable() {
        for (int i = 0; i < length; i++) {
            addToVectors(text);
            Collections.sort(table);
        }
    }

    public String getVector(int position) throws InvalidInputException {
        if(position == -1) throw new InvalidInputException();
        return table.get(position).toString();
    }

    private void initTable() {
        for (int i = 0; i < length; i++) {
            table.add(new TableVector());
        }
    }

    private void addToVectors(String word) {
        char[] chars = word.toCharArray();
        for (int i = 0; i < length; i++) {
            table.get(i).addChar(chars[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(table.get(i).toString() + "\n");
        }
        return result.toString();
    }

    public class TableVector implements Comparable<TableVector> {

        private StringBuilder stringBuilder = new StringBuilder();

        void addChar(char element) {
            stringBuilder = new StringBuilder().append(element).append(stringBuilder.toString());
        }

        @Override
        public int compareTo(TableVector o) {
            return stringBuilder.toString().compareTo(o.toString());
        }

        @Override
        public String toString() {
            return stringBuilder.toString();
        }
    }

}
