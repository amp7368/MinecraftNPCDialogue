package apple.npc.data.all;

import apple.npc.creation.from_data.convo.WriteConvoAll;
import apple.npc.creation.from_scratch.convo.category.CreateConvoGlobal;
import apple.npc.creation.from_scratch.convo.category.CreateConvoLocal;
import apple.npc.creation.from_scratch.convo.info.ConvoDataInfo;
import apple.npc.creation.from_scratch.convo.info.ConvoLocalInfo;
import apple.npc.creation.from_scratch.convo.info.ConvoRespInfo;
import apple.npc.creation.from_scratch.convo.single.CreateConvoData;
import apple.npc.creation.from_scratch.convo.single.CreateConvoResponse;
import apple.npc.data.convo.ConversationGlobalCategory;
import apple.npc.data.convo.ConversationLocalCategory;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.convo.ConversationData;
import apple.npc.defaults.CreateConvoResponseRedirectDefault;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AllConversations {
    private static HashMap<String, ConversationGlobalCategory> allConversations;
    private static File dataFolder;

    public static void initialize(File folder) {
        dataFolder = folder;
        allConversations = new HashMap<>();
        File directory = new File(String.format("%s%s%s", folder.getPath(), File.separator, YMLFileNavigate.CONVERSATION_FOLDER));
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
        File file = new File(String.format("%s%s%s%s%s%s", dataFolder.getPath(), File.separator, YMLFileNavigate.CONVERSATION_FOLDER, File.separator, global, YMLFileNavigate.YML));
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        allConversations.put(global, new ConversationGlobalCategory(config));
    }

    private static void writeGlobal(String global) {
        if (hasGlobalCategory(global))
            WriteConvoAll.write(dataFolder.getPath(), allConversations.get(global), global);
    }

    public static boolean createConvoGlobal(String global) {
        if (!hasGlobalCategory(global)) {
            allConversations.put(global, new ConversationGlobalCategory());
            writeGlobal(global);
            return true;
        }
        return false;
    }


    public static boolean createConvoLocal(String global, String local) {
        if (!hasGlobalCategory(global)) {
            System.err.println("this global category doesn't exist");
            return false;
        }
        allConversations.get(global).createConvoLocal(local);
        writeGlobal(global);
        return true;
    }

    public static boolean createConvo(String global, int local, String convo, List<String> text) {
        if (!hasLocalCategory(global, local)) {
            return false;
        }
        allConversations.get(global).createConvo(global, local, convo, text);
        writeGlobal(global);
        return true;
    }

    public static boolean createResponse(String global, int local, int convo, List<String> text) {
        if (hasGlobalCategory(global)) {
            allConversations.get(global).createResponse(global, local, convo, text);
            writeGlobal(global);
        }
        return false;
    }

    public static ConversationData get(ConvoID convoID) {
        if (hasGlobalCategory(convoID.global))
            return allConversations.get(convoID.global).get(convoID.local, convoID.uid);
        return null;
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

    public static String getLocalName(String global, int local) {
        if (hasLocalCategory(global, local)) {
            ConversationLocalCategory cat = allConversations.get(global).get(local);
            if (cat == null)
                return null;
            return cat.getName();
        }
        return null;
    }

    public static ConversationLocalCategory getLocalCategory(String global, int local) {
        if (hasLocalCategory(global, local)) {
            ConversationLocalCategory cat = allConversations.get(global).get(local);
            return cat;
        }
        return null;
    }

    public static Collection<ConversationLocalCategory> getLocalList(String global) {
        if (hasGlobalCategory(global)){
            return allConversations.get(global).getList();
        }return null;
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

    public static void writeAll() {
        for (String global : allConversations.keySet()) {
            writeGlobal(global);
        }
    }

    public static void readAll() {
        initialize(dataFolder);
    }

}
