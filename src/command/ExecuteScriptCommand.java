package command;

import util.InputHandler;
import util.ScriptException;
import java.util.Scanner;
import java.util.Set;

/**
 * Выполняет скрипт из файла.
 */
public class ExecuteScriptCommand implements Command {
    private CommandInvoker invoker;
    private Set<String> runningScripts;

    public ExecuteScriptCommand(CommandInvoker invoker, Set<String> runningScripts) {
        this.invoker = invoker;
        this.runningScripts = runningScripts;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Укажите имя файла");
            return;
        }
        String scriptFile = args[0];
        if (runningScripts.contains(scriptFile)) {
            System.out.println("Обнаружен рекурсивный вызов скрипта, пропуск");
            return;
        }
        runningScripts.add(scriptFile);

        try {
            Scanner scriptScanner = new Scanner(new java.io.File(scriptFile));

            while (scriptScanner.hasNextLine()) {
                String line = scriptScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(" ", 2);
                String cmd = parts[0];
                String[] cmdArgs = parts.length > 1 ? new String[]{parts[1]} : new String[]{};
                System.out.println("> " + line);

                if (cmd.equals("add") || cmd.equals("update") || cmd.equals("remove_lower")) {
                    invoker.updateInputHandler(new InputHandler(scriptScanner, false));
                }

                invoker.execute(cmd, cmdArgs);
            }
        } catch (ScriptException e) {
            System.out.println("Ошибка в скрипте: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка чтения скрипта");
        } finally {
            invoker.restoreInputHandler();
            runningScripts.remove(scriptFile);
        }
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из файла";
    }
}