package by.thmdev.api;

import org.bukkit.entity.Player;

public interface Attribute {

    void setPlayer(Player player);

    Player getPlayer();

    void setRegen(Integer value);

    Integer getRegen();

    void setMaxHealth(Integer value);

    Integer getMaxHealth();

    void setMaxMana(Integer value);

    Integer getMaxMana();

    void setAdditionalMana(Integer value);

    Integer getAdditionalMana();

    void setAdditionalDamage(Integer value);

    Integer getAdditionalDamage();

    void setCurrentMana(Integer value);

    Integer getCurrentMana();

    void setDefense(Integer value);

    Integer getDefense();

    void setDamage(Integer value);

    Integer getDamage();

    void setCritDmg(Double value);

    Double getCritDmg();

    void setCritChance(Double value);

    Double getCritChance();
}
