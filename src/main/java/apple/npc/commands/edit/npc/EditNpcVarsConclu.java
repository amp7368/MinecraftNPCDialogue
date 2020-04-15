package apple.npc.commands.edit.npc;

import apple.npc.ActionBar;
import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.StopCommand;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.npc.NPCData;
import apple.npc.reading.command.npc.edit.ReadingNpcConclusionNumVars;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class EditNpcVarsConclu implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcVarsConclu(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS));
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }

        if (args.length != 1) {
            commandSender.sendMessage(String.format(ChatColor.RED + "args length of %d is not valid.", args.length));
            return false;
        }
        int npcUID;
        try {
            npcUID = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(MessageUtils.BAD + "The first argument must be a number.");
            return false;
        }
        if (AllNPCs.hasUID(npcUID)) {
            Collection<Integer> conclusionList = AllNPCs.getConclusionList(npcUID);
            if (conclusionList == null) {
                // wtf happened we just checked that AllNPCs has a list
                player.sendMessage(MessageUtils.BAD + "There is no npc with uid " + npcUID);
                return false;
            }
            NPCData npc = AllNPCs.getNPCFromUID(npcUID);
            if (npc == null) {
                //todo enter a new npc
                return false;
            }
            String npcName = npc.name;
            TextComponent path = new TextComponent();
            path.setText(String.format("Npc Vars To Conclusion | %s", npcName));
            path.setBold(MessageUtils.PATH_BOLD);
            path.setColor(MessageUtils.PATH);
            ActionBar.sendLongActionBar(player, path);

            player.sendMessage(ChatColor.BLUE + "What conclusion would you like to set?");

            for (int conclusion : conclusionList) {
                player.sendMessage(MessageUtils.DASH);
                TextComponent conclu = new TextComponent();
                conclu.setText(String.format("(Edit Conclusion %d)", conclusion));
                conclu.setUnderlined(true);
                conclu.setColor(MessageUtils.EDITING_OPTION);
                conclu.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d %d",
                        CommandReferences.NPC_EDIT_VARS_SPECIFIC, npcUID, conclusion)));
                player.spigot().sendMessage(conclu);
            }

            StopCommand.startListening(new ReadingNpcConclusionNumVars(npcUID, plugin), player);
            player.sendMessage(MessageUtils.DASH);

            TextComponent back = new TextComponent();
            back.setText("(Back)");
            back.setUnderlined(true);
            back.setColor(MessageUtils.EDITING_OPTION);
            back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %d",
                    CommandReferences.NPC_NPC_EDIT, npcUID)));
            player.sendMessage("");

            player.sendMessage(MessageUtils.DASH);
            player.sendMessage(MessageUtils.LONG_DASH);

        } else {
            player.sendMessage(MessageUtils.LONG_DASH);
            player.sendMessage(MessageUtils.BAD + "There is no npc with uid " + npcUID);
            player.sendMessage(MessageUtils.LONG_DASH);
            return false;
        }


        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

}
