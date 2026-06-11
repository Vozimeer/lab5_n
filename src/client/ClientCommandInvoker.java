package client;

import common.model.*;

import java.io.*;

import common.request.*;

public class ClientCommandInvoker {
    private InputHandler inputHandler;

    public ClientCommandInvoker(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public Request parseCommand(String line) {
        if (line == null || line.isEmpty()) return null;

        String[] parts = line.trim().split(" ", 2);
        String cmd = parts[0];
        String arg = parts.length > 1 ? parts[1] : "";

        switch (cmd) {
            case "help":
            case "info":
            case "show":
            case "clear":
            case "remove_first":
            case "head":
            case "average_of_annual_turnover":
                return new CommandRequest(cmd);

            case "add":
                return new OrganizationRequest("add", inputHandler.readOrganization());

            case "update":
                long id = Long.parseLong(arg);
                System.out.println("Введите новые данные:");
                Organization org = inputHandler.readOrganization();
                return new OrganizationRequest("update", org, id);

            case "remove_by_id":
                return new IdRequest("remove_by_id", Long.parseLong(arg));

            case "remove_lower":
                return new OrganizationRequest("remove_lower", inputHandler.readOrganization());

            case "remove_all_by_type":
                OrganizationType type = OrganizationType.valueOf(arg.toUpperCase());
                return new TypeRequest("remove_all_by_type", type);

            case "count_greater_than_postal_address":
                return new ZipRequest("count_greater_than_postal_address", arg);

            default:
                System.out.println("Неизвестная команда");
                return null;
        }
    }

    private Organization readOrganizationFromScript(BufferedReader reader) throws IOException {
        String name = reader.readLine();
        if (name == null) throw new IOException("Недостаточно данных в скрипте");
        System.out.println(name.trim());

        long x = Long.parseLong(reader.readLine().trim());
        System.out.println(x);
        Long y = Long.parseLong(reader.readLine().trim());
        System.out.println(y);

        float turnover = Float.parseFloat(reader.readLine().trim().replace(',', '.'));
        System.out.println(turnover);

        String typeStr = reader.readLine();
        if (typeStr == null) throw new IOException("Недостаточно данных в скрипте");
        typeStr = typeStr.trim();
        System.out.println(typeStr);
        OrganizationType type = typeStr.isEmpty() ? null : OrganizationType.valueOf(typeStr.toUpperCase());

        String zipCode = reader.readLine().trim();
        System.out.println(zipCode);

        Integer locX = Integer.parseInt(reader.readLine().trim());
        System.out.println(locX);
        float locY = Float.parseFloat(reader.readLine().trim().replace(',', '.'));
        System.out.println(locY);
        String locName = reader.readLine().trim();
        System.out.println(locName);

        Coordinates coordinates = new Coordinates(x, y);
        Location location = new Location(locX, locY, locName);
        Address address = new Address(zipCode, location);
        return new Organization(name.trim(), coordinates, turnover, type, address);
    }

    public Request parseCommandFromScript(String cmd, String arg, BufferedReader scriptReader) throws IOException {
        switch (cmd) {
            case "add":
                return new OrganizationRequest("add", readOrganizationFromScript(scriptReader));
            case "update":
                long id = Long.parseLong(arg);
                return new OrganizationRequest("update", readOrganizationFromScript(scriptReader), id);
            case "remove_lower":
                return new OrganizationRequest("remove_lower", readOrganizationFromScript(scriptReader));
            default:
                return parseCommand(cmd + (arg.isEmpty() ? "" : " " + arg));
        }
    }
}