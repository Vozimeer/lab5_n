package command;

/**
 * Интерфейс команды.
 */
public interface Command {
    void execute(String[] args);
    String getDescription();
}