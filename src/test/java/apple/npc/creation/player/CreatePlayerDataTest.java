package apple.npc.creation.player;

import apple.npc.creation.npc.info.NpcInfo;
import apple.npc.creation.npc.single.CreateNpcData;
import apple.npc.creation.player.single.CreatePlayerData;
import apple.npc.ymlNavigate.YMLTestNpc;
import apple.npc.ymlNavigate.YMLTestPlayer;

public class CreatePlayerDataTest {
    public boolean createTest(String folder) {
        String globalName = YMLTestPlayer.PLAYER_UID;
        return CreatePlayerData.create(folder, globalName);
    }
}
