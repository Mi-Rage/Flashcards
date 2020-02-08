package flashcards;

public class GameCards {
    private String term = "";
    private String definition = "";

    public GameCards(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }
}
