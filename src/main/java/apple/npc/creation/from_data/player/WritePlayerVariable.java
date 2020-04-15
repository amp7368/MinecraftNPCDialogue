package apple.npc.creation.from_data.player;

import apple.npc.data.player.Variable;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

public class WritePlayerVariable {
    public static void setConfig(ConfigurationSection config, Variable variable) {
        config.set(YMLPlayerVariable.VARIABLE_UID, variable.uid);
        config.set(YMLPlayerVariable.VARIABLE_NAME, variable.name);
        config.set(YMLPlayerVariable.VARIABLE_VALUE, variable.value);
    }
}
