package by.thmdev.api;

public enum AttributePlaceholder {

    HEALTH_ARMOR("health"),
    MANA_ARMOR("mana"),
    DEFENSE_ARMOR("defense"),
    CRIT_DAMAGE_ARMOR("crit_dmg"),
    CRIT_CHANCE_ARMOR("crit_chance"),
    DAMAGE_ARMOR("damage"),
    ITEM_DAMAGE("bonus-damage"),
    ITEM_CRIT_DAMAGE("bonus-critdmg"),
    ITEM_CRIT_CHANCE("bonus-critchance"),
    ITEM_MANA("bonus-mana"),
    ITEM_MANA_COST("mana-cost");

    private String placeholder;

    AttributePlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }
}
