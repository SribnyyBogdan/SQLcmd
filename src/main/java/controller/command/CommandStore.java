package controller.command;

import model.DataSet;
import model.DatabaseManager;
import view.Console;
import view.InputOutput;

import java.util.Arrays;

/**
 * Created by Богдан on 12.01.2016.
 */
public class CommandStore {
    private InputOutput inputOutput;
    private DatabaseManager dbManager;

    public CommandStore(DatabaseManager dbManager, InputOutput inputOutput){
        this.dbManager = dbManager;
        this.inputOutput = inputOutput;
    }

    public void connect(){
        inputOutput.write("Enter, please, database name, user name and password" +
                " in the following format: database_name|user_name|password:");
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
            }catch(Exception e){
                printError(e);
            }
    }

    public void list(){
        if (!validateConnection()){return;}
        inputOutput.write(Arrays.toString(dbManager.getTablesNames()));
    }

    public void quit(){
        System.exit(0);
    }

    public void find(){
        if (!validateConnection()){return;}
        inputOutput.write("Enter the table name");
        DataSet[] tableData;
        String[] tableNames = dbManager.getTablesNames();
        String tableName = inputOutput.read();
        for (int i = 0; i < tableNames.length; i++) {
            if (tableNames[i].equals(tableName)){
                tableData = dbManager.getTableData(tableName);
                printTableData(tableData);
                break;
            }
        }
    }

    public void help(){
        inputOutput.write("Existing command:");

        inputOutput.write("\tlist");
        inputOutput.write("\t\tthe output of all tables on the screen");

        inputOutput.write("\tfind");
        inputOutput.write("\t\trequired parameter 'tableName'");
        inputOutput.write("\t\t\tthe output table data on the screen");

        inputOutput.write("\tquit");
        inputOutput.write("\t\texiting from the program");

        inputOutput.write("\thelp");
        inputOutput.write("\t\tthe output list of commands");

        inputOutput.write("\tconnect");
        inputOutput.write("\t\trequired parameters database_name|user_name|password");
        inputOutput.write("\t\t\tconnecting to Database");
    }

    private boolean validateConnection(){
        if (dbManager.getConnection() == null){
            inputOutput.write("This command needs to connect to Database!");
            return false;
        }else{
            return true;
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null){
            message += " " + e.getCause().getMessage();
        }
        inputOutput.write("Cause of failure:" + message);
        inputOutput.write("Try again!");
    }

    private void printHeader(DataSet[] tableData) {
        String[] names = tableData[0].getColumnsNames();
        String result = "|";
        for(String name : names){
            result += name + "|";
        }
        inputOutput.write(result);
    }

    private void printTableRow(DataSet row) {
        Object[] data = row.getColumnsValues();
        String result = "|";
        for(Object o : data){
            result += o + "|";
        }
        inputOutput.write(result);
    }

    private void printTableData(DataSet[] tableData) {
        printHeader(tableData);
        for(DataSet row : tableData){
            printTableRow(row);
        }
    }
}
