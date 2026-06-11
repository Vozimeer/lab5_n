package common.request;

import common.model.Organization;

public class OrganizationRequest extends Request {
    private Organization organization;
    private long id;

    public OrganizationRequest(String commandName, Organization organization) {
        super(commandName);
        this.organization = organization;
    }

    public OrganizationRequest(String commandName, Organization organization, long id) {
        super(commandName);
        this.organization = organization;
        this.id = id;
    }

    public Organization getOrganization() { return organization; }
    public long getId() { return id; }
}