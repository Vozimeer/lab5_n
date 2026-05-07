package command;

import model.Organization;
import model.OrganizationType;
import java.util.LinkedList;

/** Удаляет все элементы указанного типа. */
public class RemoveAllByTypeCommand implements Command {
    private LinkedList<Organization> collection;

    public RemoveAllByTypeCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Укажите тип");
            return;
        }
        try {
            OrganizationType type = OrganizationType.valueOf(args[0].toUpperCase());
            collection.removeIf(o -> o.getType() == type);
            System.out.println("Элементы типа " + type + " удалены");
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный тип. Допустимые: COMMERCIAL, TRUST, PRIVATE_LIMITED_COMPANY");
        }
    }

    @Override
    public String getDescription() {
        return "удалить элементы по типу";
    }
}