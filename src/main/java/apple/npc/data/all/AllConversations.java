package apple.npc.data.all;

import apple.npc.data.category.ConversationGlobalCategory;
import apple.npc.data.reference.ConvoID;
import apple.npc.data.single.ConversationData;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class AllConversations {
    private static HashMap<String, ConversationGlobalCategory> allConversations;

    public static void initialize(File dataFolder) {
        allConversations = new HashMap<>();
        File directory = new File(String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER), "\""));
            return;
        }
        for (String globalCategory : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", dataFolder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, globalCategory));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            ConversationGlobalCategory global = new ConversationGlobalCategory(config);
            allConversations.put(globalCategory.replace(".yml", ""), global);
        }
    }

    public static ConversationData get(ConvoID convoID) {
        return allConversations.get(convoID.global).get(convoID.local, convoID.uid);
    }
}