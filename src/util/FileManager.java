package util;

import model.*;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.LinkedList;

/**
 * Чтение и запись коллекции в XML-файл.
 */
public class FileManager {

    /**
     * Загружает организации из XML-файла.
     *
     * @param filename имя файла
     * @return список организаций
     */
    public static LinkedList<Organization> loadFromFile(String filename) {
        LinkedList<Organization> list = new LinkedList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Файл " + filename + " не существует, будет создан новый");
            return list;
        }
        if (!file.canRead()) {
            System.out.println("Нет прав на чтение файла " + filename);
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder xml = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                xml.append(line.trim());
            }
            parseOrganizations(xml.toString(), list);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла");
        }
        return list;
    }

    private static void parseOrganizations(String xml, LinkedList<Organization> list) {
        int pos = 0;
        while ((pos = xml.indexOf("<Organization>", pos)) != -1) {
            int end = xml.indexOf("</Organization>", pos);
            if (end == -1) break;
            String orgXml = xml.substring(pos + 14, end);
            Organization org = parseOrganization(orgXml);
            if (org != null) list.add(org);
            pos = end + 15;
        }
    }

    private static Organization parseOrganization(String xml) {
        try {
            long id = Long.parseLong(getTag("id", xml));
            String name = getTag("name", xml);
            if (name.isEmpty()) return null;

            String coordsXml = getTag("Coordinates", xml);
            long x = Long.parseLong(getTag("x", coordsXml));
            Long y = Long.parseLong(getTag("y", coordsXml));
            Coordinates coordinates = new Coordinates(x, y);

            ZonedDateTime creationDate = ZonedDateTime.parse(getTag("creationDate", xml));
            float turnover = Float.parseFloat(getTag("annualTurnover", xml));

            String typeStr = getTag("type", xml);
            OrganizationType type = typeStr.isEmpty() ? null : OrganizationType.valueOf(typeStr);

            String addrXml = getTag("Address", xml);
            String zipCode = getTag("zipCode", addrXml);

            String locXml = getTag("Location", addrXml);
            Integer locX = Integer.parseInt(getTag("x", locXml));
            float locY = Float.parseFloat(getTag("y", locXml));
            String locName = getTag("name", locXml);
            Location town = new Location(locX, locY, locName);
            Address address = new Address(zipCode, town);

            return new Organization(id, name, coordinates, creationDate, turnover, type, address);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getTag(String tag, String xml) {
        String open = "<" + tag + ">";
        String close = "</" + tag + ">";
        int start = xml.indexOf(open);
        int end = xml.indexOf(close);
        if (start == -1 || end == -1) return "";
        return xml.substring(start + open.length(), end);
    }

    /**
     * Сохраняет коллекцию в XML-файл.
     *
     * @param filename имя файла
     * @param list     коллекция организаций
     */
    public static void saveToFile(String filename, LinkedList<Organization> list) {
        File file = new File(filename);
        if (file.exists() && !file.canWrite()) {
            System.out.println("Нет прав на запись в файл " + filename);
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<Organizations>");
            for (Organization org : list) {
                writer.println(toXml(org));
            }
            writer.println("</Organizations>");
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл");
        }
    }

    private static String toXml(Organization org) {
        StringBuilder sb = new StringBuilder();
        sb.append("  <Organization>\n");
        sb.append("    <id>").append(org.getId()).append("</id>\n");
        sb.append("    <name>").append(escape(org.getName())).append("</name>\n");
        sb.append("    <Coordinates>\n");
        sb.append("      <x>").append(org.getCoordinates().getX()).append("</x>\n");
        sb.append("      <y>").append(org.getCoordinates().getY()).append("</y>\n");
        sb.append("    </Coordinates>\n");
        sb.append("    <creationDate>").append(org.getCreationDate()).append("</creationDate>\n");
        sb.append("    <annualTurnover>").append(org.getAnnualTurnover()).append("</annualTurnover>\n");
        sb.append("    <type>").append(org.getType() != null ? org.getType() : "").append("</type>\n");
        sb.append("    <Address>\n");
        sb.append("      <zipCode>").append(escape(org.getPostalAddress().getZipCode())).append("</zipCode>\n");
        sb.append("      <Location>\n");
        sb.append("        <x>").append(org.getPostalAddress().getTown().getX()).append("</x>\n");
        sb.append("        <y>").append(org.getPostalAddress().getTown().getY()).append("</y>\n");
        sb.append("        <name>").append(escape(org.getPostalAddress().getTown().getName())).append("</name>\n");
        sb.append("      </Location>\n");
        sb.append("    </Address>\n");
        sb.append("  </Organization>");
        return sb.toString();
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}