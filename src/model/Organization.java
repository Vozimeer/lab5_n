package model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Организация - основной элемент коллекции.
 * Сортировка по умолчанию - по annualTurnover.
 */
public class Organization implements Comparable<Organization> {
    private long id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private float annualTurnover;
    private OrganizationType type;
    private Address postalAddress;

    private static long nextId = 1;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    /**
     * Создание новой организации, id и creationDate генерируются автоматически.
     */
    public Organization(String name, Coordinates coordinates, float annualTurnover,
                        OrganizationType type, Address postalAddress) {
        this.id = nextId++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    /**
     * Восстановление организации из файла с указанием всех полей.
     */
    public Organization(long id, String name, Coordinates coordinates,
                        ZonedDateTime creationDate, float annualTurnover,
                        OrganizationType type, Address postalAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
        if (id >= nextId) nextId = id + 1;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public ZonedDateTime getCreationDate() { return creationDate; }
    public float getAnnualTurnover() { return annualTurnover; }
    public OrganizationType getType() { return type; }
    public Address getPostalAddress() { return postalAddress; }

    @Override
    public int compareTo(Organization other) {
        return Float.compare(this.annualTurnover, other.annualTurnover);
    }

    @Override
    public String toString() {
        String formattedDate = creationDate.format(FORMATTER);
        return "ID: " + id + ", name: " + name + ", coords: " + coordinates +
                ", created: " + formattedDate + ", turnover: " + annualTurnover +
                ", type: " + (type != null ? type : "null") + ", address: " + postalAddress;
    }
}