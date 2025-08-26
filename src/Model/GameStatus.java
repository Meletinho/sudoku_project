package Model;

public enum GameStatus {

    NOT_STARTED("Não iniciado"),
    IMCOMPLETE("Incompleto"),
    COMPLETE("Completo");

    private final String description;
    GameStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    


}
