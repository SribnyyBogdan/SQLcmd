import model.DataSet;
import model.DatabaseManager;
import model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class TestSQLcmd {

    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = new JDBCDatabaseManager();
        manager.connect("test", "postgres", "1605");
    }

    @Test
    public void testGetAllTableNames() {
        String tableNames = manager.getTablesNames();
        assertEquals("[test, user]", tableNames);
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("user");
        String tableName = "user";

        // when
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.insert(tableName, input);

        // then
        DataSet[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getColumnsNames()));
        assertEquals("[Stiven, pass, 13]", Arrays.toString(user.getColumnsValues()));
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("user");
        String tableName = "user";

        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        DataSet input1 = new DataSet();
        input1.put("id", 11);
        input1.put("name", "Stiven");
        input1.put("password", "pass");
        manager.insert(tableName, input);
        manager.insert(tableName, input1);


        // when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        manager.update("user", 13, newValue);

        // then
        DataSet[] users = manager.getTableData("user");
        assertEquals(2, users.length);

        DataSet user = users[1];
        assertEquals("[name, password, id]", Arrays.toString(user.getColumnsNames()));
        assertEquals("[Pup, pass2, 13]", Arrays.toString(user.getColumnsValues()));
    }
}
