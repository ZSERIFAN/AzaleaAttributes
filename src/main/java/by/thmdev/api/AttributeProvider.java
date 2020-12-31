package by.thmdev.api;

import org.bukkit.entity.Player;

public final class AttributeProvider {

    private static AttributedPlayer instance = null;

    public static AttributedPlayer get(Player player) {
        instance = AttributeManager.getAttributedPlayer(player);
        if (instance == null) {
            throw new IllegalStateException("The AzaleaAttribute API is not loaded.");
        }
        return instance;
    }
}
