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

public class DamageAttribute implements Listener {

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e) {
        if (e.getNewArmorPiece() == null) return;
        if (e.getNewArmorPiece().getType() == Material.AIR) return;
        NBTItem nbti = new NBTItem(e.getNewArmorPiece());
        if (!nbti.hasKey(AttributePlaceholder.DAMAGE_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setDamage(attributedPlayer.getDamage() + nbti.getInteger(AttributePlaceholder.DAMAGE_ARMOR.getPlaceholder()));
    }

    @EventHandler
    public void onArmorUnequip(ArmorEquipEvent e) {
        if (e.getOldArmorPiece() == null) return;
        if (e.getOldArmorPiece().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(e.getOldArmorPiece());
        if (!nbtItem.hasKey(AttributePlaceholder.DAMAGE_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setDamage(attributedPlayer.getDamage() - nbtItem.getInteger(AttributePlaceholder.DAMAGE_ARMOR.getPlaceholder()));
    }
}
