import model.DatabaseManager;
import model.JDBCDatabaseManager;

/**
 * Created by Богдан on 07.02.2016.
 */
public class JDBCDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager();
    }
}
