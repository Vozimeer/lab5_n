package command;

import model.Organization;
import util.FileManager;
import java.util.LinkedList;

/** Сохраняет коллекцию в файл. */
public class SaveCommand implements Command {
    private LinkedList<Organization> collection;
    private String filename;

    public SaveCommand(LinkedList<Organization> collection, String filename) {
        this.collection = collection;
        this.filename = filename;
    }

    @Override
    public void execute(String[] args) {
        FileManager.saveToFile(filename, collection);
        System.out.println("Сохранено в " + filename);
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}