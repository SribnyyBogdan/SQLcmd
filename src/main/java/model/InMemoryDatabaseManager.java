package model;

import java.util.Arrays;

/**
 * Created by Богдан on 03.02.2016.
 */
public class InMemoryDatabaseManager implements DatabaseManager {
    private DataSet[] data = new DataSet[100];
    private int freeIndex = 0;
    public static final String TABLE_NAME = "user";

    private void validateTable(String tableName){
        if (!tableName.equals("user")){
            throw new UnsupportedOperationException("Only for 'user' table, but you try to work with:" + tableName);
        }
    }

    @Override
    public int getTableSize(String tableName) {
        validateTable(tableName);
        return data.length;
    }

    @Override
    public String getTablesNames() {
        return "[" + TABLE_NAME + "]";
    }

    @Override
    public void connect(String dbName, String userName, String password) {
        //do nothing
    }

    @Override
    public int getCountColumn(String tableName) {
        validateTable(tableName);
        return data[freeIndex - 1].getColumnsNames().length;
    }

    @Override
    public DataSet[] getTableData(String tableName) {
        validateTable(tableName);
        return Arrays.copyOf(data, freeIndex);
    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);
        data = new DataSet[100];
        freeIndex = 0;

    }

    @Override
    public void insert(String tableName, DataSet dataSet) {
        validateTable(tableName);
        data[freeIndex++] = dataSet;

    }

    @Override
    public void update(String tableName, int id, DataSet dataSet) {
        validateTable(tableName);
        for (int index = 0; index < freeIndex; index++) {
            if (data[index].get("id").equals(id)){
                data[index].updateFrom(dataSet);
            }
        }

    }
}
