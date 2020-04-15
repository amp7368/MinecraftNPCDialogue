package apple.npc.creation.from_data.player;

import apple.npc.creation.from_data.convo.WriteConvoLocal;
import apple.npc.data.all.AllPlayers;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.data.player.VariableCategory;
import apple.npc.ymlNavigate.YMLFileNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WritePlayerAll {
    public static void write(String dataFolder, String playerUID) {
        File file = new File(String.format("%s%s%s%s%s%s", dataFolder, File.separator, YMLFileNavigate.PLAYER_FOLDER,
                File.separator, playerUID, YMLFileNavigate.YML));
        YamlConfiguration configOrig = YamlConfiguration.loadConfiguration(file);
        configOrig.set(YMLPlayerVariable.PLAYER_UID, playerUID);
        ConfigurationSection configVar = configOrig.createSection(YMLPlayerVariable.VARIABLES);
        HashMap<String, VariableCategory> globalCats = AllPlayers.getPlayer(playerUID).getVariables();
        for (String global : globalCats.keySet()) {
            WritePlayerVars.setConfig(configVar.createSection(String.valueOf(global)), globalCats.get(global));
        }

        try {
            configOrig.save(file);
        } catch (IOException e) {
            System.err.println("There was an error writing the global conversation.");
        }
    }
}
