package command;

import model.Organization;
import java.util.LinkedList;

/**
 * Удаляет элемент по id.
 */
public class RemoveByIdCommand implements Command {
    private LinkedList<Organization> collection;

    public RemoveByIdCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Укажите id");
            return;
        }
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("id должен быть целым числом");
            return;
        }
        collection.removeIf(org -> org.getId() == id);
        System.out.println("Удалён (если существовал)");
    }

    @Override
    public String getDescription() {
        return "удалить элемент по id";
    }
}