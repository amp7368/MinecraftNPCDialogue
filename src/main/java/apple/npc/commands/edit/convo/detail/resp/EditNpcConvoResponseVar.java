package apple.npc.commands.edit.convo.detail.resp;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllPlayers;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConvoID;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public class EditNpcConvoResponseVar implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponseVar(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_GLOBAL);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_GLOBAL));
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
        player.sendMessage(MessageUtils.LONG_DASH);

        player.sendMessage(String.format("%sWhat global category of player variables do you want to change in %s-%s-%s-%s",
                MessageUtils.EDITING, args[0], args[1], args[2], args[3]));

        Set<String> varGlobals = AllPlayers.allVars.keySet();
        for (String varGlobal : varGlobals) {
            TextComponent globalText = new TextComponent();
            globalText.setText(String.format("(Edit %s)", varGlobal));
            globalText.setColor(MessageUtils.EDITING_OPTION);
            globalText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %s %s %s %s",
                    CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_LOCAL, args[0], args[1], args[2], args[3], varGlobal)));
            player.spigot().sendMessage(globalText);
        }

        player.sendMessage(MessageUtils.LONG_DASH);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
