package tech.antee.cba.tasks.bwt_mtf.bwt;

public class BwtResult {
    public String text;
    public int initialPosition;

    public BwtResult(String text, int initialPosition) {
        this.text = text;
        this.initialPosition = initialPosition;
    }

    public String getText() {
        return text;
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    @Override
    public String toString() {
        return "BwtResult{" +
                "text='" + text + '\'' +
                ", initialPosition=" + initialPosition +
                '}';
    }
}