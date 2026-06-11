package common.request;

public class IdRequest extends Request {
    private long id;

    public IdRequest(String commandName, long id) {
        super(commandName);
        this.id = id;
    }

    public long getId() { return id; }
}