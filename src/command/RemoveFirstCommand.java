package command;

import model.Organization;
import java.util.LinkedList;

/** Удаляет первый элемент. */
public class RemoveFirstCommand implements Command {
    private LinkedList<Organization> collection;

    public RemoveFirstCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        if (!collection.isEmpty()) {
            collection.removeFirst();
            System.out.println("Первый элемент удалён");
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    @Override
    public String getDescription() {
        return "удалить первый элемент коллекции";
    }
}