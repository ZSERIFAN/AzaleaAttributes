package by.thmdev.type.attributes;

import by.thmdev.api.AttributeManager;
import by.thmdev.api.AttributePlaceholder;
import by.thmdev.api.AttributedPlayer;
import by.thmdev.Attributes;
import com.codingforcookies.armorequip.ArmorEquipEvent;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class DefenseAttribute implements Listener {

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e) {
        if (e.getNewArmorPiece() == null) return;
        if (e.getNewArmorPiece().getType() == Material.AIR) return;
        NBTItem nbti = new NBTItem(e.getNewArmorPiece());
        if (!nbti.hasKey(AttributePlaceholder.DEFENSE_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setDefense(attributedPlayer.getDefense() + nbti.getInteger(AttributePlaceholder.DEFENSE_ARMOR.getPlaceholder()));
    }

    @EventHandler
    public void onArmorUnequip(ArmorEquipEvent e) {
        if (e.getOldArmorPiece() == null) return;
        if (e.getOldArmorPiece().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(e.getOldArmorPiece());
        if (!nbtItem.hasKey(AttributePlaceholder.DEFENSE_ARMOR.getPlaceholder())) return;
        Player p = e.getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        attributedPlayer.setDefense(attributedPlayer.getDefense() - nbtItem.getInteger(AttributePlaceholder.DEFENSE_ARMOR.getPlaceholder()));
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (!(entity instanceof Player)) return;
        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) return;
        Player p = ((Player) entity).getPlayer();
        AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(p);
        double defense = attributedPlayer.getDefense();
        int gradient = Attributes.cfg.getInt("defense-reduction-gradient");
        double reduction = defense/(defense + gradient);
        e.setDamage(e.getDamage() * (1 - reduction));
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity attacker = e.getDamager();
        if (!(attacker instanceof Player)) return;
        Player p = ((Player) attacker).getPlayer();
        AttributedPlayer attackerAttributed = AttributeManager.getAttributedPlayer(p);
        e.setDamage(attackerAttributed.getDamage());
        int crit = attackerAttributed.getCritChance().intValue();
        int hash = ThreadLocalRandom.current().nextInt(100);
        if (hash < crit) {
            e.setDamage(e.getDamage() * (attackerAttributed.getCritDmg() / 100.0));
        }
        Entity damaged = e.getEntity();
        if (!(damaged instanceof LivingEntity)) return;
        int def = 0;
        if (damaged instanceof Player) {
            AttributedPlayer attributedPlayer = AttributeManager.getAttributedPlayer(((Player) damaged).getPlayer());
            def = attributedPlayer.getDefense();
        } else {
            if (((LivingEntity) damaged).getEquipment().getHelmet() == null || ((LivingEntity) damaged).getEquipment().getHelmet().getType() == Material.AIR) def = 0;
            else {
                NBTItem comp = new NBTItem(((LivingEntity) damaged).getEquipment().getHelmet());
                if (comp.hasKey("defense"))
                    def = comp.getInteger("defense");
            }
        }
        System.out.println(def);
        int gradient = Attributes.cfg.getInt("defense-reduction-gradient");
        double reduction = (double) def / ((double) def + (double) gradient);
        e.setDamage(e.getDamage() * (1 - reduction));
        AtomicReference<Double> damageAmplifier = new AtomicReference<>(0.0);
        AtomicReference<Double> weaknessAmplifier = new AtomicReference<>(0.0);
        p.getActivePotionEffects().forEach(pe -> {
            if (pe.getType().equals(PotionEffectType.INCREASE_DAMAGE)) damageAmplifier.set((pe.getAmplifier() + 1) * 15.0);
        });
        p.getActivePotionEffects().forEach(pe -> {
            if (pe.getType().equals(PotionEffectType.WEAKNESS)) weaknessAmplifier.set((pe.getAmplifier() + 1) * 25.0);
        });
        double finalAmplifier = (damageAmplifier.get() - weaknessAmplifier.get())/100.0;
        e.setDamage(e.getDamage() + (finalAmplifier * e.getDamage()));
    }
}
