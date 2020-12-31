package by.thmdev.runnables;

import by.thmdev.api.AttributeManager;
import by.thmdev.api.AttributedPlayer;
import by.thmdev.Attributes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class HealthRegenTask implements Runnable {

    private BukkitTask task;

    public HealthRegenTask() {
        task = Bukkit.getScheduler().runTaskTimer(Attributes.getInstance(), this, 0L, 20L);
    }

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isDead()) continue;
            AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
            if (p.getHealth() + attributedPlayer.getRegen() >= attributedPlayer.getMaxHealth()) p.setHealth(attributedPlayer.getMaxHealth());
            else p.setHealth(p.getHealth() + (double) attributedPlayer.getRegen()/5);
        }
    }
}
