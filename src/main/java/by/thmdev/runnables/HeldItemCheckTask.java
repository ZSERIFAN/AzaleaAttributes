package by.thmdev.runnables;

import by.thmdev.api.AttributeManager;
import by.thmdev.api.AttributePlaceholder;
import by.thmdev.api.AttributedPlayer;
import by.thmdev.Attributes;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class HeldItemCheckTask implements Runnable {

    private BukkitTask task;

    public HeldItemCheckTask() {
        task = Bukkit.getScheduler().runTaskTimer(Attributes.getInstance(), this, 0L, 1L);
    }

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
            int lastAdditionalDamage = attributedPlayer.getAdditionalDamage();
            int lastAdditionalCritDamage = attributedPlayer.getAdditionalCritDamage();
            int lastAdditionalCritChance = attributedPlayer.getAdditionalCritChance();
            if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(p.getInventory().getItemInHand());
                if (nbti.hasKey(AttributePlaceholder.ITEM_DAMAGE.getPlaceholder())) attributedPlayer.setAdditionalDamage(nbti.getInteger(AttributePlaceholder.ITEM_DAMAGE.getPlaceholder()));
                else attributedPlayer.setAdditionalDamage(0);
                if (nbti.hasKey(AttributePlaceholder.ITEM_CRIT_DAMAGE.getPlaceholder())) attributedPlayer.setAdditionalCritDamage(nbti.getInteger(AttributePlaceholder.ITEM_CRIT_DAMAGE.getPlaceholder()));
                else attributedPlayer.setAdditionalCritDamage(0);
                if (nbti.hasKey(AttributePlaceholder.ITEM_CRIT_CHANCE.getPlaceholder())) attributedPlayer.setAdditionalCritChance(nbti.getInteger(AttributePlaceholder.ITEM_CRIT_CHANCE.getPlaceholder()));
                else attributedPlayer.setAdditionalCritChance(0);
            } else {
                attributedPlayer.setAdditionalDamage(0);
                attributedPlayer.setAdditionalCritChance(0);
                attributedPlayer.setAdditionalCritDamage(0);
            }
            if (lastAdditionalDamage < attributedPlayer.getAdditionalDamage()) attributedPlayer.setDamage(attributedPlayer.getDamage() + attributedPlayer.getAdditionalDamage() - lastAdditionalDamage);
            else attributedPlayer.setDamage(attributedPlayer.getDamage() - (lastAdditionalDamage - attributedPlayer.getAdditionalDamage()));
            if (lastAdditionalCritDamage < attributedPlayer.getAdditionalCritDamage()) attributedPlayer.setCritDmg(attributedPlayer.getCritDmg() + attributedPlayer.getAdditionalCritDamage() - lastAdditionalCritDamage);
            else attributedPlayer.setCritDmg(attributedPlayer.getCritDmg() - (lastAdditionalCritDamage - attributedPlayer.getAdditionalCritDamage()));
            if (lastAdditionalCritChance < attributedPlayer.getAdditionalCritChance()) attributedPlayer.setCritChance(attributedPlayer.getCritChance() + attributedPlayer.getAdditionalCritChance() - lastAdditionalCritChance);
            else attributedPlayer.setCritChance(attributedPlayer.getCritChance() - (lastAdditionalCritChance - attributedPlayer.getAdditionalCritChance()));
        }
    }
}
