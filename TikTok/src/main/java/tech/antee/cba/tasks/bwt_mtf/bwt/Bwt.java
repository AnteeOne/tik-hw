package tech.antee.cba.tasks.bwt_mtf.bwt;

public class Bwt {

    public BwtResult encode(String text) {
        BwtEncodingTable bwtEncodingTable = new BwtEncodingTable(text);
        bwtEncodingTable.buildTable();
        bwtEncodingTable.sortTable();
        return new BwtResult(
                bwtEncodingTable.getLastColumn(),
                bwtEncodingTable.getInitialPosition()
        );
    }

    public String decode(BwtResult bwtResult) {
        BwtDecodingTable table = new BwtDecodingTable(bwtResult.text);
        table.buildTable();

        try {
            return table.getVector(bwtResult.initialPosition);
        } catch (InvalidInputException e) {
            return "";
        }
    }



}
