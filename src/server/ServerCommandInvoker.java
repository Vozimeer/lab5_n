package server;

import common.request.*;
import common.response.Response;
import common.model.Organization;

public class ServerCommandInvoker {
    private CollectionManager collectionManager;
    private String filename;

    public ServerCommandInvoker(CollectionManager collectionManager, String filename) {
        this.collectionManager = collectionManager;
        this.filename = filename;
    }

    public Response execute(Request request) {
        String cmd = request.getCommandName();
        try {
            switch (cmd) {
                case "help":
                    return new Response(help(), true);
                case "info":
                    return new Response(collectionManager.info(), true);
                case "show":
                    return new Response(collectionManager.show(), true);
                case "add":
                    Organization org = ((OrganizationRequest) request).getOrganization();
                    return new Response(collectionManager.add(org), true);
                case "update":
                    OrganizationRequest updReq = (OrganizationRequest) request;
                    return new Response(collectionManager.update(updReq.getId(), updReq.getOrganization()), true);
                case "remove_by_id":
                    return new Response(collectionManager.removeById(((IdRequest) request).getId()), true);
                case "clear":
                    return new Response(collectionManager.clear(), true);
                case "save":
                    FileManager.saveToFile(filename, collectionManager.getCollection());
                    return new Response("Сохранено в " + filename, true);
                case "remove_first":
                    return new Response(collectionManager.removeFirst(), true);
                case "head":
                    return new Response(collectionManager.head(), true);
                case "remove_lower":
                    Organization lowerOrg = ((OrganizationRequest) request).getOrganization();
                    return new Response(collectionManager.removeLower(lowerOrg), true);
                case "remove_all_by_type":
                    return new Response(collectionManager.removeAllByType(((TypeRequest) request).getType()), true);
                case "average_of_annual_turnover":
                    return new Response(collectionManager.averageOfAnnualTurnover(), true);
                case "count_greater_than_postal_address":
                    return new Response(collectionManager.countGreaterThanPostalAddress(((ZipRequest) request).getZipCode()), true);
                default:
                    return new Response("Неизвестная команда", false);
            }
        } catch (Exception e) {
            return new Response("Ошибка выполнения команды", false);
        }
    }

    private String help() {
        return "help - справка\n" +
                "info - информация\n" +
                "show - показать все\n" +
                "add - добавить\n" +
                "update id - обновить\n" +
                "remove_by_id id - удалить\n" +
                "clear - очистить\n" +
                "remove_first - удалить первый\n" +
                "head - первый элемент\n" +
                "remove_lower - удалить меньшие\n" +
                "remove_all_by_type type - удалить по типу\n" +
                "average_of_annual_turnover - средний оборот\n" +
                "count_greater_than_postal_address zip - количество";
    }
}