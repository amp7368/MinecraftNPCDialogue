package apple.npc.reading.command.npc.edit;

import apple.npc.data.all.AllNPCs;
import apple.npc.reading.command.ReadingCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class ReadingNpc extends ReadingCommand {

    @Override
    public void dealWithStop(Player player) {
        String command = super.command;
        List<Integer> uids = AllNPCs.getNpcUids(command);
        if (uids.isEmpty()) {
            player.sendMessage(ChatColor.RED + String.format("There is no %s npc", command));
            return;
        }
        if (uids.size() != 1) {
            player.sendMessage(ChatColor.BLUE + String.format("There are %d npc(s) named %s", uids.size(), command));
            return;
        }
        int uid = uids.get(0);
        player.sendMessage(ChatColor.BLUE + String.format("What would you like to edit about %s (uid=%d)?", command, uid));
        TextComponent editName = new TextComponent();
        editName.setText("(Name)");
        editName.setUnderlined(true);
        editName.setColor(ChatColor.GREEN);
        editName.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_name " + uid));

        TextComponent startingConclusion = new TextComponent();
        startingConclusion.setText("(Starting Conclusion)");
        startingConclusion.setUnderlined(true);
        startingConclusion.setColor(ChatColor.GREEN);
        startingConclusion.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_startingconclusion " + uid));

        TextComponent startLocation = new TextComponent();
        startLocation.setText("(start location)");
        startLocation.setUnderlined(true);
        startLocation.setColor(ChatColor.GREEN);
        startLocation.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_startLocation " + uid));

        TextComponent startPosition = new TextComponent();
        startPosition.setText("(start position)");
        startPosition.setUnderlined(true);
        startPosition.setColor(ChatColor.GREEN);
        startPosition.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_startPosition " + uid));

        TextComponent varsToConclusions = new TextComponent();
        varsToConclusions.setText("(Vars to Conclusion)");
        varsToConclusions.setUnderlined(true);
        varsToConclusions.setColor(ChatColor.GREEN);
        varsToConclusions.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_varsToConclusions " + uid));

        TextComponent conclusionsToConvoUid = new TextComponent();
        conclusionsToConvoUid.setText("(Conclusion to Convo)");
        conclusionsToConvoUid.setUnderlined(true);
        conclusionsToConvoUid.setColor(ChatColor.GREEN);
        conclusionsToConvoUid.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/npc_edit_conclusionsToConvo " + uid));

        TextComponent separator = new TextComponent();
        separator.setText("   ");

        player.spigot().sendMessage(editName, separator, startingConclusion, separator, startLocation, separator, startPosition, separator, varsToConclusions, separator, conclusionsToConvoUid);
    }
}
