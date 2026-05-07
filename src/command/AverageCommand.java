package command;

import model.Organization;
import java.util.LinkedList;

/** Среднее значение annualTurnover. */
public class AverageCommand implements Command {
    private LinkedList<Organization> collection;

    public AverageCommand(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }
        double avg = collection.stream()
                .mapToDouble(Organization::getAnnualTurnover)
                .average()
                .orElse(0);
        System.out.println("Средний оборот: " + avg);
    }

    @Override
    public String getDescription() {
        return "вывести среднее значение annualTurnover";
    }
}