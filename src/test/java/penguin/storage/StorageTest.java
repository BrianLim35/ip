package penguin.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import penguin.exception.PenguinException;

import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageTest {
    @Test
    void saveAndRead_validContent_roundTrips(@TempDir Path tempDir) throws PenguinException {
        Storage storage = new Storage(
                tempDir.resolve("data/tasks.txt").toString());
        ArrayList<String> content = new ArrayList<>();
        content.add("T | 0 | read book");

        storage.save(content);

        assertEquals(content, storage.read());
    }

    @Test
    void read_missingFile_returnsEmptyList(@TempDir Path tempDir)
            throws PenguinException {
        Storage storage = new Storage(
                tempDir.resolve("missing/tasks.txt").toString());

        assertEquals(new ArrayList<>(), storage.read());
    }

    @Test
    void save_emptyContent_createsEmptyFile(@TempDir Path tempDir)
            throws PenguinException {
        Storage storage = new Storage(
                tempDir.resolve("data/tasks.txt").toString());

        storage.save(new ArrayList<>());

        assertEquals(new ArrayList<>(), storage.read());
    }

    @Test
    void read_directoryPath_throwsPenguinException(@TempDir Path tempDir) {
        Storage storage = new Storage(tempDir.toString());

        assertThrows(PenguinException.class, storage::read);
    }
}
