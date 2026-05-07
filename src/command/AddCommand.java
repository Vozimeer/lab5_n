package command;

import model.Organization;
import util.InputHandler;
import java.util.LinkedList;

/** Добавляет новый элемент. */
public class AddCommand implements Command {
    private LinkedList<Organization> collection;
    private InputHandler inputHandler;

    public AddCommand(LinkedList<Organization> collection, InputHandler inputHandler) {
        this.collection = collection;
        this.inputHandler = inputHandler;
    }

    @Override
    public void execute(String[] args) {
        Organization org = inputHandler.readOrganization();
        collection.add(org);
        System.out.println("Добавлен элемент с id=" + org.getId());
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}