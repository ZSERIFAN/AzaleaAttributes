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

public class ManaAttribute implements Listener {

   /* @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() == null || e.getItem().getType() == Material.AIR) return;
        NBTItem nbti = new NBTItem(e.getItem());
        if (!nbti.hasKey(AttributePlaceholder.ITEM_MANA_COST.getPlaceholder())) return;
        int cost = nbti.getInteger(AttributePlaceholder.ITEM_MANA_COST.getPlaceholder());
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (attributedPlayer.getCurrentMana() < cost) return;
        else attributedPlayer.setCurrentMana(attributedPlayer.getCurrentMana() - cost);
    } */

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e) {
        if (e.getNewArmorPiece() == null) return;
        if (e.getNewArmorPiece().getType() == Material.AIR) return;
        NBTItem nbti = new NBTItem(e.getNewArmorPiece());
        if (!nbti.hasKey(AttributePlaceholder.MANA_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setMaxMana(attributedPlayer.getMaxMana() + nbti.getInteger(AttributePlaceholder.MANA_ARMOR.getPlaceholder()));
    }

    @EventHandler
    public void onArmorUnequip(ArmorEquipEvent e) {
        if (e.getOldArmorPiece() == null) return;
        if (e.getOldArmorPiece().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(e.getOldArmorPiece());
        if (!nbtItem.hasKey(AttributePlaceholder.MANA_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setMaxMana(attributedPlayer.getMaxMana() - nbtItem.getInteger(AttributePlaceholder.MANA_ARMOR.getPlaceholder()));
    }
}
