package apple.npc.commands.edit.convo.detail.resp.editing;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllPlayers;
import apple.npc.data.player.Variable;
import apple.npc.data.player.VariableCategory;
import apple.npc.reading.command.response.var.ReadingConvoResponseVarName;
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

public class EditNpcConvoResponseVarName implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoResponseVarName(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_LOCAL);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_LOCAL));
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
        if (args.length != 6) {
            player.sendMessage(MessageUtils.BAD + "Invalid number of arguments");
            return false;
        }
        player.sendMessage(MessageUtils.LONG_DASH);

        player.sendMessage(String.format("%sWhat is the local name of player variable in %s after choosing the response %s-%s-%s-%s-%s?",
                MessageUtils.EDITING, args[5], args[0], args[1], args[2], args[3],args[4]));


        VariableCategory varLocals = AllPlayers.allVars.get(args[5]);
        for (Variable var : varLocals.getVariables().values()) {
            TextComponent globalText = new TextComponent();
            globalText.setText(String.format("(Edit %s-%d)", var.name, var.uid));
            globalText.setColor(MessageUtils.EDITING_OPTION);
            globalText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %s %s %s %s %s %d",
                    CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_VAL, args[0], args[1], args[2], args[3], args[4] ,args[5], var.uid)));
            player.spigot().sendMessage(globalText);
        }

        player.sendMessage(MessageUtils.LONG_DASH);
        StopCommand.startListening(new ReadingConvoResponseVarName(plugin,args[0], args[1], args[2], args[3],args[4],args[5]), player);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
