package apple.npc.commands;

import apple.npc.commands.edit.EditNpc;
import apple.npc.commands.edit.convo.EditNpcConvo;
import apple.npc.commands.edit.NpcConclusionToConvo;
import apple.npc.commands.edit.convo.EditNpcConvoConvo;
import apple.npc.commands.edit.convo.EditNpcConvoGlobal;
import apple.npc.commands.edit.convo.EditNpcConvoLocal;
import apple.npc.commands.make.convo.MakeCon;
import apple.npc.commands.make.convo.MakeConGlobal;
import apple.npc.commands.make.convo.MakeConLocal;
import apple.npc.commands.make.convo.MakeConResponse;
import apple.npc.commands.make.npc.MakeNpc;
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
        new MakeNpc(plugin);


        new NpcCommand(plugin);

        new EditNpcConvo(plugin);
        new EditNpcConvoGlobal(plugin);
        new EditNpcConvoLocal(plugin);
        new EditNpcConvoConvo(plugin);

        new EditNpc(plugin);
        new NpcConclusionToConvo(plugin);
        new RespondToNpcCommand(plugin);

        new CommandPing(plugin);
        new StopCommand(plugin);
    }
}
