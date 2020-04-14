package apple.npc.commands;

import apple.npc.commands.edit.boolean_algebra.commands.*;
import apple.npc.commands.edit.convo.detail.resp.*;
import apple.npc.commands.edit.npc.*;
import apple.npc.commands.edit.convo.*;
import apple.npc.commands.edit.npc.concluCon.*;
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

        // npc main branch
        new EditNpcSpecific(plugin);

        // varsConclusion main branch
        new EditNpcVarsConclu(plugin);
        new EditNpcVarsConcluSpecific(plugin);

        new BooleanConvoResponsePre(plugin);

        // boolean main branch
        new BooleanCompCommand(plugin);
        new BooleanCompTypeCommand(plugin);
        new BooleanDoubleCommand(plugin);
        new BooleanDoubleTypeCommand(plugin);
        new BooleanCompLocalCommand(plugin);

        // conclusionVars main branch
        new EditNpcConcluConGlobal(plugin);
        new EditNpcConcluConLocal(plugin);
        new EditNpcConcluConCon(plugin);
        new EditNpcConcluConMake(plugin);

        // convo main branch
        new EditNpcConvo(plugin);
        new EditNpcConvoGlobal(plugin);
        new EditNpcConvoLocal(plugin);
        new EditNpcConvoConvo(plugin);
        new EditNpcConvoResponse(plugin);
        new EditNpcConvoResponseDetails(plugin);

        // response branch
        new EditNpcConvoResponsePost(plugin);
        new EditNpcConvoResponseLocal(plugin);
        new EditNpcConvoResponseConvo(plugin);
        new EditnpcConvoResponseSet(plugin);
        new EditNpcConvoResponseVar(plugin);

        new EditNpc(plugin);
        new EditNpcName(plugin);
        new EditNpcConclusion(plugin);
        new RespondToNpcCommand(plugin);

        new CommandPing(plugin);
        new StopCommand(plugin);
    }
}
