package Model;

public class SudokuCell {


    private int value; //Valor da célula (0 = vazia 1-9 = preenchida)
    private final boolean isFixed; //INidica se é um número fixo (não pode ser removido)

    //Connstrutor: Inicializa célula com valor status de fixação
    public SudokuCell(int value, boolean isFixed) {
        this.value = value;
        this.isFixed = isFixed;
    }


    //Getters e setters que permitem acesso controlado aos atributos
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
