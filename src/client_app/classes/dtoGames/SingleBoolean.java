package client_app.classes.dtoGames;

public class SingleBoolean {

    private boolean value;

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public SingleBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SingleBoolean{" +
                "value=" + value +
                '}';
    }
}
