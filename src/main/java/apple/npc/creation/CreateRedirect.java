package apple.npc.creation;

import apple.npc.data.all.AllConversations;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CreateRedirect {
    public static String dataFolder;

    public static void initialize(String folder) {
        dataFolder = folder;
    }

    public static void createConvoGlobal(String global, Player player) {
        if (AllConversations.createConvoGlobal(global)) {
            player.sendMessage(String.format("Successfully made a conversation global category of %s", global));
        } else {
            if (AllConversations.hasGlobalCategory(global)) {
                player.sendMessage(String.format("The conversation global category of %s already exists", global));
            } else {
                player.sendMessage("Something went wrong making a global category of " + global);
            }
        }
    }

    public static void createConvoLocal(String global, String local, Player player) {
        if (AllConversations.createConvoLocal(global, local)) {
            player.sendMessage(String.format("Successfully made a conversation local category of %s in global category of %s", local, global));
        } else {
            if (!AllConversations.hasGlobalCategory(global)) {
                player.sendMessage(String.format("The conversation global category of %s does not exist.", global));
            } else {
                player.sendMessage("Something went wrong making a conversation local category of " + local);
            }
        }
    }

    public static void createConvo(String global, String local, String convoName, Player player) {
    }

    public static void createConvoResponse(String global, String local, String convoName, Player player) {
    }
}
