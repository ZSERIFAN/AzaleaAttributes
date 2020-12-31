package by.thmdev.listeners;

import by.thmdev.api.AttributeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        AttributeManager.removeAttributedPlayer(p);
        System.out.println("[AzaleaAttributes] Removed player " + p.getName() + " from cached memory.");
    }
}
