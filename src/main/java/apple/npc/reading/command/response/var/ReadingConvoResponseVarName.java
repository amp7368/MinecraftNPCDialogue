package apple.npc.reading.command.response.var;

import apple.npc.commands.CommandReferences;
import apple.npc.reading.command.ReadingCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReadingConvoResponseVarName extends ReadingCommand {
    private String global;
    private String local;
    private String con;
    private String resp;
    private String post;

    private String respGlobal;
    private JavaPlugin plugin;

    public ReadingConvoResponseVarName(JavaPlugin plugin, String global, String local, String con, String resp, String post, String respGlobal) {
        this.global = global;
        this.local = local;
        this.con = con;
        this.resp = resp;
        this.post = post;
        this.respGlobal = respGlobal;
        this.plugin = plugin;
    }

    @Override
    public void dealWithStop(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.performCommand(String.format(
                "%s %s %s %s %s %s %s %s", CommandReferences.NPC_CONVO_EDIT_RESPONSE_VAR_VAL, global, local, con, resp, post, respGlobal, command)));
    }
}
