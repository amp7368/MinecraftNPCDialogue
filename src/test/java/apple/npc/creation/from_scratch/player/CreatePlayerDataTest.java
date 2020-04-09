package apple.npc.creation.from_scratch.player;

import apple.npc.creation.from_scratch.player.single.CreatePlayerData;
import apple.npc.ymlNavigate.YMLTestPlayer;

public class CreatePlayerDataTest {
    public boolean createTest(String folder) {
        String globalName = YMLTestPlayer.PLAYER_UID;
        return CreatePlayerData.create(folder, globalName);
    }
}
