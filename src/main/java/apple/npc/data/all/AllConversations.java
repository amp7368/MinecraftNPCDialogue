package apple.npc.data.all;

import apple.npc.data.category.ConversationGlobalCategory;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class AllConversations {
    private static HashMap<String, ConversationGlobalCategory> allConversations;

    public static void initialize(JavaPlugin plugin) {
        allConversations = new HashMap<>();
        File directory = new File(String.format("%s%s%s", plugin.getDataFolder(), File.separator, "conversationData"));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", plugin.getDataFolder(), File.separator, "npcData"), "\""));
            return;
        }
        for (String globalCategory : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", plugin.getDataFolder(), File.separator, "conversationData", File.separator, globalCategory));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            ConversationGlobalCategory global = new ConversationGlobalCategory(config);
            allConversations.put(globalCategory, global);
        }
    }
}
