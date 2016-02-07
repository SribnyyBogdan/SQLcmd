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
        while(true){
            console.write("Enter, please, database name, user name and password" +
                    " in the following format: database_name|user_name|password:");
            try{
                String string = console.read();
                String[] result = string.split("\\|");
                String dbName = result[0];
                String userName = result[1];
                String password = result[2];
                dbManager.connect(dbName, userName, password);
                break;

            }catch(Exception e){
                console.write("Cause of failure:" + e.getMessage());
                console.write("Try again!");
            }

        }
        //console.write("Успех!");

    }



    public void run(){
        CommandStore commandStore = new CommandStore(dbManager, console);
        commandMap.put("list", new ListCommand(commandStore));
        commandMap.put("quit", new QuitCommand(commandStore));
        commandMap.put("help", new HelpCommand(commandStore));
        connectToDB();
    }


}
