package controller;

import controller.command.*;
import model.DatabaseManager;
import view.Console;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    private Map commandMap;

    private DatabaseManager dbManager;
    private Console console;

    public Controller(DatabaseManager dbManager, Console console){
        commandMap = new HashMap<String, Command>();
        this.dbManager = dbManager;
        this.console = console;
    }

    private void connectToDB(){
        console.write("It's a program SQLcmd!");
        console.write("Enter, please, database name, user name and password in the following format database_name|user_name|password:");
        while(true){

        }

    }



    public void run(){
        CommandStore commandStore = new CommandStore(dbManager, console);
        commandMap.put("list", new ListCommand(commandStore));
        commandMap.put("quit", new QuitCommand(commandStore));
        commandMap.put("help", new HelpCommand(commandStore));
        while(true){

        }
    }


}
