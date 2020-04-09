package apple.npc.commands.edit.convo.detail;

import apple.npc.commands.CommandReferences;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EditNpcConvoLocalName implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public EditNpcConvoLocalName(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_CONVO_EDIT);
        if (command == null) {
            System.err.println("[NPCDialogue] could not get the " + CommandReferences.NPC_CONVO_EDIT + " command");
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        return true;
    }
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<String> tabComplete = new ArrayList<>();
        return null;
    }

}
