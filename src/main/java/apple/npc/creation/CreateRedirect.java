package apple.npc.creation;

import apple.npc.commands.makeCon.ConStopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.exceptions.BadUIDException;
import apple.npc.exceptions.NoUIDException;
import org.bukkit.entity.Player;

import java.util.List;

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
            player.sendMessage(String.format("Successfully made a conversation %s.%s", global, local));
        } else {
            if (!AllConversations.hasGlobalCategory(global)) {
                player.sendMessage(String.format("The conversation global category of %s does not exist.", global));
            } else {
                player.sendMessage("Something went wrong making a conversation local category of " + local);
            }
        }
    }

    public static void createConvo(String global, String local, String convoName, Player player) {
        if (!AllConversations.hasGlobalCategory(global)) {
            player.sendMessage(String.format("The conversation global category of %s does not exist.", global));
            return;
        }
        if (!AllConversations.hasLocalCategory(global, local)) {
            player.sendMessage(String.format("The conversation local category of %s does not exist for category %s.", local, global));
            return;
        }
        List<Integer> localUIDs;
        try {
            localUIDs = getUIDLocal(global, local);
        } catch (BadUIDException e) {
            player.sendMessage(e.getMessage());
            return;
        } catch (NoUIDException e) {
            player.sendMessage(String.format("There is no local category under %s.%s", global, local));
            return;
        }
        int localUID;
        if (localUIDs.size() > 1) {
            // todo deal with multiple local uids
            return;
        } else {
            localUID = localUIDs.get(0);
        }
        ConStopCommand.startListening(global, localUID, convoName, player);
    }


    public static void createConvoResponse(String global, String local, String convoName, Player player) {
        if (!AllConversations.hasLocalCategory(global, local)) {
            player.sendMessage(String.format("The conversation local category of %s does not exist for category %s.", local, global));
            return;
        }
        List<Integer> localUIDs;
        try {
            localUIDs = getUIDLocal(global, local);
        } catch (BadUIDException e) {
            player.sendMessage(e.getMessage());
            return;
        } catch (NoUIDException e) {
            player.sendMessage(String.format("There is no local category under %s.%s", global, local));
            return;
        }
        int localUID;
        if (localUIDs.size() > 1) {
            // todo deal with multiple local uids
            return;
        } else {
            localUID = localUIDs.get(0);
        }
        List<Integer> convoUIDs;
        try {
            convoUIDs = getUIDConvo(global, localUID, convoName);
        } catch (BadUIDException e) {
            player.sendMessage(e.getMessage());
            return;
        } catch (NoUIDException e) {
            player.sendMessage(String.format("There is no conversation under %s.%s.%s", global, local, convoName));
            return;
        }
        int convoUID;
        if (convoUIDs.size() > 1) {
            // todo deal with multiple convo uids
            return;
        } else {
            convoUID = convoUIDs.get(0);
        }
        ConStopCommand.startListening(global, localUID, convoUID, player);
    }


    private static List<Integer> getUIDLocal(String global, String local) throws BadUIDException, NoUIDException {
        List<Integer> localUIDs = AllConversations.getLocalUIDs(global, local);
        if (localUIDs == null) {
            // this should never happen
            throw new BadUIDException(String.format("The uid %s does not exist. This shouldn't happen.", global));
        }
        if (localUIDs.isEmpty()) {
            // notify the user that they had a bad name for a conversation
            throw new NoUIDException("There is no uid with name " + local);
        }
        return localUIDs;
    }

    private static List<Integer> getUIDConvo(String global, int localUID, String convoName) throws BadUIDException, NoUIDException {
        List<Integer> convoUIDs = AllConversations.getConvoUIDs(global, localUID, convoName);
        if (convoUIDs == null) {
            // this should never happen
            throw new BadUIDException(String.format("The uid %d does not exist. This shouldn't happen.", localUID));
        }
        if (convoUIDs.isEmpty()) {
            // notify the user that they had a bad name for a conversation
            throw new NoUIDException("There is no uid with name " + convoName);
        }
        return convoUIDs;
    }
}
