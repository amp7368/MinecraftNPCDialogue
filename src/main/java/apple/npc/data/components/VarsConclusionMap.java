package apple.npc.data.components;

import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class VarsConclusionMap implements Evaluateable {
    public int conclusionResult;
    private Evaluateable exp;

    public VarsConclusionMap(ConfigurationSection config) {
        exp = new BooleanExpRequirement(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION));
        conclusionResult = config.getInt(YMLBooleanNavigate.CONCLUSION_ID);
    }

    @Override
    public boolean evaluate(String playerUID, int currentConclusion) {
        return exp.evaluate(playerUID, currentConclusion);
    }
}
