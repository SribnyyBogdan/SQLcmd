package model;

import java.sql.*;
import java.util.Arrays;

public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;

    @Override
    public int getTableSize(String tableName){
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM public." + tableName);
            rs.next();
            int count = rs.getInt("rowcount");
            rs.close();
            return count;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String[] getTablesNames(){
        try {
           // String result = "[";
            String[] result = new String[1000];
            String[] types = {"TABLE"};
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, "public", "%", types);
            int index = 0;
            while (rs.next()) {
                result[index++] = rs.getString(3);
            }
            result = Arrays.copyOf(result, index);
           /* result = result.substring(0, result.length() - 2);
            result = result + "]";*/
            rs.close();
            return result;
        } catch(SQLException e){
            e.printStackTrace();
            return new String[0];
        }

    }

    @Override
    public void connect(String dbName, String userName, String password){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add JDBC jar to project", e);
        }

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/" + dbName, userName, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format("Can't get connection for model: %s user:%s",
                    dbName, userName), e);
        }
    }

    public Connection getConnection(){
        return connection;
    }

    @Override
    public int getCountColumn(String tableName){
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from public." + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            rs.close();
            stmt.close();
            return columnsNumber;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public DataSet[] getTableData(String tableName){
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet dataSet[] = new DataSet[getTableSize(tableName)];
            int index = 0;
            while (rs.next()) {
                DataSet dataRow = new DataSet();
                for (int i = 1; i <= getCountColumn(tableName); i++) {
                    dataRow.put(rsmd.getColumnName(i), rs.getString(i));
                }
                dataSet[index++] = dataRow;
            }
            rs.close();
            stmt.close();
            return dataSet;
        }catch(SQLException e){
            e.printStackTrace();
            return new DataSet[0];
        }
    }

    @Override
    public void clear(String tableName){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public." + tableName);
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(String tableName, DataSet dataSet){
        try {
            String resultValues = "";
            String resultNames = "";
            for (int i = 0; i < getCountColumn(tableName); i++) {
                resultNames += dataSet.getColumnsNames()[i] + ",";
            }
            resultNames = resultNames.substring(0, resultNames.length() - 1);
            for (int i = 0; i < getCountColumn(tableName); i++) {
                resultValues += "'" + dataSet.getColumnsValues()[i] + "'" + ",";
            }
            resultValues = resultValues.substring(0, resultValues.length() - 1);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO public." + tableName + "(" + resultNames + ")"
                                + "VALUES (" + resultValues + ");");
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, int id, DataSet dataSet){
        try {
            String resultNames = "";
            for(String name: dataSet.getColumnsNames()){
                resultNames += name + "=?,";
            }
            resultNames = resultNames.substring(0, resultNames.length() - 1);
            PreparedStatement ps = getConnection().prepareStatement(
                    "UPDATE public." + tableName + " SET " + resultNames + " WHERE id = ?");
            int index = 1;
            for (Object o : dataSet.getColumnsValues()) {
                ps.setObject(index, o);
                index++;
            }
            ps.setObject(index, id);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
