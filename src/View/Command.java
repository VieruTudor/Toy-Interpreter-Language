package View;

public abstract class Command {
    private final String key;
    private final String description;

    public Command(String _key, String _description) {
        this.key = _key;
        this.description = _description;
    }

    public abstract void execute();

    public String getKey() {
        return this.key;
    }
    public String getDescription(){
        return this.description;
    }
}
