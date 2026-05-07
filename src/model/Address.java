package model;

/**
 * Адрес: индекс и локация.
 */
public class Address {
    private String zipCode;
    private Location town;

    /**
     * @param zipCode почтовый индекс, не null
     * @param town    локация, не null
     */
    public Address(String zipCode, Location town) {
        this.zipCode = zipCode;
        this.town = town;
    }

    public String getZipCode() { return zipCode; }
    public Location getTown() { return town; }

    @Override
    public String toString() {
        return zipCode + " " + town;
    }
}