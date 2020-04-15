package apple.npc.commands.edit.convo.detail.resp;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.reading.command.response.var.ReadingConvoResponseVarVal;
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
        player.sendMessage(MessageUtils.LONG_DASH);

        player.sendMessage(String.format("%sWhat is the value of player variable %s-%s after choosing the response %s-%s-%s-%s-%s?",
                MessageUtils.EDITING, args[5], args[6], args[0], args[1], args[2], args[3], args[4]));

        player.sendMessage(MessageUtils.LONG_DASH);
        StopCommand.startListening(new ReadingConvoResponseVarVal(plugin, args[0], args[1], args[2], args[3], args[4], args[5], args[6]), player);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
