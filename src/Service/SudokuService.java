package Service;

import Model.SudokuCell;
import Model.GameStatus;

public class SudokuService {
    //Atributos privados
    private SudokuCell[][] board; //Inicializa a matriz 9x9 representando o tabuleiro
    private boolean gameStarted;


    public SudokuService() {
        board = new SudokuCell[9][9]; //Preenche as matrizes 9x9
        gameStarted = false;
        initializeEmptyBoard(); //Inicializa todas as células como vazias
    }

    //Métodos privados de inicialização




    // Cria um tabuleiro 9x9 vazio
    //Todas as células começam com um valor 0 e não são fixas
    //O jogo inicia no estado "não iniciado"
    public void initializeEmptyBoard() {
        for (int i = 0; i < 9; i++) { //Para cada linha
            for (int j = 0; j < 9; j++) { //Para cada coluna
                board[i][j] = new SudokuCell(0, false);  //Cria a célula vazia
            }
        }
    }

    //Limpa completamente o tabuleiro (reinicia para vazio)
    public void clearBoard() {
        initializeEmptyBoard();
    }

    // Inicia um novo jogo com números fIXOS

    //Assinatura do métod, e recebe a matriz 2D com números fixos iniciais
    public void startNewGame(int[][] fixedNumbers){

        clearBoard(); //Limpeza do tabuleiro

        gameStarted = true; //Marca o jogo como iniciado

        //Preenchimento dos números fixos nas Linhas 4-9
        //Preenche os números fixos

        //Use de enhenced for-loop
        //Mais legiível, mais claro que o loop tradicional
        for (int[] numbers : fixedNumbers) {

            int row = numbers[0]; //Pega a linha do número
            int col = numbers[1]; //Pega a coluna do número
            int value = numbers[2];//Pega o valor do número
            board[row][col] = new SudokuCell(value, true); //Coloca o número fixo

            //Exemplo: SE receber {0, 0, 5}, significa: "Coloque o número 5 na linha 0, coluna 0, e deixe ele fixo"

        }

    }

    public boolean addNumber(int row, int col, int value) {
        // Verifica se a posição e o valor são válidos
        if(!isValidPosition(row, col) || !isValidaValue(value)){
            return false; //Se não forem, não deixa colocar
        }

        // Verifica se já tem algum número nessa posição
        if(board[row][col].getValue() != 0){
            return false; // Se já tiver um número, não deixa coloca outro
        }

        //Se tudo der certo! Adciona o número (Não é fixo, o jogador pode remover depois)
        board[row][col] = new SudokuCell(value, false);
        return true;
    }




    //Remove um número do tabuleiro
    public boolean removeNumber(int row, int col) {
        //Verifica se a posição já existe
        if(!isValidPosition(row, col)){
            return false; // Posição inválida
        }
        //Verifica se o número é Fixo (o número veio com o jogo)
        if(board[row][col].isFixed()){
            return false; // Não deixa remover os números fixos
        }

        // Se tudo estiver certo, remove o número (coloca Zero)
        board[row][col] = new SudokuCell(0, false);
        return true;

        //Só deixa remover números que o jogador adcionou
    }

    //Limpa tudo do jogador
    public void clearUserNumbers(){

        for (int i = 0; i < 9; i++) { //Para cada linha
            for (int j = 0; j < 9; j++) { //Para cada coluna
                if(!board[i][j].isFixed()){
                    board[i][j] = new SudokuCell(0, false);
                }
            }
        }

        //É como um botão de reset, que mantém as dicas originais do jogo, mas apaga todas as posições que o jogador preencheu

    }

    public boolean isComplete(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //Se encontrar alguma célula vazia
                if(board[i][j].getValue() == 0){
                    return false;  // Ainda não está completo!
                }
            }

        }
        return true;
    }


    //Verificação de conflitos
    public boolean hasErrors() {
        for (int i = 0; i < 9; i++) {
            //Verifica duplicatas em linhas, colunas e quadrantes
            if (hasDuplicatesInRow(i)) return true;
        }
        //Verifica se tem números repetidos em colunas
        for (int j = 0; j < 9; j++) {
            if (hasDuplicatesInColumn(j)) return true;
        }
        //Verifica se tem números repetidos em quadrantes 3X3
        for (int i = 0; i < 9; i+=3) {
            for (int j = 0; j < 9; j+=3) {
                if(hasDuplicatesInBox(i, j))
                    return true;   //Achou um erro em algum quadrante
            }

        }

        // Para cada grupo (linha, coluna, quadrante), mantém um array booleano para marcar números já vistos
        // Se encontrar um número repetido, retorna true

        return false;
    }


    //Procura duplicates  nas linhas
    private boolean hasDuplicatesInRow(int row) {
        boolean[] seen = new boolean[10];  // Lista para marcar números já vistos

        for (int j = 0; j < 9; j++) {   //Para cada coluna por linha
            int value = board[row][j].getValue();  //Pega o número
            if (value != 0) {   //Se não estiver vazio
                if (seen[value]) {  //Se já viu o número antes
                    return true; //Achou duplicate
                }
                seen[value] = true;         //Marca que viu o número
            }
        }
        return false;  // Nenhuma duplicata encontrada
    }

    private boolean hasDuplicatesInColumn(int col) {
        boolean[] seen = new boolean[10];

        for (int i = 0; i < 9; i++) { //Pega cada linha por coluna
            int value = board[i][col].getValue(); //Pega o valor do número
            if (value != 0) { //SE não estiver vazio
                if (seen[value]) {//Se já viu esse número antes

                    return true; //Duplica encontrada
                }
            seen[value] = true; //Nenhuma duplicata enconstrada nas colunas
            }
        }
        return false;
    }

    //Verifica se existem números duplicados por quadrantes 3x3 específico do tabuleiro
    private boolean hasDuplicatesInBox(int startRow, int startCol) {
        //
        boolean[] seen = new boolean[10];
        //Percorre cada quadrante para verificar se há uma duplicata
        for (int i = startRow; i < startCol + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int value = board[i][j].getValue();   //lê o número da célula
                if (value != 0) {  //Verifica se a célula não está vazia
                    if (seen[value]) {
                        return true; //Duplicata encontrada
                    }
                    seen[value] = true; //Duplicata não encontrada
                }
            }
        }

        return false;
    }


    //Valida se as coordenadas estão dos limites do tabuleiro
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }

    //Valida se os valores estão do permitido
    private boolean isValidaValue(int value) {
        return value >= 1 && value <= 9;
    }

    public GameStatus getGameStatus() {
        if (!gameStarted) {
            return GameStatus.NOT_STARTED;
        } else if (isComplete()) {
            return GameStatus.COMPLETE;
        }else {
            return GameStatus.IMCOMPLETE;
        }
    }

    public boolean isGameStarted(){
        return gameStarted;
    }



}


