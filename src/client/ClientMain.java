package client;

import common.request.Request;
import common.response.Response;
import java.io.*;
import java.net.SocketTimeoutException;
import java.util.*;

public class ClientMain {
    private static final int PORT = 6916;
    private static final int TIMEOUT = 5000;
    private static Set<String> runningScripts = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(scanner, true);
        ClientCommandInvoker invoker = new ClientCommandInvoker(inputHandler);
        NetworkClient networkClient = null;

        try {
            networkClient = new NetworkClient("localhost", PORT, TIMEOUT);
        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу");
            System.exit(1);
        }

        System.out.println("Клиент запущен. Введите help для справки.");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            if (line.equals("exit")) {
                System.out.println("Завершение работы клиента");
                break;
            }

            if (line.startsWith("execute_script ")) {
                String scriptFile = line.substring(15).trim();
                executeScript(scriptFile, invoker, networkClient);
                continue;
            }

            processCommand(line, invoker, networkClient);
        }

        try {
            if (networkClient != null) networkClient.close();
        } catch (IOException e) {
            // ignore
        }
    }

    private static void executeScript(String scriptFile, ClientCommandInvoker invoker, NetworkClient networkClient) {
        try {
            String absolutePath = new File(scriptFile).getCanonicalPath();
            if (runningScripts.contains(absolutePath)) {
                System.out.println("Обнаружен рекурсивный вызов скрипта, пропуск");
                return;
            }
            runningScripts.add(absolutePath);

            try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.equals("exit")) continue;
                    System.out.println("> " + line);

                    String[] parts = line.split(" ", 2);
                    String cmd = parts[0];
                    String arg = parts.length > 1 ? parts[1] : "";

                    if (cmd.equals("execute_script")) {
                        executeScript(arg, invoker, networkClient);
                        continue;
                    }

                    Request request = invoker.parseCommandFromScript(cmd, arg, reader);
                    if (request == null) continue;

                    try {
                        Response response = networkClient.sendRequest(request);
                        System.out.println(response.getMessage());
                    } catch (SocketTimeoutException e) {
                        System.out.println("Сервер недоступен");
                        break;
                    }
                }
            } finally {
                runningScripts.remove(absolutePath);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения скрипта: " + e.getMessage());
        }
    }

    private static void processCommand(String line, ClientCommandInvoker invoker, NetworkClient networkClient) {
        Request request = invoker.parseCommand(line);
        if (request == null) return;

        try {
            Response response = networkClient.sendRequest(request);
            System.out.println(response.getMessage());
        } catch (SocketTimeoutException e) {
            System.out.println("Сервер недоступен, попробуйте позже");
        } catch (IOException e) {
            System.out.println("Ошибка связи с сервером");
        }
    }
}