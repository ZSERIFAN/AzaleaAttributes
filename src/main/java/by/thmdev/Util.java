package by.thmdev;

import by.thmdev.api.AttributedPlayer;
import by.thmdev.listeners.PlayerJoinListener;
import by.thmdev.listeners.PlayerQuitListener;
import by.thmdev.runnables.ActionBarTask;
import by.thmdev.runnables.HealthRegenTask;
import by.thmdev.runnables.HeldItemCheckTask;
import by.thmdev.runnables.ManaRegenTask;
import by.thmdev.type.attributes.*;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;

public class Util {
    public static Map<String, Double> baseStats = ImmutableMap.of("health", 100.0, "defense", 30.0, "mana", 100.0);
    public static Map<String, Double> damageBase = ImmutableMap.of("damage", 5.0, "crit_dmg", 200.0, "crit_chance", 0.0);

    public static Map<String, Double> getBaseStats() {
        return baseStats;
    }
    public static Map<String, Double> getDamageBase() { return damageBase; }

    public static void setupFiles() {
        File dir = new File("plugins", "AzaleaAttributes");
        File playerData = new File("plugins/AzaleaAttributes/data");
        if (!playerData.exists()) playerData.mkdir();
        if (!dir.exists()) dir.mkdir();
        if (!Attributes.config.exists()) Attributes.getInstance().saveDefaultConfig();
        try {
            Attributes.cfg.load(Attributes.config);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void registerEvents() {
        Attributes.getInstance().events = new Listener[] {
                new PlayerJoinListener(),
                new PlayerQuitListener(),
                new HealthAttribute(),
                new DefenseAttribute(),
                new ManaAttribute(),
                new DamageAttribute(),
                new CritAttribute()
        };
        Arrays.stream(Attributes.getInstance().events).forEach(listener -> {
            Bukkit.getPluginManager().registerEvents(listener, Attributes.getInstance());
        });
    }

    public static void setupTasks() {
        new ActionBarTask();
        new HealthRegenTask();
        new ManaRegenTask();
        new HeldItemCheckTask();
    }

    public static void setupObjects() {
        for (Player player : Bukkit.getOnlinePlayers())
            new AttributedPlayer(player);
    }

    public static void initPlayer(Player player) throws IOException {
        JsonObject object = new JsonObject();
        File playerFile = new File("plugins/AzaleaAttributes/data/" + player.getUniqueId() + ".json");
        if (playerFile.exists()) return;
        else playerFile.createNewFile();
        for (String str : baseStats.keySet())
            object.addProperty(str, baseStats.get(str));
        for (String str : damageBase.keySet())
            object.addProperty(str, damageBase.get(str));
        try (FileWriter writer = new FileWriter(playerFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String prettyString = gson.toJson(object);
            writer.write(prettyString);
            Bukkit.getLogger().log(Level.INFO, "Successfully loaded " + player.getName() + " into our local database.");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
