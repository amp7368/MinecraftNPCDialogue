package apple.npc.reading.command.response.var;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllVariables;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.convo.PostPlayerResponse;
import apple.npc.data.player.Variable;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadingConvoResponseVarVal extends ReadingCommand {
    private String global;
    private String local;
    private String con;
    private String resp;
    private String post;

    private String respGlobal;
    private String respName;

    private JavaPlugin plugin;

    public ReadingConvoResponseVarVal(JavaPlugin plugin, String global, String local, String con, String resp, String post, String respGlobal, String respName) {
        this.global = global;
        this.local = local;
        this.con = con;
        this.resp = resp;
        this.post = post;
        this.respGlobal = respGlobal;
        this.respName = respName;

        this.plugin = plugin;
    }

    @Override
    public void dealWithStop(Player player) {
        int respNameInt;
        int respVal;

        int localInt;
        int conInt;
        int respInt;
        int postInt;
        try {
            respNameInt = Integer.parseInt(respName);
        } catch (NumberFormatException e) {
            player.sendMessage("This variableName argument must be a number");
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.performCommand(String.format(
                    "%s %s %s %s %s %s %s", CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_LOCAL, global, local, con, resp, respGlobal)));
            return;
        }
        try {
            respVal = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            player.sendMessage("This value must be a number");
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.performCommand(String.format(
                    "%s %s %s %s %s %s %s", CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_VAL, global, local, con, resp, respGlobal, respName)));
            return;
        }
        try {
            localInt = Integer.parseInt(local);
            conInt = Integer.parseInt(con);
            respInt = Integer.parseInt(resp);
            postInt = Integer.parseInt(post);
        } catch (NumberFormatException e) {
            player.sendMessage("The local, conversation, and response arguments must be numbers try going through the menu again");
            return;
        }

        ConversationData convo = AllConversations.get(new ConvoID(global, localInt, conInt));
        if (convo == null) {
            player.sendMessage(MessageUtils.BAD + "The conversation was wrong. Try again.");
            return;
        }
        ConversationResponse response = convo.get(respInt);
        if (response == null) {
            player.sendMessage(MessageUtils.BAD + "The conversation was wrong. Try again.");
            return;
        }
        PostPlayerResponse post;
        if (postInt == -1) {
            post = response.getDefaultPostReponse();
        } else {
            post = response.getPostResponses().get(postInt);
        }
        if (post == null) {
            player.sendMessage(MessageUtils.BAD + "The responseId was wrong. Try again.");
            return;
        }

        post.putVarChange(respGlobal, new Variable(respNameInt, respName), respVal);
        AllVariables.addVar(respGlobal,new Variable(respNameInt,respName));
        AllConversations.writeGlobal(global);
        player.sendMessage(MessageUtils.GOOD + "You just set the response variable change");

    }
}
