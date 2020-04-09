package apple.npc.commands;

import apple.npc.commands.edit.NpcCommand;
import apple.npc.commands.edit.NpcConclusionToConvo;
import apple.npc.commands.makeCon.*;
import apple.npc.commands.makeNpc.MakeNpc;
import apple.npc.commands.talking.RespondToNpcCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * initializes all of the commands
 */
public class Commands {
    public static void initialize(JavaPlugin plugin) {
        new MakeCon(plugin);
        new MakeConGlobal(plugin);
        new MakeConLocal(plugin);
        new MakeConResponse(plugin);
        new CommandPing(plugin);
        new StopCommand(plugin);
        new MakeNpc(plugin);

        new NpcCommand(plugin);
        new NpcConclusionToConvo(plugin);
        new RespondToNpcCommand(plugin);
    }
}
