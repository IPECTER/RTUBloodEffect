package com.github.ipecter.rtu.bloodeffect.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FileManager {
    /**
     * config.yml *
     */
    private static final int CONFIG_FILE_VERSION = 4;
    private static final boolean CONFIG_RESET_FILE = true;
    private static final List<String> CONFIG_IGNORED_NODES = new ArrayList<String>() {
        {
            add("config-version");
        }
    };
    private static final List<String> CONFIG_REPLACE_NODES = new ArrayList<String>() {
        {
            add("disabled-worlds");
        }
    };

    /**
     * messages.yml *
     */
    private final int MESSAGES_FILE_VERSION = 1;
    private final boolean MESSAGES_RESET_FILE = false;
    private final List<String> MESSAGES_IGNORED_NODES = new ArrayList<String>() {
        {
            add("config-version");
        }
    };
    private static final List<String> MESSAGES_REPLACE_NODES = new ArrayList<>();

    /**
     * biomes.yml *
     */
    private static final int BIOMES_FILE_VERSION = 0; //Unused for now
    private static final boolean BIOMES_RESET_FILE = false;
    private static final List<String> BIOMES_IGNORED_NODES = new ArrayList<String>() {
        {
            add("config-version");
        }
    };
    private static final List<String> BIOMES_REPLACE_NODES = new ArrayList<>();

    /**
     * database.db *
     */
    private static final int DATABASE_FILE_VERSION = 0; //Unused for now
    private static final boolean DATABASE_RESET_FILE = false;

    public static final StorageFileManager INSTANCE = new StorageFileManager();
    private HashMap<String, StorageFile> fileMap = new HashMap<>();

    public StorageFileManager() {
        registerFile("config.yml", new YAMLFile(Aurora.getInstance().getDataFolder().getPath(), "config.yml", CONFIG_FILE_VERSION, CONFIG_RESET_FILE, CONFIG_IGNORED_NODES, CONFIG_REPLACE_NODES));
        registerFile("messages.yml", new YAMLFile(Aurora.getInstance().getDataFolder().getPath(), "messages.yml", MESSAGES_FILE_VERSION, MESSAGES_RESET_FILE, MESSAGES_IGNORED_NODES, MESSAGES_REPLACE_NODES));
        registerFile("biomes.yml", new YAMLFile(Aurora.getInstance().getDataFolder().getPath(), "biomes.yml", BIOMES_FILE_VERSION, BIOMES_RESET_FILE, BIOMES_IGNORED_NODES, BIOMES_REPLACE_NODES));
        registerFile("database.db", new DBFile(Aurora.getInstance().getDataFolder().getPath(), "database.db", DATABASE_FILE_VERSION, DATABASE_RESET_FILE));
    }

    public static void reloadFiles() {
        INSTANCE.fileMap.clear();
        INSTANCE.registerFile("config.yml", new YAMLFile("config.yml"));
        INSTANCE.registerFile("messages.yml", new YAMLFile("messages.yml"));
        INSTANCE.registerFile("biomes.yml", new YAMLFile("biomes.yml"));
        INSTANCE.registerFile("database.db", new DBFile("database.db"));
    }

    public StorageFile getFile(String fileName) {
        return fileMap.get(fileName);
    }

    public YAMLFile getYAMLFile(String fileName) {
        return (YAMLFile) getFile(fileName);
    }

    public DBFile getDBFile(String fileName) {
        return (DBFile) getFile(fileName);
    }

    public Set<String> getFileNames() {
        return fileMap.keySet();
    }

    public void registerFile(String fileName, StorageFile file) {
        fileMap.put(fileName, file);
    }

    public void unregisterFile(String fileName) {
        fileMap.remove(fileName);
    }

    public static YAMLFile getConfig() {
        return (YAMLFile) INSTANCE.getFile("config.yml");
    }

    public static YAMLFile getMessages() {
        return (YAMLFile) INSTANCE.getFile("messages.yml");
    }

    public static YAMLFile getBiomes() {
        return (YAMLFile) INSTANCE.getFile("biomes.yml");
    }

    public static DBFile getDatabase() {
        return (DBFile) INSTANCE.getFile("database.db");
    }
}
