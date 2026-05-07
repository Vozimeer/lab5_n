import model.Organization;
import util.FileManager;
import command.CommandInvoker;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Главный класс приложения.
 * Загружает коллекцию из файла и запускает интерактивный режим.
 */
public class Main {
    /**
     * Точка входа. Читает переменную окружения INPUT_XML,
     * загружает данные и запускает цикл обработки команд.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        String filename = System.getenv("INPUT_XML");
        if (filename == null || filename.isEmpty()) {
            System.out.println("Переменная окружения INPUT_XML не задана");
            System.exit(1);
        }

        LinkedList<Organization> collection = FileManager.loadFromFile(filename);

        Scanner scanner = new Scanner(System.in);
        CommandInvoker invoker = new CommandInvoker(collection, filename, scanner);

        System.out.println("Программа запущена. Введите help для справки.");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(" ", 2);
            String command = parts[0];
            String[] cmdArgs = parts.length > 1 ? new String[]{parts[1]} : new String[]{};
            invoker.execute(command, cmdArgs);
        }
    }
}