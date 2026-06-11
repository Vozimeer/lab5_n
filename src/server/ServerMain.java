package server;

import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        String filename = System.getenv("INPUT_XML");
        if (filename == null || filename.isEmpty()) {
            System.out.println("Переменная INPUT_XML не задана");
            System.exit(1);
        }

        CollectionManager manager = new CollectionManager();
        manager.setCollection(FileManager.loadFromFile(filename));

        ServerCommandInvoker invoker = new ServerCommandInvoker(manager, filename);
        NetworkServer server = new NetworkServer(6916, invoker);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FileManager.saveToFile(filename, manager.getCollection());
        }));

        Thread consoleThread = getConsoleThread(filename, manager);
        consoleThread.start();

        server.start();
    }

    private static Thread getConsoleThread(String filename, CollectionManager manager) {
        Thread consoleThread = new Thread(() -> {
            Scanner consoleScanner = new Scanner(System.in);
            while (consoleScanner.hasNextLine()) {
                String line = consoleScanner.nextLine().trim();
                if (line.equals("save")) {
                    FileManager.saveToFile(filename, manager.getCollection());
                }
                if (line.equals("exit")) {
                    System.out.println("Сервер завершает работу");
                    System.exit(0);
                }
            }
        });
        consoleThread.setDaemon(true);
        return consoleThread;
    }
}