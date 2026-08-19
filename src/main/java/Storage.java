import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/** Handles saving Penguin data to a file. */
public class Storage {
    /** Path of the file used to store task data. */
    private final Path filePath;

    /**
     * Creates storage using the given file path.
     *
     * @param filePath path of the task data file
     */
    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Writes task data to the storage file, creating its parent directory if necessary.
     *
     * @param content lines to write to the storage file
     * @throws PenguinException if the file cannot be written
     */
    public void save(ArrayList<String> content) throws PenguinException {
        try {
            Path parent = filePath.getParent();

            if (parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }

            Files.write(filePath, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PenguinException("Unable to save tasks: " + e.getMessage());
        }
    }

    /**
     * Reads all saved task lines from the storage file.
     *
     * @return saved task lines, or an empty list if the file does not exist
     * @throws PenguinException if the file cannot be read
     */
    public ArrayList<String> read() throws PenguinException {
        try {
            if (Files.notExists(filePath)) {
                return new ArrayList<>();
            }

            return new ArrayList<>(Files.readAllLines(filePath, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new PenguinException("Unable to load tasks: " + e.getMessage());
        }
    }
}
