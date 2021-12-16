package tech.antee.cba.tasks.bwt_mtf.bwt;

import java.util.ArrayList;
import java.util.Collections;

public class BwtEncodingTable {

    public String text;
    public int length;

    private ArrayList<String> table;

    public BwtEncodingTable(String text) {
        this.text = text;
        this.length = text.length();
        this.table = new ArrayList<>();
    }

    public void buildTable() {
        String tableText = text;
        for (int i = 0; i < length ; i++) {
            StringBuilder nextString = new StringBuilder();
            nextString.append(tableText.substring(i));
            nextString.append(tableText.substring(0,i));
            table.add(nextString.toString());
        }
    }

    public void sortTable() {
        Collections.sort(table);
    }

    public int getInitialPosition() {
        for (int i = 0; i < length; i++) {
            if (table.get(i).equals(text)) {
                return i;
            }
        }
        return -1;
    }

    public String getLastColumn() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(table.get(i).charAt((int)length - 1));
        }
        return result.toString();
    }

    public ArrayList<String> getTable() {
        return table;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(table.get(i));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

