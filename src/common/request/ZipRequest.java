package common.request;

public class ZipRequest extends Request {
    private String zipCode;

    public ZipRequest(String commandName, String zipCode) {
        super(commandName);
        this.zipCode = zipCode;
    }

    public String getZipCode() { return zipCode; }
}