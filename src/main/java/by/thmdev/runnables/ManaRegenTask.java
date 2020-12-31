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

public class ManaRegenTask implements Runnable {

    private BukkitTask task;

    public ManaRegenTask() {
        task = Bukkit.getScheduler().runTaskTimer(Attributes.getInstance(), this, 0L, 10L);
    }

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
            int lastAdditionalMana = attributedPlayer.getAdditionalMana();
            if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR) {
                NBTItem nbti = new NBTItem(p.getInventory().getItemInHand());
                if (nbti.hasKey(AttributePlaceholder.ITEM_MANA.getPlaceholder()))
                    attributedPlayer.setAdditionalMana(nbti.getInteger(AttributePlaceholder.ITEM_MANA.getPlaceholder()));
                else
                    attributedPlayer.setAdditionalMana(0);
            } else attributedPlayer.setAdditionalMana(0);
            if (lastAdditionalMana < attributedPlayer.getAdditionalMana()) attributedPlayer.setMaxMana(attributedPlayer.getMaxMana() + attributedPlayer.getAdditionalMana() - lastAdditionalMana);
            else attributedPlayer.setMaxMana(attributedPlayer.getMaxMana() - (lastAdditionalMana - attributedPlayer.getAdditionalMana()));
            int increase = attributedPlayer.getMaxMana() / 80;
            if ((attributedPlayer.getCurrentMana() + increase) >= attributedPlayer.getMaxMana()) attributedPlayer.setCurrentMana(attributedPlayer.getMaxMana());
            else attributedPlayer.setCurrentMana(attributedPlayer.getCurrentMana() + increase);
        }
    }
}
