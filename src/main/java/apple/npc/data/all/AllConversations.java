package apple.npc.data.all;

import apple.npc.creation.convo.category.CreateConvoGlobal;
import apple.npc.creation.convo.category.CreateConvoLocal;
import apple.npc.creation.convo.info.ConvoDataInfo;
import apple.npc.creation.convo.info.ConvoLocalInfo;
import apple.npc.creation.convo.single.CreateConvoData;
import apple.npc.data.category.ConversationGlobalCategory;
import apple.npc.data.category.ConversationLocalCategory;
import apple.npc.data.reference.ConvoID;
import apple.npc.data.single.ConversationData;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllConversations {
    private static HashMap<String, ConversationGlobalCategory> allConversations;
    private static File dataFolder;

    public static void initialize(File folder) {
        dataFolder = folder;
        allConversations = new HashMap<>();
        File directory = new File(String.format("%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER), "\""));
            return;
        }
        for (String globalCategory : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", folder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, globalCategory));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            ConversationGlobalCategory global = new ConversationGlobalCategory(config);
            allConversations.put(globalCategory.replace(".yml", ""), global);
        }
    }

    private static void readGlobal(String global) {
        // read that category in
        File file = new File(String.format("%s%s%s%s%s", dataFolder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, global));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        allConversations.put(global, new ConversationGlobalCategory(config));
    }

    public static boolean createConvoGlobal(String global) {
        if (CreateConvoGlobal.create(dataFolder.getPath(), global)) {
            readGlobal(global);
            return true;
        } else {
            return false;
        }
    }


    public static boolean createConvoLocal(String global, String local) {
        if (!hasGlobalCategory(global))
            return false;
        ConversationGlobalCategory globalCategory = allConversations.get(global);
        // iterate until you find an empty local uid
        int nextLocalUID = 0;
        while (globalCategory.hasLocalCategory(nextLocalUID)) {
            nextLocalUID++;
        }
        ;
        if (CreateConvoLocal.create(dataFolder.getPath(), global, new ConvoLocalInfo(nextLocalUID, local))) {
            readGlobal(global);
            return true;
        } else {
            return false;
        }
    }

    public static boolean createConvo(String global, int local, String convo, List<String> text) {
        if (!hasLocalCategory(global, local)) {
            return false;
        }
        ConversationLocalCategory localCategory = allConversations.get(global).get(local);
        if (localCategory == null)
            return false; // we already checked this. wtf happened?

        // iterate until you find an empty convo uid
        int convoUID = 0;
        while (localCategory.convoUIDExists(convoUID)) {
            convoUID++;
        }
        return CreateConvoData.create(dataFolder.getPath(), global, local, new ConvoDataInfo(convoUID, convo, text));
    }

    public static ConversationData get(ConvoID convoID) {
        return allConversations.get(convoID.global).get(convoID.local, convoID.uid);
    }

    public static List<Integer> getLocalUIDs(String global, String local) {
        if (hasGlobalCategory(global)) {
            return allConversations.get(global).getLocalUIDs(local);
        } else {
            return null;
        }
    }

    public static List<Integer> getConvoUIDs(String global, int localUID, String convoName) {
        if (hasLocalCategory(global, localUID)) {
            return allConversations.get(global).getConvoUIDs(localUID, convoName);
        } else
            return null;
    }

    public static boolean hasGlobalCategory(String global) {
        return allConversations.containsKey(global);
    }

    public static boolean hasLocalCategory(String global, int local) {
        if (!hasGlobalCategory(global))
            return false;
        return allConversations.get(global).hasLocalCategory(local);
    }

    public static boolean hasLocalCategory(String global, String local) {
        if (!hasGlobalCategory(global))
            return false;
        return allConversations.get(global).hasLocalCategory(local);
    }


}
