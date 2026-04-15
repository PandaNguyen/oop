package protasker.Model;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Authenticator {

    public static final Path DATA_FILE = resolveDataFile();
    public static final String FILE_PATH = DATA_FILE.toString();

    private static Path resolveDataFile() {
        String fileProp = System.getProperty("protasker.data.file");
        if (fileProp != null && !fileProp.isBlank()) {
            return Paths.get(fileProp.trim());
        }

        String fileOverride = System.getenv("PROTASKER_DATA_FILE");
        if (fileOverride != null && !fileOverride.isBlank()) {
            return Paths.get(fileOverride.trim());
        }

        String dirProp = System.getProperty("protasker.data.dir");
        if (dirProp != null && !dirProp.isBlank()) {
            return Paths.get(dirProp.trim()).resolve("userdata.json");
        }

        String dirOverride = System.getenv("PROTASKER_DATA_DIR");
        Path baseDir =
                (dirOverride != null && !dirOverride.isBlank())
                        ? Paths.get(dirOverride.trim())
                        : Paths.get(System.getProperty("user.home"), ".protasker");

        return baseDir.resolve("userdata.json");
    }

    public static User checkLogin(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        String normalizedUsername = username.trim();
        DataStore dataStore = FileContact.loadDataStore();
        for (User user : dataStore.getUsers()) {
            if (user.getUsername().equals(normalizedUsername) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean isUsernameTaken(String username) {
        if (username == null) {
            return false;
        }

        DataStore dataStore = FileContact.loadDataStore();
        return isUsernameTaken(dataStore, username.trim());
    }

    private static boolean isUsernameTaken(DataStore dataStore, String username) {
        for (User user : dataStore.getUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static String registerUser(String username, String password, String confirmPassword) {
        String normalizedUsername = username == null ? "" : username.trim();
        if (normalizedUsername.isBlank() || password == null || password.isBlank() || confirmPassword == null || confirmPassword.isBlank()) {
            return "Please fill in the information";
        }
        if (!password.equals(confirmPassword)) {
            return "Wrong confirmation password";
        }
        DataStore dataStore = FileContact.loadDataStore();
        if (isUsernameTaken(dataStore, normalizedUsername)) {
            return "Existing Username!";
        }

        User newUser = new User(normalizedUsername, password);
        dataStore.getUsers().add(newUser);
        FileContact.saveDataStore(dataStore);
        
        return "Successfully registered!";
    }
}
