package apple.npc.creation.from_data.convo;

import apple.npc.data.convo.ConversationGlobalCategory;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WriteConvoAll {
    public static void write(String dataFolder, ConversationGlobalCategory global, String globalName) {
        File file = new File(String.format("%s%s%s%s%s%s", dataFolder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER,
                File.separator, globalName, YMLFileNavigate.YML));
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.err.println("There was an error making a new file in the global conversation.");
                    return;
                }
            } catch (IOException e) {
                System.err.println("There was an error  making a new file in the global conversation.");
                return;
            }
        }
        YamlConfiguration configOrig = YamlConfiguration.loadConfiguration(file);
        HashMap<Integer, ConversationLocalCategory> localCats = global.getLocalCategoryConversations();
        for (Integer local : localCats.keySet()) {
            WriteConvoLocal.setConfig(configOrig.createSection(String.valueOf(local)), localCats.get(local));
        }

        try {
            configOrig.save(file);
        } catch (IOException e) {
            System.err.println("There was an error writing the global conversation.");
        }
    }
}
