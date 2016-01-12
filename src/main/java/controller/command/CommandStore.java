package controller.command;

import model.DatabaseManager;
import view.Console;

/**
 * Created by Богдан on 12.01.2016.
 */
public class CommandStore {
    private Console console;
    private DatabaseManager dbManager;

    public CommandStore(DatabaseManager dbManager, Console console){
        this.dbManager = dbManager;
        this.console = console;
    }

    public void list(){
        console.write(dbManager.getTablesNames());
    }

    public void quit(){
        System.exit(0);
    }
}
