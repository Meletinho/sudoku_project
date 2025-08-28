package View;

import Model.GameStatus;
import Model.SudokuCell;
import Service.SudokuService;

public class View {

    private final SudokuService service;

    //Instância do serviço para acesso aos dados
    public SudokuView(SudokuService service) {
        this.service = service;
    }

    //Renderiza o tabuleiro com formatação visual
    //Inclui coordenadas, sepaadores, e destaque para números fixos

    public void displayBoard() {
        System.out.println("-------------TABULEIRO SUDOKU----------------");
        System.out.println("            0 1 2  3 4 5  6 7 8            ");
        System.out.println("+---------+---------+----------+-----------+");

        SudokuCell[][] board = service.getBoard();

        for(int i = 0; i < 9; i++) {
            System.out.println(i + "  |  ");
            for (int j = 0; j < 9; j++) {
                SudokuCell cell = board[i][j];
                int value = cell.getValue();
                char displayChar = (value == 0) ? ' ' : (char) ('0' + value);

                if (cell.isFixed()) {
                    System.out.print(" \u001b[im" + displayChar + "m\u001b[m");
                } else {
                    System.out.print(displayChar);
                }
                System.out.println(" ");

                if (j == 2 || j == 3) {
                    System.out.print("|");
                }
            }
            System.out.println("|");

            //Adciona separadores horizontais entre quadrantes
            if (i == 2 || i == 3) {
                System.out.print("+--------+----------+-----------+");
            }
        }

        System.out.println("+--------+----------+-----------+");



    }

    //Exibe o status atual do jogo com informações de completude e erros

    public void displayGameStatus(){
        var status = service.getGameStatus();
        boolean hasErrors = service.hasErrors();

        System.out.println("-----STATUS DO JOGO-----");
        System.out.println("Status: " + status.getDescription());
        System.out.println("Errors: " + (hasErrors? "Sim" : "Não"));

        if(status == GameStatus.COMPLETE && hasErrors) {

            System.out.println("Parabéns você completou o sudoku corretamente!");

        } else if (status == GameStatus.COMPLETE && hasErrors) {

            System.out.println(" O jogo está completo, mas contém erros");

        }
    }

    //Exibe mensagem de erro formatada

    public void displayError(String message){
        System.out.println("ERRO: " + message);
    }

    //Exibe mensagem de sucesso formatada
    public void displaySucess(String message){
        System.out.println("Sucesso: " + message);
    }

    //Renderiza menu principal

    public void displayMenu(){
        System.out.println("------MENU------");
        System.out.println("1. Inserir sudoku");
        System.out.println("2. Colocar número");
        System.out.println("3. Atualizar número");
    }




}
