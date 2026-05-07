package command;

import model.Organization;
import java.util.LinkedList;

/** Очищает коллекцию. */
public class ClearCommand implements Command {
    private LinkedList<Organization> collection;

    public ClearCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        collection.clear();
        System.out.println("Коллекция очищена");
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}