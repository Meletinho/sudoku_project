package Model;

public class SudokoCell {

    private int value;
    private final boolean isFixed;

    public SudokoCell(int value, boolean isFixed) {
        this.value = value;
        this.isFixed = isFixed;
    }

    public int getValue() {
        return value;
    }
    public boolean isFixed() {
        return isFixed;
    }
    public void setValue(int value) {
        this.value = value;
    }

}
