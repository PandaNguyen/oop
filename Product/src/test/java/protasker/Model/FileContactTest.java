package protasker.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileContactTest {

    @Test
    void saveAndLoadDataStore_roundTrips() throws Exception {
        Path tempDir = Files.createTempDirectory("protasker-test-");
        System.setProperty("protasker.data.dir", tempDir.toString());

        DataStore store = new DataStore();
        store.getUsers().add(new User("alice", "password123"));
        FileContact.saveDataStore(store);

        DataStore loaded = FileContact.loadDataStore();
        assertNotNull(loaded);
        assertEquals(1, loaded.getUsers().size());
        assertEquals("alice", loaded.getUsers().getFirst().getUsername());
    }
}

