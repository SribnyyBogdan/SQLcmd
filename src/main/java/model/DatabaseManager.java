package model;

/**
 * Created by Богдан on 11.01.2016.
 */
public interface DatabaseManager {
    int getTableSize(String tableName);

    String[] getTablesNames();

    void connect(String dbName, String userName, String password);

    int getCountColumn(String tableName);

    DataSet[] getTableData(String tableName);

    void clear(String tableName);

    void insert(String tableName, DataSet dataSet);

    void update(String tableName, int id, DataSet dataSet);
}
