package command;

import model.Organization;
import java.util.LinkedList;

/** Количество элементов с большим postalAddress. */
public class CountGreaterCommand implements Command {
    private LinkedList<Organization> collection;

    public CountGreaterCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Укажите zipCode");
            return;
        }
        String zip = args[0];
        long count = collection.stream()
                .filter(o -> o.getPostalAddress().getZipCode().compareTo(zip) > 0)
                .count();
        System.out.println("Элементов с zipCode > " + zip + ": " + count);
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов с большим postalAddress";
    }
}