package controller;

import model.DatabaseManager;
import model.JDBCDatabaseManager;
import view.Console;

public class Main {

    public static void main(String[] args) {
        Console console = new Console();
        DatabaseManager dbManager = new JDBCDatabaseManager();
        Controller controller = new Controller(dbManager, console);
        controller.run();

    }
}
