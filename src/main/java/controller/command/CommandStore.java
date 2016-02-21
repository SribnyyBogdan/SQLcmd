package controller.command;

import model.DataSet;
import model.DatabaseManager;
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
        inputOutput.write("Connect is successful!");
    }

    public void list(){
        if (!validateConnection()){return;}
        inputOutput.write(Arrays.toString(dbManager.getTablesNames()));
    }

    public void quit(){
        throw new QuitException();
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

        inputOutput.write("\tconnect");
        inputOutput.write("\t\trequired parameters database_name|user_name|password");
        inputOutput.write("\t\t\tconnecting to Database");

        inputOutput.write("\thelp");
        inputOutput.write("\t\tthe output list of commands");

        inputOutput.write("\tlist");
        inputOutput.write("\t\tthe output of all tables on the screen");

        inputOutput.write("\tfind");
        inputOutput.write("\t\trequired parameter 'tableName'");
        inputOutput.write("\t\t\tthe output table data on the screen");

        inputOutput.write("\tinsert");
        inputOutput.write("\t\trequired parameter tableName|columnName1|columnValue1|...|...|columnNameN|columnValueN");
        inputOutput.write("\t\t\tinsert new row with data in table");

        inputOutput.write("\tclear");
        inputOutput.write("\t\trequired parameter 'tableName'");
        inputOutput.write("\t\t\tdelete all data from the table");

        inputOutput.write("\tquit");
        inputOutput.write("\t\texiting from the program");

    }

    public void insert() {
        inputOutput.write("Enter, please, table name and data for inserting" +
                " in the following format: table_name|columnName1|columnValue1|...|...|columnNameN|columnValueN:");
        String string = inputOutput.read();
        String[] result = string.split("\\|");
        if (result.length % 2 != 1){
            throw new IllegalArgumentException("It must be an odd number of arguments, but you entered " + result.length);
        }
        String tableName = result[0];
        DataSet dataSet = new DataSet();
        for (int index = 1; index <= result.length/2; index++) {
            String columnName = result[2*index - 1];
            String columnValue = result[2*index];
            dataSet.put(columnName, columnValue);
        }
        dbManager.insert(tableName,dataSet);

    }

    public void clear(){
        if (!validateConnection()){return;}
        inputOutput.write("Enter the table name");
        String tableName = inputOutput.read();
        dbManager.clear(tableName);
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

    private void printHeader(DataSet[] tableData, int maxCountSymbol) {
        String[] names = tableData[0].getColumnsNames();
        String result = "|";
        for(String name : names){
            result += name + addSpace(name.length(), maxCountSymbol) + "|";
        }
        inputOutput.write(result);
    }

    private void printTableRow(DataSet row, int maxCountSymbol) {
        Object[] data = row.getColumnsValues();
        String result = "|";

        for(Object o : data){
            result += o + addSpace(o.toString().length(), maxCountSymbol) + "|";
        }
        inputOutput.write(result);
    }

    private void printTableData(DataSet[] tableData) {
        int maxCountSymbolInCell = maxCountSymbolInCell(tableData);
        int maxCountSymbolInRow = maxCountSymbolInRow(tableData);
        inputOutput.write(addHyphen(maxCountSymbolInRow));
        printHeader(tableData, maxCountSymbolInCell);
        inputOutput.write(addHyphen(maxCountSymbolInRow));
        for(DataSet row : tableData){
            printTableRow(row, maxCountSymbolInCell);
            inputOutput.write(addHyphen(maxCountSymbolInRow));
        }
    }

    private String addHyphen(int maxCountSymbol) {
        String result = "";
        for (int index = 0; index < maxCountSymbol; index++) {
            result += "-";
        }
        return result;

    }

    private String addSpace(int startCount, int endCount){
        String result = "";
        for (int index = 0; index < endCount - startCount; index++) {
            result += " ";
        }
        return result;
    }

    private int maxCountSymbolInCell(DataSet[] tableData){
        int result = 0;
        String[] names = tableData[0].getColumnsNames();
        for (int i = 0; i < names.length; i++) {
            int countSymbol = names[i].length();
            if (countSymbol > result) {
                result = countSymbol;
            }
        }
        for (int i = 0; i < tableData.length; i++) {
            Object[] data = tableData[i].getColumnsValues();
            for (int j = 0; j < data.length; j++) {
                int countSymbol = data[j].toString().length();
                if (countSymbol > result) {
                    result = countSymbol;
                }
            }
        }
        return result;

    }

    private int maxCountSymbolInRow(DataSet[] tableData){
        int result = 0;
        String[] names = tableData[0].getColumnsNames();
        for (int i = 0; i < names.length; i++) {
            int countSymbol = names[i].length();
            if (countSymbol > result) {
                result = countSymbol;
            }
        }
        for (int i = 0; i < tableData.length; i++) {
            Object[] data = tableData[i].getColumnsValues();
            for (int j = 0; j < data.length; j++) {
                int countSymbol = data[j].toString().length();
                if (countSymbol > result) {
                    result = countSymbol;
                }
            }
        }
        result = result * names.length + names.length + 1;
        return result;

    }
}
