package apple.npc.commands;

import apple.npc.NPCDialogueMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CreateCommand implements CommandExecutor {
    JavaPlugin plugin;

    public CreateCommand(NPCDialogueMain plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("scroll_create");
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the scroll_create command");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        return false;
    }
}
