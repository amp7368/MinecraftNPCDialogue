package apple.npc.data.npc;

import apple.npc.data.booleanAlgebra.BooleanRedirect;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class VarsConclusionMap implements Evaluateable {
    public int conclusionResult;
    private Evaluateable exp;

    public VarsConclusionMap(ConfigurationSection config) {
        exp = BooleanRedirect.make(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION));
        conclusionResult = config.getInt(YMLNpcNavigate.CONCLUSION_ID);
    }

    public VarsConclusionMap(int conclusionResult, Evaluateable finished) {
        this.conclusionResult = conclusionResult;
        this.exp = finished;
    }

    @Override
    public boolean evaluate(String playerUID, int currentConclusion, long timeLastTalked) {
        return exp.evaluate(playerUID, currentConclusion, timeLastTalked);
    }

    public Evaluateable getExpression() {
        return exp;
    }

    public void setExpression(Evaluateable finished) {
        this.exp = finished;
    }
}
