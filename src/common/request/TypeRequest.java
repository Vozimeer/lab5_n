package common.request;

import common.model.OrganizationType;

public class TypeRequest extends Request {
    private OrganizationType type;

    public TypeRequest(String commandName, OrganizationType type) {
        super(commandName);
        this.type = type;
    }

    public OrganizationType getType() { return type; }
}