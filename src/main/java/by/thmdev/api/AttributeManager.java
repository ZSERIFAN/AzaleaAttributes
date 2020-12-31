package by.thmdev.api;

import by.thmdev.type.AttributeType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class AttributeManager {

    private static HashMap<UUID, AttributedPlayer> attributedPlayers = new HashMap<>();

    public static void addAttributedPlayer(Player player, AttributedPlayer attributedPlayer) {
        attributedPlayers.put(player.getUniqueId(), attributedPlayer);
    }

    public static void removeAttributedPlayer(Player player) {
        attributedPlayers.remove(player.getUniqueId());
    }

    public static AttributedPlayer getAttributedPlayer(Player player) {
        return attributedPlayers.get(player.getUniqueId());
    }

    public static void updateDefault(Player player, AttributeType attributeType, Double value) {
        AttributedPlayer attributedPlayer = getAttributedPlayer(player);
        JsonParser parser = new JsonParser();
        JsonObject object = null;
        try {
            object = parser.parse(new FileReader(attributedPlayer.getPlayerFile())).getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(attributedPlayer.getPlayerFile())) {
            switch (attributeType) {
                case HEALTH:
                    object.addProperty("health", value);
                    break;
                case MANA:
                    object.addProperty("mana", value);
                    break;
                case DEFENSE:
                    object.addProperty("defense", value);
                    break;
                case CRIT_DAMAGE:
                    object.addProperty("crit_dmg", value);
                    break;
                case CRIT_CHANCE:
                    object.addProperty("crit_chance", value);
                    break;
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String prettyString = gson.toJson(object);
            writer.write(prettyString);
            System.out.println("[AzaleaAttributes] Updated field " + attributeType.toString() + " for player " + player.getName() + ".");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
