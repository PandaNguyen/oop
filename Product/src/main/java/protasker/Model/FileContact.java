package protasker.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import static protasker.Model.Authenticator.FILE_PATH;

public class FileContact {

    private static final Logger LOGGER = Logger.getLogger(FileContact.class.getName());
    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Gson GSON = new Gson();

    public static void saveDataStore(DataStore dataStore) {
        try {
            Path filePath = Paths.get(FILE_PATH);
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            Path tmpPath = filePath.resolveSibling(filePath.getFileName().toString() + ".tmp");
            try (Writer writer = Files.newBufferedWriter(tmpPath, StandardCharsets.UTF_8)) {
                PRETTY_GSON.toJson(dataStore, writer);
            }

            try {
                Files.move(tmpPath, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tmpPath, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save datastore", e);
        }
    }

    public static DataStore loadDataStore() {
        Path filePath = Paths.get(FILE_PATH);

        if (!Files.exists(filePath)) {
            return new DataStore();
        }

        try {
            if (Files.size(filePath) == 0) {
                return new DataStore();
            }
        } catch (IOException ignored) {
            // continue to parse and report error below
        }

        try (Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            DataStore dataStore = GSON.fromJson(reader, DataStore.class);
            return dataStore != null ? dataStore : new DataStore();
        } catch (JsonParseException e) {
            backupCorruptJson(filePath);
            LOGGER.log(Level.WARNING, "Corrupt JSON detected, created new datastore", e);
            return new DataStore();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load datastore", e);
            return new DataStore();
        }
    }

    private static void backupCorruptJson(Path filePath) {
        try {
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path badPath = filePath.resolveSibling("userdata.bad-" + ts + ".json");
            Files.move(filePath, badPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {
            // best-effort backup only
        }
    }

    public static void updateUser(DataStore dataStore, User updatedUser) {
        for (int i = 0; i < dataStore.getUsers().size(); i++) {
            if (dataStore.getUsers().get(i).getUserId().equals(updatedUser.getUserId())) {
                dataStore.getUsers().set(i, updatedUser);
                break;
            }
        }
        saveDataStore(dataStore);
    }

    public static void updateProject(DataStore dataStore, Project updatedProject) {
        for (int i = 0; i < dataStore.getProjects().size(); i++) {
            if (dataStore.getProjects().get(i).getProjectId().equals(updatedProject.getProjectId())) {
                dataStore.getProjects().set(i, updatedProject);
                break;
            }
        }
        saveDataStore(dataStore);
    }

    public static void updateTask(DataStore dataStore, Task updatedTask) {
        for (int i = 0; i < dataStore.getTasks().size(); i++) {
            if (dataStore.getTasks().get(i).getTaskId().equals(updatedTask.getTaskId())) {
                dataStore.getTasks().set(i, updatedTask);
                break;
            }
        }
        saveDataStore(dataStore);
    }
}
