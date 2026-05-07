package command;

import model.Organization;
import java.util.LinkedList;

/** Информация о коллекции. */
public class InfoCommand implements Command {
    private LinkedList<Organization> collection;

    public InfoCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Тип: LinkedList<Organization>");
        System.out.println("Элементов: " + collection.size());
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}