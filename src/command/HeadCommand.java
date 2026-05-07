package command;

import model.Organization;
import java.util.LinkedList;

/** Показывает первый элемент. */
public class HeadCommand implements Command {
    private LinkedList<Organization> collection;

    public HeadCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        if (!collection.isEmpty()) {
            System.out.println(collection.getFirst());
        } else {
            System.out.println("Коллекция пуста");
        }
    }

    @Override
    public String getDescription() {
        return "вывести первый элемент коллекции";
    }
}