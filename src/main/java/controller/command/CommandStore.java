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

    public void list(){
        inputOutput.write(Arrays.toString(dbManager.getTablesNames()));
    }

    public void quit(){
        System.exit(0);
    }

    public void find(){
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

    private void printTableData(DataSet[] tableData) {
        printHeader(tableData);
        for(DataSet row : tableData){
            printTableRow(row);
        }
    }

    private void printTableRow(DataSet row) {
        Object[] data = row.getColumnsValues();
        String result = "|";
        for(Object o : data){
            result += o + "|";
        }
        inputOutput.write(result);
    }

    private void printHeader(DataSet[] tableData) {
        String[] names = tableData[0].getColumnsNames();
        String result = "|";
        for(String name : names){
            result += name + "|";
        }
        inputOutput.write(result);
    }

    public void help(){
        inputOutput.write("Existing command:");

        inputOutput.write("\tlist");
        inputOutput.write("\t\tthe output of all tables on the screen");

        inputOutput.write("\tfind |tableName");
        inputOutput.write("\t\tthe output table data on the screen");

        inputOutput.write("\tquit");
        inputOutput.write("\t\texiting from the program");

        inputOutput.write("\thelp");
        inputOutput.write("\t\tthe output list of commands");
    }
}
