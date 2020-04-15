package apple.npc.commands.edit.convo.detail.resp.editing;

import apple.npc.ActionBar;
import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConvoID;
import apple.npc.reading.command.response.var.ReadingConvoResponseVarVal;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class EditNpcConvoResponseVarVal implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponseVarVal(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_VAL);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_VAL));
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
        if (args.length != 7) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        String global = args[0];
        int local;
        int convo;
        int response;
        int redirect;
        try {
            local = Integer.parseInt(args[1]);
            convo = Integer.parseInt(args[2]);
            response = Integer.parseInt(args[3]);
            redirect = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            player.sendMessage(String.format("%sArgument length of %d is invalid", MessageUtils.BAD, args.length));
            return false;
        }
        String localName = AllConversations.getLocalName(global, local);
        if (localName == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get local category %s:%d", global, local));
            return false;
        }
        ConversationData conversationData = AllConversations.get(new ConvoID(global, local, convo));
        if (conversationData == null) {
            player.sendMessage(MessageUtils.BAD + String.format("I could not get conversation %s:%s:%d", global, localName, convo));
            return false;
        }

        TextComponent path = new TextComponent();
        path.setText(String.format("Convo | Global-Local-Convo-Response-Redirect-(VarChange) | %s-%s-%s-%d-%d", global, localName, conversationData.name, response, redirect));
        path.setBold(MessageUtils.PATH_BOLD);
        path.setColor(MessageUtils.PATH);
        ActionBar.sendLongActionBar(player, path);


        player.sendMessage(MessageUtils.LONG_DASH);

        player.sendMessage(String.format("%sWhat is the value of player variable %s-%s after choosing the response %s-%s-%s-%s-%s?",
                MessageUtils.EDITING, args[5], args[6], global, localName, conversationData.name, args[3], args[4]));

        player.sendMessage(MessageUtils.LONG_DASH);
        StopCommand.startListening(new ReadingConvoResponseVarVal(plugin, args[0], args[1], args[2], args[3], args[4], args[5], args[6]), player);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
