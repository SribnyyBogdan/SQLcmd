package controller;



import controller.command.*;
import model.DatabaseManager;
import view.Console;
import view.InputOutput;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    private Map<String, Command> commandMap;

    private DatabaseManager dbManager;
    private InputOutput inputOutput;

    public Controller(DatabaseManager dbManager, InputOutput inputOutput){
        commandMap = new HashMap<String, Command>();
        this.dbManager = dbManager;
        this.inputOutput = inputOutput;
    }

    /*private void connectToDB(){
        inputOutput.write("It's a program SQLcmd!");
        inputOutput.write("Enter, please, database name, user name and password" +
                " in the following format: database_name|user_name|password:");
        while(true){
            try{
                String string = inputOutput.read();
                String[] result = string.split("\\|");
                if (result.length != 3){
                    throw new IllegalArgumentException("Invalid number of parameters," +
                            " must be 3, but entered " + result.length);
                }
                String dbName = result[0];
                String userName = result[1];
                String password = result[2];
                dbManager.connect(dbName, userName, password);
                break;

            }catch(Exception e){
                printError(e);
            }
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null){
            message += " " + e.getCause().getMessage();
        }
        inputOutput.write("Cause of failure:" + message);
        inputOutput.write("Try again!");
    }*/


    public void run(){
        CommandStore commandStore = new CommandStore(dbManager, inputOutput);
        commandMap.put("list", new ListCommand(commandStore));
        commandMap.put("quit", new QuitCommand(commandStore));
        commandMap.put("help", new HelpCommand(commandStore));
        commandMap.put("find", new FindCommand(commandStore));
        commandMap.put("connect", new ConnectCommand(commandStore));
        commandMap.put("clear", new ClearCommand(commandStore));
        commandMap.put("insert", new InsertCommand(commandStore));
        inputOutput.write("It's a program SQLcmd!");
        try {
            while (true) {
                inputOutput.write("Enter the command or help:");
                String command = inputOutput.read();
                if (commandMap.get(command) != null) {
                    commandMap.get(command).execute();
                } else {
                    inputOutput.write("Does not exist command");
                    inputOutput.write("Try again!");

                }
            }
        }catch(QuitException e){

        }
    }


}
