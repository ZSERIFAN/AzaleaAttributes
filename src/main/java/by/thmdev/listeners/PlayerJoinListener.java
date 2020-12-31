package by.thmdev.listeners;

import by.thmdev.api.AttributedPlayer;
import by.thmdev.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoinListener implements Listener {

    private long timeEnabled;

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) throws IOException {
        this.timeEnabled = System.currentTimeMillis();
        Player p = e.getPlayer();
        Util.initPlayer(p);
        new AttributedPlayer(p);
    }
}
