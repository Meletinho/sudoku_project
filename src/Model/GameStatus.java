package Model;

public enum GameStatus {

    NOT_STARTED("Não iniciado"),
    IMCOMPLETE("Incompleto"),
    COMPLETE("Completo");



    // Construtor privado que recebe os parâmetros para inicializar os campos de cada constante
    private final String description;
    GameStatus(String description) {
        this.description = description;
    }

    // Fornece acesso controlado ao campo description,e mantém o encapsulamento
    public String getDescription() {
        return description;
    }




}
