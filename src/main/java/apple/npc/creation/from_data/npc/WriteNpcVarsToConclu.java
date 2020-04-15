package apple.npc.creation.from_data.npc;

import apple.npc.creation.from_data.bool.WriteBoolean;
import apple.npc.data.npc.VarsConclusionMap;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class WriteNpcVarsToConclu {
    public static void setConfig(ConfigurationSection config, ArrayList<VarsConclusionMap> varsToConclusion) {
        int i = 0;
        for (VarsConclusionMap varMap : varsToConclusion) {
            ConfigurationSection subConfig = config.createSection(String.valueOf(i++));
            subConfig.set(YMLNpcNavigate.CONCLUSION_ID, varMap.conclusionResult);
            WriteBoolean.setConfig(subConfig.createSection(YMLBooleanNavigate.EXPRESSION),varMap.getExpression());
        }
    }
}
