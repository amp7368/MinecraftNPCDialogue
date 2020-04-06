package apple.npc.commands;

import apple.npc.commands.makeCon.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands {
    public static void initialize(JavaPlugin plugin) {
        new MakeCon(plugin);
        new MakeConGlobal(plugin);
        new MakeConLocal(plugin);
        new MakeConResponse(plugin);
        new CommandPing(plugin);
        new ConStopCommand(plugin);
    }
}
