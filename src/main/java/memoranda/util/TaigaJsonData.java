package memoranda.util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

@Singleton
public class TaigaJsonData {
    private static final Path DATA_DIR = Path.of(System.getProperty("user.home"), ".memoranda/data");

    private static final Path USER_DATA = DATA_DIR.resolve("user.json");
    private static final Path ISSUES_FILE = DATA_DIR.resolve("issues.json");
    private static final Path STATS_FILE = DATA_DIR.resolve("stats.json");

    private final Gson gson;

    private Object userData;
    private Object issuesData;
    private Object statsData;

    @Inject
    public TaigaJsonData() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        initializeDataMaps();
        loadAllConfigs();
    }

    private void initializeDataMaps() {
        try {
            Files.createDirectories(DATA_DIR);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data directory", e);
        }
    }

    public void loadAllConfigs() {
        userData = loadConfig(USER_DATA);
        issuesData = loadConfig(ISSUES_FILE);
        statsData = loadConfig(STATS_FILE);
    }

    private Object loadConfig(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                String jsonContent = Files.readString(filePath);
                return gson.fromJson(jsonContent, Object.class);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from " + filePath, e);
        }
        return null;
    }

    public void saveAllConfigs() {
        saveConfig(USER_DATA, userData);
        saveConfig(ISSUES_FILE, issuesData);
        saveConfig(STATS_FILE, statsData);
    }

    private void saveConfig(Path filePath, Object data) {
        if (data == null) return;
        try {
            String jsonContent = gson.toJson(data);
            Files.writeString(filePath, jsonContent,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration to " + filePath, e);
        }
    }

    public void setUserData(Object newData) {
        this.userData = newData;
        saveConfig(USER_DATA, newData);
    }

    public void setIssuesData(Object newData) {
        this.issuesData = newData;
        saveConfig(ISSUES_FILE, newData);
    }

    public void setStatsData(Object newData) {
        this.statsData = newData;
        saveConfig(STATS_FILE, newData);
    }

    /**
     * Get the project data as a specific type
     * @param type The class type to cast the project data to
     * @return The project data as the specified type
     * @param <T> The type parameter
     */
    public <T> T getUserData(Class<T> type) {
        return gson.fromJson(gson.toJson(userData), type);
    }

    public <T> T getIssuesData(Class<T> type) {
        return gson.fromJson(gson.toJson(issuesData), type);
    }

    public <T> T getStatsData(Class<T> type) {
        return gson.fromJson(gson.toJson(statsData), type);
    }
    public Path getUserPath() {
        return USER_DATA;
    }
}