package by.thmdev;

import de.tr7zw.nbtinjector.NBTInjector;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class Attributes extends JavaPlugin {

    private static Attributes instance;
    public static Attributes getInstance() {
        return instance;
    }
    public static File config = new File("plugins/AzaleaAttributes/config.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
    private long timeEnabled;
    public Listener[] events;

    @Override
    public void onEnable() {
        this.timeEnabled = System.currentTimeMillis();
        getLogger().log(Level.INFO, "Attempting to enable plugin modules.");
        instance = this;
        Util.setupFiles();
        Util.registerEvents();
        Util.setupObjects();
        Util.setupTasks();
        getLogger().log(Level.INFO, "Plugin successfully enabled. Process took: " + (System.currentTimeMillis() - timeEnabled) + "ms");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Disabling plugin..");
    }
}
