package apple.npc.commands.edit.boolean_algebra.commands;

import apple.npc.MessageUtils;
import apple.npc.afer_boolean.AfterPreResponse;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.BooleanSessionStart;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

///npc_convo_edit_response_preresponse bacon 0 0 0
public class BooleanConvoResponsePre implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanConvoResponsePre(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_PRERESPONSE);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_PRERESPONSE));
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (args.length != 4) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        String global = args[0];
        int localUID;
        int convoUID;
        int responseUID;
        try {
            localUID = Integer.parseInt(args[1]);
            convoUID = Integer.parseInt(args[2]);
            responseUID = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The second, third, and fourth arguments must be a number.");
            return false;
        }

        String localName = AllConversations.getLocalName(global, localUID);
        if (localName == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get local category %s:%d", global, localUID));
            return false;
        }
        ConversationData convo = AllConversations.get(new ConvoID(global, localUID, convoUID));
        if (convo == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get conversation %s:%d:%d", global, localName, convoUID));
            return false;
        }
        String convoName = convo.name;
        ConversationResponse response = convo.responses.get(responseUID);
        player.sendMessage(MessageUtils.EDITING + String.format("You are now editing the preresponse requirement in %s-%s-%s-%d", global, localName, convoName, responseUID));
        player.sendMessage(MessageUtils.EDITING + String.join("---", response.response));
        BooleanSessionStart.start(global, localUID, convoUID, responseUID, player);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}