package apple.npc.data.category;

import apple.npc.data.single.ConversationData;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Set;

public class ConversationGlobalCategory {
    private HashMap<Integer, ConversationLocalCategory> localCategoryConversations;

    public ConversationGlobalCategory(YamlConfiguration config) {
        // we're just barely in the file
        localCategoryConversations = new HashMap<>();
        Set<String> localCategories = config.getKeys(false);
        for (String local : localCategories) {
            if (!StringUtils.isNumeric(local))
                continue;
            int localInt = Integer.parseInt(local);
            localCategoryConversations.put(localInt, new ConversationLocalCategory(config.getConfigurationSection(local)));
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Integer key : localCategoryConversations.keySet()) {
            string.append(key);
            string.append(": ");
            string.append(localCategoryConversations.get(key).toString());
            string.append("\n");
        }
        return string.toString();
    }

    public ConversationData get(int local, int uid) {
        return localCategoryConversations.get(local).get(uid);
    }
}
