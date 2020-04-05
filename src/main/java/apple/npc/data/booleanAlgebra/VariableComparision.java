package apple.npc.data.booleanAlgebra;

import apple.npc.data.all.AllPlayers;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class VariableComparision implements Evaluateable {
    private boolean isNoted;
    private boolean isConclusionVar;
    private int comparisonType;
    private int comparisonValue;
    private String comparisonVarGlobal;
    private int comparisonVarUID;

    public VariableComparision(ConfigurationSection config) {
        isNoted = config.getBoolean(YMLBooleanNavigate.IS_NOTED);
        comparisonType = config.getInt(YMLBooleanNavigate.COMPARISON_TYPE);
        comparisonValue = config.getInt(YMLBooleanNavigate.COMPARISON_VALUE);
        if (config.contains(YMLBooleanNavigate.IS_CONCLUSION_VAR)) {
            isConclusionVar = true;
        } else {
            isConclusionVar = false;
            comparisonVarGlobal = config.getString(String.format("%s%c%s",YMLBooleanNavigate.COMPARISON_VAR,'.',YMLBooleanNavigate.GLOBAL_CATEGORY));
            comparisonVarUID = config.getInt(String.format("%s%c%s",YMLBooleanNavigate.COMPARISON_VAR,'.',YMLBooleanNavigate.VAR_UID));
        }
    }

    @Override
    public boolean evaluate(String playerUID, int currentConclusion) {
        int varVal;
        if (isConclusionVar)
            varVal = currentConclusion;
        else
            varVal = AllPlayers.getVarVal(playerUID, comparisonVarGlobal, comparisonVarUID);

        boolean result;
        switch (comparisonType) {
            case -2:
                result = varVal < comparisonValue;
                break;
            case -1:
                result = varVal <= comparisonValue;
                break;
            case 0:
                result = varVal == comparisonValue;
                break;
            case 1:
                result = varVal >= comparisonValue;
                break;
            default:
                result = varVal > comparisonValue;
        }
        if (isNoted)
            result = !result;
        return result;
    }
}