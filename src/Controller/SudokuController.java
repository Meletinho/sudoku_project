package Controller;


import Model.GameStatus;
import Service.SudokuService;
import View.SudokuView;

import java.util.Scanner;


public class SudokuController {

    private final SudokuService service;
    private final SudokuView view;
    private final Scanner scanner;
    private boolean running;

    private final int[][] FIXED_NUMBERS = {{0, 0, 9}, {0, 1, 5}, {0, 2, 8}, {0, 5, 2},
            {1, 3, 2}, {1, 4, 5}, {1, 5, 6}, {1, 6, 4},
            {2, 2, 6}, {2, 5, 5}, {2, 6, 1}, {2, 7, 7},
            {3, 0, 6}, {3, 3, 3}, {3, 4, 7}, {3, 5, 8},
            {4, 0, 7}, {4, 1, 8}, {4, 2, 4}, {4, 5, 9}, {4, 6, 3}, {4, 7, 2},
            {5, 3, 4}, {5, 4, 2}, {5, 5, 9}, {5, 7, 8},
            {6, 0, 4}, {6, 1, 9}, {6, 2, 2}, {6, 5, 1},
            {7, 1, 6}, {7, 3, 5}, {7, 4, 8}, {7, 5, 1},
            {8, 1, 1}, {8, 5, 7}, {8, 6, 6}, {8, 7, 3}
    };

    //Construtor - Inicializa dependência e estado
    public SudokuController() {
        this.service = new SudokuService();
        this.view = new SudokuView(service);
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    //Método principal que inicia o loop de execução do jogo

    public void start() {
        System.out.println("Bem-vindo ao Jogo Sudoku!");
        while (running) {
            view.displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> startNewGame();
                case 2 -> addNumber();
                case 3 -> removeNumber();
                case 4 -> viewGame();
                case 5 -> checkStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 0 -> exitGame();
                default -> view.displayError("Opção inválida!");

            }
        }

    }

    private boolean isGameStarted() {
        if (service.getGameStatus() == GameStatus.NOT_STARTED) {
            view.displayError("O jogo não foi iniciado. Use a opção 1 primeiro.");
            return false;
        }
        return true;
    }


    private void startNewGame() {
        if(!isGameStarted()) return;
        view.displayBoard();
        System.out.println("\n=== ADICIONAR NÚMERO ===");

        int row = getCoordinate("linha (0 - 8): ");
        int col = getCoordinate("coluna (0 -8): ");
        int value = getNumberValue();

        boolean sucess = service.addNumber(row, col, value);
        if(!sucess) {
            view.displaySucess("Números adicionados.");
            view.displayBoard();
        }
        else{
            view.displayError("Não foi possível adicionar um numero!");
            view.displayError("Posição inválida!");
        }


    }

    public void addNumber() {
        if(!isGameStarted()){
            view.displayBoard();
            System.out.println("\n=== ADICIONAR NUMERO ===");
            int row = getCoordinate("linha (0 - 8): ");
            int col = getCoordinate("coluna (0 -8): ");

            boolean sucess = service.removeNumber(row, col);
            if(!sucess) {
                view.displaySucess("Você adcionou um numero!");
                view.displayBoard();
            }else {
                view.displayError("Número não adcionado");
            }
        }
    }

    //Fluxo para remover número: solicita posição e válida
    public void removeNumber(){
        if(!isGameStarted()) return;
        view.displayBoard();
        System.out.println("\n=== REMOVER NUMERO ===");

        int row = getCoordinate("Linha (0 - 8)");
        int col = getCoordinate("Coluna (0 - 8)");

        boolean sucess = service.removeNumber(row, col);
        if(!sucess) {
            view.displaySucess("Número removido.");
            view.displayBoard();
        }
        else{
            view.displayError("Não foi possível remover o numero!");
            view.displayError("Pode ser um número fixo.");
        }


    }

    //Exibe o tabuleiro atual

    public void viewGame(){
        if(!isGameStarted()) return;
        view.displayBoard();

    }

    private void checkStatus() {
        view.displayGameStatus();
    }

    private void clearGame() {
        if(!isGameStarted()) return;
        service.clearUserNumbers();
        view.displaySucess("Todos os números do usuário foram removidos!");
        view.displayBoard();

    }

    private void finishGame() {
        if(!isGameStarted()) return;
        if(service.isComplete() && !service.hasErrors()){
            view.displaySucess("Parabéns! Você completou o Sudoku");
            running = false;
        }

        else{
            view.displayError("O jogo não está completo!. Continue Jogando");
            view.displayGameStatus();
        }

    }


    public void exitGame() {
        System.out.println("Obrigado por jogar!");
        running = false;
    }

    //Métodos auxiliares
    private int getCoordinate(String prompt) {
        int coordinate = -1;
        do{
            System.out.print("Digite a: " +prompt);
            coordinate = getIntInput();
        }while(coordinate < 0 || coordinate > 8);
        return coordinate;
    }

    //Solicita e valida o valor numérico (1 - 9)

    private int getNumberValue() {
        int value;
        do{
            System.out.print("Digite um numero: ");
            value = getIntInput();
        }while(value < 1 || value > 9);
        return value;
    }
    private int getIntInput() {
        while(!scanner.hasNextInt()) {
            System.out.print("Digite um numero: ");
            scanner.nextInt();
        }
        return scanner.nextInt();
    }




}

