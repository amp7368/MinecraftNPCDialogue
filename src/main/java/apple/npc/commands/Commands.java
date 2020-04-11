package apple.npc.commands;

import apple.npc.commands.edit.convo.detail.resp.EditNpcConvoResponseLocal;
import apple.npc.commands.edit.convo.detail.resp.EditNpcConvoResponsePost;
import apple.npc.commands.edit.npc.EditNpc;
import apple.npc.commands.edit.convo.*;
import apple.npc.commands.edit.npc.EditNpcConclusionToConvo;
import apple.npc.commands.edit.npc.EditNpcName;
import apple.npc.commands.io.ReadCommand;
import apple.npc.commands.io.WriteCommand;
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

        new WriteCommand(plugin);
        new ReadCommand(plugin);

        new NpcCommand(plugin);

        // main branch
        new EditNpcConvo(plugin);
        new EditNpcConvoGlobal(plugin);
        new EditNpcConvoLocal(plugin);
        new EditNpcConvoConvo(plugin);
        new EditNpcConvoResponse(plugin);
        new EditNpcConvoResponseDetails(plugin);

        // response branch
        new EditNpcConvoResponsePost(plugin);
        new EditNpcConvoResponseLocal(plugin);

        new EditNpc(plugin);
        new EditNpcName(plugin);
        new EditNpcConclusionToConvo(plugin);
        new RespondToNpcCommand(plugin);

        new CommandPing(plugin);
        new StopCommand(plugin);
    }
}
