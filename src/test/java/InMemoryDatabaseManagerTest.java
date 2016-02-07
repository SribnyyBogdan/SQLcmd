import model.DatabaseManager;
import model.InMemoryDatabaseManager;

/**
 * Created by Богдан on 07.02.2016.
 */
public class InMemoryDatabaseManagerTest extends DatabaseManagerTest {
    @Override
    public DatabaseManager getDatabaseManager() {
        return new InMemoryDatabaseManager();
    }
}
