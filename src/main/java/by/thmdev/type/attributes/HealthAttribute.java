package by.thmdev.type.attributes;

import by.thmdev.api.AttributeManager;
import by.thmdev.api.AttributePlaceholder;
import by.thmdev.api.AttributedPlayer;
import com.codingforcookies.armorequip.ArmorEquipEvent;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class HealthAttribute implements Listener {

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e) {
        if (e.getNewArmorPiece() == null) return;
        if (e.getNewArmorPiece().getType() == Material.AIR) return;
        NBTItem nbti = new NBTItem(e.getNewArmorPiece());
        if (!nbti.hasKey(AttributePlaceholder.HEALTH_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setMaxHealth(attributedPlayer.getMaxHealth() + nbti.getInteger(AttributePlaceholder.HEALTH_ARMOR.getPlaceholder()));
        attributedPlayer.setRegen(attributedPlayer.getMaxHealth()/20);
        p.setMaxHealth(attributedPlayer.getMaxHealth());
    }

    @EventHandler
    public void onArmorUnequip(ArmorEquipEvent e) {
        if (e.getOldArmorPiece() == null) return;
        if (e.getOldArmorPiece().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(e.getOldArmorPiece());
        if (!nbtItem.hasKey(AttributePlaceholder.HEALTH_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setMaxHealth(attributedPlayer.getMaxHealth() - nbtItem.getInteger(AttributePlaceholder.HEALTH_ARMOR.getPlaceholder()));
        attributedPlayer.setRegen(attributedPlayer.getMaxHealth()/20);
        p.setMaxHealth(attributedPlayer.getMaxHealth());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        NBTItem nbtItem = new NBTItem(new ItemStack(Material.STICK));
        nbtItem.setInteger(AttributePlaceholder.ITEM_MANA.getPlaceholder(), 50);
        nbtItem.setInteger(AttributePlaceholder.ITEM_MANA_COST.getPlaceholder(), 30);
        nbtItem.setInteger(AttributePlaceholder.ITEM_DAMAGE.getPlaceholder(), 2000);
        nbtItem.setInteger(AttributePlaceholder.ITEM_CRIT_CHANCE.getPlaceholder(), 50);
        nbtItem.setInteger(AttributePlaceholder.ITEM_CRIT_DAMAGE.getPlaceholder(), 200);
        nbtItem.setInteger(AttributePlaceholder.ITEM_MANA_COST.getPlaceholder(), 30);
        e.getPlayer().getInventory().addItem(nbtItem.getItem());
    }
}
