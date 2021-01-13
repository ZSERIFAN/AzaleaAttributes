package by.thmdev.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;

public class AttributedPlayer implements Attribute {

    private Player player;
    private Integer maxHealth;
    private Integer maxMana;
    private Integer currentMana;
    private Integer defense;
    private Integer damage;
    private Double critDmg;
    private Double critChance;
    private File playerData;
    private Integer regenPer5;
    private Integer additionalHealth;
    private Integer additionalDefense;
    private Integer additionalMana;
    private Integer additionalDamage;
    private Integer additionalCritDamage;
    private Integer additionalCritChance;

    public AttributedPlayer(Player player) {
        File playerData = new File("plugins/AzaleaAttributes/data/" + player.getUniqueId() + ".json");
        this.playerData = playerData;
        this.player = player;
        JsonParser parser = new JsonParser();
        try {
            JsonObject object = parser.parse(new FileReader(playerData)).getAsJsonObject();
            this.maxHealth = object.get("health").getAsInt();
            player.setMaxHealth(this.maxHealth);
            player.setHealthScale(20.0D + this.getMaxHealth()/250.0D);
            player.setHealth(this.maxHealth);
            this.maxMana = object.get("mana").getAsInt();
            this.currentMana = this.maxMana;
            this.defense = object.get("defense").getAsInt();
            this.critDmg = object.get("crit_dmg").getAsDouble();
            this.critChance = object.get("crit_chance").getAsDouble();
            this.damage = object.get("damage").getAsInt();
            this.regenPer5 = this.getMaxHealth()/20;
            this.additionalMana = 0;
            this.additionalDamage = 0;
            this.additionalCritChance = 0;
            this.additionalCritDamage = 0;
            this.additionalHealth = 0;
            this.additionalDefense = 0;
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item == null || item.getType() == Material.AIR) continue;
                NBTItem nbtItem = new NBTItem(item);
                if (nbtItem.hasKey(AttributePlaceholder.HEALTH_ARMOR.getPlaceholder())) {
                    this.maxHealth += nbtItem.getInteger(AttributePlaceholder.HEALTH_ARMOR.getPlaceholder());
                    player.setMaxHealth(this.getMaxHealth());
                    player.setHealth(player.getMaxHealth());
                }
                if (nbtItem.hasKey(AttributePlaceholder.DEFENSE_ARMOR.getPlaceholder())) this.defense += nbtItem.getInteger(AttributePlaceholder.DEFENSE_ARMOR.getPlaceholder());
                if (nbtItem.hasKey(AttributePlaceholder.MANA_ARMOR.getPlaceholder())) {
                    this.maxMana += nbtItem.getInteger(AttributePlaceholder.MANA_ARMOR.getPlaceholder());
                    this.currentMana = this.maxMana;
                }
                if (nbtItem.hasKey(AttributePlaceholder.DAMAGE_ARMOR.getPlaceholder())) this.damage += nbtItem.getInteger(AttributePlaceholder.DAMAGE_ARMOR.getPlaceholder());
                if (nbtItem.hasKey(AttributePlaceholder.CRIT_DAMAGE_ARMOR.getPlaceholder())) this.critDmg += nbtItem.getInteger(AttributePlaceholder.CRIT_DAMAGE_ARMOR.getPlaceholder());
                if (nbtItem.hasKey(AttributePlaceholder.CRIT_CHANCE_ARMOR.getPlaceholder())) this.critChance += nbtItem.getInteger(AttributePlaceholder.CRIT_CHANCE_ARMOR.getPlaceholder());
            }
            AttributeManager.addAttributedPlayer(player, this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public File getPlayerFile() {
        return this.playerData;
    }

    public void setRegen(Integer value) {
        this.regenPer5 = value;
    }

    public Integer getRegen() {
        return this.regenPer5;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setMaxHealth(Integer value) {
        this.maxHealth = value;
    }

    public Integer getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxMana(Integer value) {
        this.maxMana = value;
    }

    public Integer getMaxMana() {
        return this.maxMana;
    }

    public void setAdditionalMana(Integer value) {
        this.additionalMana = value;
    }

    public Integer getAdditionalMana() {
        return this.additionalMana;
    }

    public void setAdditionalDamage(Integer value) {
        this.additionalDamage = value;
    }

    public Integer getAdditionalDamage() {
        return this.additionalDamage;
    }

    public void setAdditionalCritDamage(Integer value) {
        this.additionalCritDamage = value;
    }

    public Integer getAdditionalCritDamage() {
        return this.additionalCritDamage;
    }

    public void setAdditionalCritChance(Integer value) {
        this.additionalCritChance = value;
    }

    public Integer getAdditionalCritChance() {
        return this.additionalCritChance;
    }

    public void setAdditionalHealth(Integer value) {
        this.additionalHealth = value;
    }

    public Integer getAdditionalHealth() {
        return this.additionalHealth;
    }

    public void setAdditionalDefense(Integer value) {
        this.additionalDefense = value;
    }

    public Integer getAdditionalDefense() {
        return this.additionalDefense;
    }

    public void setCurrentMana(Integer value) {
        this.currentMana = value;
    }

    public Integer getCurrentMana() {
        return this.currentMana;
    }

    public void setDefense(Integer value) {
        this.defense = value;
    }

    public Integer getDefense() {
        return this.defense;
    }

    public void setDamage(Integer value) {
        this.damage = value;
    }

    public Integer getDamage() {
        return this.damage;
    }

    public void setCritDmg(Double value) {
        this.critDmg = value;
    }

    public Double getCritDmg() {
        return this.critDmg;
    }

    public void setCritChance(Double value) {
        this.critChance = value;
    }

    public Double getCritChance() {
        return this.critChance;
    }
}
