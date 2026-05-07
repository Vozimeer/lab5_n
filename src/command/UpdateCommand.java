package command;

import model.Organization;
import util.InputHandler;
import java.util.LinkedList;

/**
 * Обновляет элемент по id.
 */
public class UpdateCommand implements Command {
    private LinkedList<Organization> collection;
    private InputHandler inputHandler;

    public UpdateCommand(LinkedList<Organization> collection, InputHandler inputHandler) {
        this.collection = collection;
        this.inputHandler = inputHandler;
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
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId() == id) {
                System.out.println("Введите новые данные для элемента " + id);
                Organization org = inputHandler.readOrganization();
                collection.set(i, new Organization(id, org.getName(), org.getCoordinates(),
                        org.getCreationDate(), org.getAnnualTurnover(), org.getType(), org.getPostalAddress()));
                System.out.println("Обновлено");
                return;
            }
        }
        System.out.println("Элемент с id=" + id + " не найден");
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента по id";
    }
}