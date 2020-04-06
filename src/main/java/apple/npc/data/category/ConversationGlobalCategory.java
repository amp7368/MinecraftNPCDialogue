package apple.npc.data.category;

import apple.npc.data.single.ConversationData;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConversationGlobalCategory {
    private HashMap<Integer, ConversationLocalCategory> localCategoryConversations;

    public ConversationGlobalCategory(YamlConfiguration config) {
        // we're just barely in the file
        localCategoryConversations = new HashMap<>();
        Set<String> localCategories = config.getKeys(false);
        for (String local : localCategories) {
            if (!StringUtils.isNumeric(local)) {
                System.err.println("not numeric");
                continue;
            }
            int localInt = Integer.parseInt(local);
            ConfigurationSection configLocal = config.getConfigurationSection(local);
            if (configLocal == null)
                continue; // wtf happened if this happens?
            localCategoryConversations.put(localInt, new ConversationLocalCategory(configLocal));
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
        if (hasLocalCategory(local))
            return localCategoryConversations.get(local).get(uid);
        return null;
    }

    public ConversationLocalCategory get(int local) {
        if (hasLocalCategory(local))
            return localCategoryConversations.get(local);
        return null;
    }

    public List<Integer> getLocalUIDs(String local) {
        List<Integer> localUIDs = new ArrayList<>();
        for (ConversationLocalCategory category : localCategoryConversations.values()) {
            if (category.name.equals(local))
                localUIDs.add(category.uid);
        }
        return localUIDs;
    }

    public List<Integer> getConvoUIDs(int localUID, String convoName) {
        if (hasLocalCategory(localUID))
            return localCategoryConversations.get(localUID).getConvoUIDs(convoName);
        return null;
    }

    public boolean hasLocalCategory(int local) {
        return localCategoryConversations.containsKey(local);
    }

    public boolean hasLocalCategory(String local) {
        for (ConversationLocalCategory category : localCategoryConversations.values()) {
            if (category.equalsLocalName(local))
                return true;
        }
        return false;
    }

}
