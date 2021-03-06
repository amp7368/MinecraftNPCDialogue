package apple.npc.data.convo;

import apple.npc.data.all.AllConversations;
import apple.npc.data.booleanAlgebra.Evaluateable;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

public class ConversationGlobalCategory {


    private HashMap<Integer, ConversationLocalCategory> localCategoryConversations;

    public ConversationGlobalCategory(YamlConfiguration config) {
        // we're just barely in the file
        localCategoryConversations = new HashMap<>();
        Set<String> localCategories = config.getKeys(false);
        for (String local : localCategories) {
            int localInt;
            try {
                localInt = Integer.parseInt(local);
            } catch (NumberFormatException e) {
                System.err.println("not numeric");
                continue;
            }
            ConfigurationSection configLocal = config.getConfigurationSection(local);
            if (configLocal == null)
                continue; // wtf happened if this happens?
            localCategoryConversations.put(localInt, new ConversationLocalCategory(configLocal));
        }
    }

    public ConversationGlobalCategory() {
        localCategoryConversations = new HashMap<>();
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
            if (category.getName().equals(local))
                localUIDs.add(category.getUid());
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

    public Collection<ConversationLocalCategory> getList(){
        return localCategoryConversations.values();
    }
    public HashMap<Integer, ConversationLocalCategory> getLocalCategoryConversations() {
        return localCategoryConversations;
    }

    public void createConvoLocal(String localName) {
        // iterate until you find an empty local uid
        int nextLocalUID = 0;
        while (hasLocalCategory(nextLocalUID)) {
            nextLocalUID++;
        }
        localCategoryConversations.put(nextLocalUID, new ConversationLocalCategory(nextLocalUID, localName));
    }

    public void createConvo(String global, int local, String convo, List<String> text) {
        if (hasLocalCategory(local)) {
            localCategoryConversations.get(local).createConvo(global, local, convo, text);
        }
    }

    public void createResponse(String global, int local, int convo, List<String> text) {
        if (hasLocalCategory(local)) {
            localCategoryConversations.get(local).createResponse(global, local, convo, text);
        }
    }

    public void setRedirectRequirements(int local, int convo, int responseUID, int redirectNum, Evaluateable exp) {
       if(hasLocalCategory(local)){
           localCategoryConversations.get(local).setRedirectRequirements(convo,responseUID,redirectNum,exp);
       }
    }
}
