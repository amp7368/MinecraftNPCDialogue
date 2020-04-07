package apple.npc.commands;

import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
import apple.npc.exceptions.BadUIDException;
import apple.npc.exceptions.NoUIDException;
import apple.npc.reading.text.ReadingTextConvo;
import apple.npc.reading.text.ReadingTextResponse;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * A class for dealing with every creation command
 */
public class CreateRedirect {

    /**
     * creates a global category in conversations
     *
     * @param global the name of the global category we're trying to create (global is always String)
     * @param player the player requesting this
     */
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

    /**
     * creates a local category in conversation
     *
     * @param global the name of the global category we're going into (global is always String)
     * @param local  the String of the local category we're trying to make (just makes one with a new uid)
     * @param player the player requesting this service
     */
    public static void createConvoLocal(String global, String local, Player player) {
        System.out.println("Starting to create convoLocal for " + global + " " + local);
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

    /**
     * (probably will be replaced by making local an int)
     * creates a conversation with the request of text
     *
     * @param global    the global category (global is always String)
     * @param local     the local category (String so we'll need to differentiate)
     * @param convoName the convoName we're requesting (Creates a conversation with a new UID regardless)
     * @param player    the player requesting this service
     */
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
        StopCommand.startListening(new ReadingTextConvo(global, localUID, convoName), player);
    }


    /**
     * creates a response in the conversation
     * responses are supposed to be ordered but this just sticks the response on the end regardless of where you want it
     *
     * @param global    the global category (global is always a String)
     * @param local     the local category (String so we'll need to differentiate)
     * @param convoName the conversation name (String so we'll need to differentiate)
     * @param player    the player requesting the service
     */
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
        StopCommand.startListening(new ReadingTextResponse(global, localUID, convoUID), player);
    }

    /**
     * gets a list of all the uids in global with name of local
     *
     * @param global the global category (global is always String)
     * @param local  the local category we're trying to find the uids for
     * @return the list of all uids with name of local
     * @throws BadUIDException when the global category doesn't exist
     * @throws NoUIDException  when the local category doesn't have any uids associated with that name
     */
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

    /**
     * gets a list of all the uids in global in local with name of convoName
     *
     * @param global    the global category (global is always String)
     * @param localUID  the local category uid
     * @param convoName the name of the conversation we're trying to find the uids for
     * @return the list of all uids with name of convoName
     * @throws BadUIDException when the global or local category doesn't exist
     * @throws NoUIDException  when the local category doesn't have any uids associated with that name
     */
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

    /**
     * create a default npc with the name of name
     *
     * @param name   the name of the npc we're creating
     * @param player the player requesting this service
     */
    public static void createNpc(String name, Player player) {
        if (AllNPCs.makeNPC(name, player.getLocation())) {

        }
    }
}
