package apple.npc.reading.command.response;

import apple.npc.reading.command.ReadingCommand;
import apple.npc.reading.command.ResponseType;
import org.bukkit.entity.Player;

public class ReadingConvoResponseLocal extends ReadingCommand {

    public ReadingConvoResponseLocal(String global, int local, int convo, int response, ResponseType normalPostResponse, String newGlobal, Player player) {
        super();
    }

    @Override
    public void dealWithStop(Player player) {

    }
}
