package command;

import model.Organization;
import util.InputHandler;
import java.util.*;

/**
 * Управляет регистрацией и вызовом команд.
 */
public class CommandInvoker {
    private Map<String, Command> commands = new LinkedHashMap<>();
    private LinkedList<Organization> collection;
    private InputHandler inputHandler;
    private InputHandler originalInputHandler;
    private Set<String> runningScripts = new HashSet<>();

    public CommandInvoker(LinkedList<Organization> collection, String filename, Scanner scanner) {
        this.collection = collection;
        this.inputHandler = new InputHandler(scanner, true);
        this.originalInputHandler = this.inputHandler;

        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(collection));
        commands.put("show", new ShowCommand(collection));
        commands.put("add", new AddCommand(collection, inputHandler));
        commands.put("update", new UpdateCommand(collection, inputHandler));
        commands.put("remove_by_id", new RemoveByIdCommand(collection));
        commands.put("clear", new ClearCommand(collection));
        commands.put("save", new SaveCommand(collection, filename));
        commands.put("execute_script", new ExecuteScriptCommand(this, runningScripts));
        commands.put("exit", new ExitCommand());
        commands.put("remove_first", new RemoveFirstCommand(collection));
        commands.put("head", new HeadCommand(collection));
        commands.put("remove_lower", new RemoveLowerCommand(collection, inputHandler));
        commands.put("remove_all_by_type", new RemoveAllByTypeCommand(collection));
        commands.put("average_of_annual_turnover", new AverageCommand(collection));
        commands.put("count_greater_than_postal_address", new CountGreaterCommand(collection));
    }

    public void execute(String commandName, String[] args) {
        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(args);
        } else {
            System.out.println("Неизвестная команда. help - список команд.");
        }
    }

    public void updateInputHandler(InputHandler newHandler) {
        this.inputHandler = newHandler;
        commands.put("add", new AddCommand(collection, inputHandler));
        commands.put("update", new UpdateCommand(collection, inputHandler));
        commands.put("remove_lower", new RemoveLowerCommand(collection, inputHandler));
    }

    public void restoreInputHandler() {
        updateInputHandler(originalInputHandler);
    }
}