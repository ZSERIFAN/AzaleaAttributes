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

public class CritAttribute implements Listener {

    @EventHandler
    public void onArmorEquipCritDamage(ArmorEquipEvent e) {
        if (e.getNewArmorPiece() == null) return;
        if (e.getNewArmorPiece().getType() == Material.AIR) return;
        NBTItem nbti = new NBTItem(e.getNewArmorPiece());
        if (!nbti.hasKey(AttributePlaceholder.CRIT_DAMAGE_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setCritDmg(attributedPlayer.getCritDmg() + nbti.getInteger(AttributePlaceholder.CRIT_DAMAGE_ARMOR.getPlaceholder()));
    }

    @EventHandler
    public void onArmorEquipCritChance(ArmorEquipEvent e) {
        if (e.getNewArmorPiece() == null) return;
        if (e.getNewArmorPiece().getType() == Material.AIR) return;
        NBTItem nbti = new NBTItem(e.getNewArmorPiece());
        if (!nbti.hasKey(AttributePlaceholder.CRIT_CHANCE_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setCritChance(attributedPlayer.getCritChance() + nbti.getInteger(AttributePlaceholder.CRIT_CHANCE_ARMOR.getPlaceholder()));
    }

    @EventHandler
    public void onArmorUnequipCritDamage(ArmorEquipEvent e) {
        if (e.getOldArmorPiece() == null) return;
        if (e.getOldArmorPiece().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(e.getOldArmorPiece());
        if (!nbtItem.hasKey(AttributePlaceholder.CRIT_DAMAGE_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setCritDmg(attributedPlayer.getCritDmg() - nbtItem.getInteger(AttributePlaceholder.CRIT_DAMAGE_ARMOR.getPlaceholder()));
    }

    @EventHandler
    public void onArmorUnequipCritChance(ArmorEquipEvent e) {
        if (e.getOldArmorPiece() == null) return;
        if (e.getOldArmorPiece().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(e.getOldArmorPiece());
        if (!nbtItem.hasKey(AttributePlaceholder.CRIT_CHANCE_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setCritChance(attributedPlayer.getCritChance() - nbtItem.getInteger(AttributePlaceholder.CRIT_CHANCE_ARMOR.getPlaceholder()));
    }

}
