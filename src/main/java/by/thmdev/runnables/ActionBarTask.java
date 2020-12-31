package by.thmdev.runnables;

import by.thmdev.api.AttributeManager;
import by.thmdev.api.AttributedPlayer;
import by.thmdev.Attributes;
import by.thmdev.multiversion.ReflectionUtils;
import com.connorlinfoot.bountifulapi.BountifulAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class ActionBarTask implements Runnable {

    private BukkitTask task;
    private boolean useBountifulAPI;
    private short version;

    public ActionBarTask() {
        task = Bukkit.getScheduler().runTaskTimer(Attributes.getInstance(), this, 0L, 1L);
        this.version = Short.parseShort(ReflectionUtils.PackageType.getServerVersion().split("_")[1]);
        this.useBountifulAPI = false;
        switch (version) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                this.useBountifulAPI = true;
                System.out.println("[AzaleaAttributes] Server is running on a version lower than 1.12.x, therefore will use BountifulAPI for ActionBar messages.");
                break;
            default:
                this.useBountifulAPI = false;
                System.out.println("[AzaleaAttributes] Server is running on a version higher than 1.12.x, therefore will use default Spigot ActionBar modules.");
        }
    }

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
            if (this.useBountifulAPI) {
                BountifulAPI.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', Attributes.cfg.getString("actionbar-message")).replace("%currentHealth%", String.valueOf((int) p.getHealth())).replace("%maxHealth%", String.valueOf(attributedPlayer.getMaxHealth())).replace("%defense%", String.valueOf(attributedPlayer.getDefense())).replace("%currentMana%", String.valueOf(attributedPlayer.getCurrentMana())).replace("%maxMana%", String.valueOf(attributedPlayer.getMaxMana())));
            } else {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', Attributes.cfg.getString("actionbar-message")).replace("%currentHealth%", String.valueOf((int) p.getHealth())).replace("%maxHealth%", String.valueOf(attributedPlayer.getMaxHealth())).replace("%defense%", String.valueOf(attributedPlayer.getDefense())).replace("%currentMana%", String.valueOf(attributedPlayer.getCurrentMana())).replace("%maxMana%", String.valueOf(attributedPlayer.getMaxMana()))));
                // .replace("%maxHealth%", String.valueOf(attributedPlayer.getMaxHealth())).replace("%defense%", String.valueOf(attributedPlayer.getDefense())).replace("%currentMana%", String.valueOf(attributedPlayer.getCurrentMana()))
            }
        }
    }

}
