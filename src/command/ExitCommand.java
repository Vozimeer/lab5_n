package command;

/** Завершает программу без сохранения. */
public class ExitCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Выход без сохранения");
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "завершить программу без сохранения";
    }
}