package command;

import model.Organization;
import util.InputHandler;
import java.util.LinkedList;

/** Удаляет элементы, меньшие заданного. */
public class RemoveLowerCommand implements Command {
    private LinkedList<Organization> collection;
    private InputHandler inputHandler;

    public RemoveLowerCommand(LinkedList<Organization> collection, InputHandler inputHandler) {
        this.collection = collection;
        this.inputHandler = inputHandler;
    }

    @Override
    public void execute(String[] args) {
        Organization org = inputHandler.readOrganization();
        collection.removeIf(o -> o.compareTo(org) < 0);
        System.out.println("Элементы, меньшие заданного, удалены");
    }

    @Override
    public String getDescription() {
        return "удалить элементы, меньшие заданного";
    }
}