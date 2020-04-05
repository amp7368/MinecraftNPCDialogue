package apple.npc.creation.player;

import apple.npc.creation.player.info.PlayerVarInfo;
import apple.npc.creation.player.single.CreatePlayerVars;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import apple.npc.ymlNavigate.YMLTestPlayer;

public class CreatePlayerDataVarsTest extends CreatePlayerDataTest {
    public boolean createTest(String folder) {
        String globalName = YMLTestPlayer.PLAYER_UID;
        PlayerVarInfo var = new PlayerVarInfo(YMLPlayerVariable.GLOBAL, YMLPlayerVariable.UID, YMLPlayerVariable.NAME, YMLPlayerVariable.VALUE);
        return CreatePlayerVars.set(folder, globalName, var);
    }
}
