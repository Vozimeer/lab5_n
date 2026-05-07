package command;

import model.Organization;
import java.util.LinkedList;

/** Показывает все элементы. */
public class ShowCommand implements Command {
    private LinkedList<Organization> collection;

    public ShowCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        for (Organization org : collection) {
            System.out.println(org);
        }
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}