package server;

import common.model.Organization;
import common.model.OrganizationType;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private LinkedList<Organization> collection = new LinkedList<>();
    private ZonedDateTime initTime;

    public CollectionManager() {
        initTime = ZonedDateTime.now();
    }

    public LinkedList<Organization> getCollection() {
        return collection;
    }

    public void setCollection(LinkedList<Organization> collection) {
        this.collection = collection;
    }

    public String info() {
        return "Тип: LinkedList<Organization>\n" +
                "Время инициализации: " + initTime + "\n" +
                "Элементов: " + collection.size();
    }

    public String show() {
        if (collection.isEmpty()) return "Коллекция пуста";
        return collection.stream()
                .sorted(Comparator.comparing(Organization::getName))
                .map(Organization::toString)
                .collect(Collectors.joining("\n"));
    }

    public synchronized String add(Organization org) {
        collection.add(org);
        return "Добавлен элемент с id=" + org.getId();
    }

    public synchronized String update(long id, Organization org) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId() == id) {
                collection.set(i, new Organization(id, org.getName(), org.getCoordinates(),
                        org.getCreationDate(), org.getAnnualTurnover(), org.getType(), org.getPostalAddress()));
                return "Обновлено";
            }
        }
        return "Элемент с id=" + id + " не найден";
    }

    public synchronized String removeById(long id) {
        boolean removed = collection.removeIf(org -> org.getId() == id);
        return removed ? "Удалён" : "Элемент не найден";
    }

    public synchronized String clear() {
        collection.clear();
        return "Коллекция очищена";
    }

    public String removeFirst() {
        if (!collection.isEmpty()) {
            collection.removeFirst();
            return "Первый элемент удалён";
        }
        return "Коллекция пуста";
    }

    public String head() {
        if (!collection.isEmpty()) {
            return collection.getFirst().toString();
        }
        return "Коллекция пуста";
    }

    public synchronized String removeLower(Organization org) {
        collection.removeIf(o -> o.getAnnualTurnover() < org.getAnnualTurnover());
        return "Элементы, меньшие заданного, удалены";
    }

    public synchronized String removeAllByType(OrganizationType type) {
        collection.removeIf(o -> o.getType() == type);
        return "Элементы типа " + type + " удалены";
    }

    public String averageOfAnnualTurnover() {
        if (collection.isEmpty()) return "Коллекция пуста";
        double avg = collection.stream()
                .mapToDouble(Organization::getAnnualTurnover)
                .average()
                .orElse(0);
        return "Средний оборот: " + avg;
    }

    public String countGreaterThanPostalAddress(String zip) {
        long count = collection.stream()
                .filter(o -> o.getPostalAddress().getZipCode().compareTo(zip) > 0)
                .count();
        return "Элементов с zipCode > " + zip + ": " + count;
    }
}