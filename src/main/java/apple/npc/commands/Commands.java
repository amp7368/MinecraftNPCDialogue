package apple.npc.commands;

import apple.npc.commands.makeCon.MakeCon;
import apple.npc.commands.makeCon.MakeConGlobal;
import apple.npc.commands.makeCon.MakeConLocal;
import apple.npc.commands.makeCon.MakeConResponse;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands {
    public static void initialize(JavaPlugin plugin){
        new MakeCon(plugin);
        new MakeConGlobal(plugin);
        new MakeConLocal(plugin);
        new MakeConResponse(plugin);
        new CommandPing(plugin);
    }
}
