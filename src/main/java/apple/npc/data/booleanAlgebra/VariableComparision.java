package apple.npc.data.booleanAlgebra;

import apple.npc.data.all.AllPlayers;
import apple.npc.data.player.Variable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;

public class VariableComparision implements Evaluateable {
    private boolean isNoted;
    private boolean isConclusionVar;
    private int comparisonType;
    private int comparisonValue;

    private String comparisonVarGlobal;
    private int comparisonVarUID;
    private String comparisumVarName;

    public VariableComparision(boolean isNoted, boolean isConclusionVar, int comparisonType, int comparisonValue,
                               String comparisonVarGlobal, int comparisonVarUID, String comparisumVarName) {
        this.isNoted = isNoted;
        this.isConclusionVar = isConclusionVar;
        this.comparisonType = comparisonType;
        this.comparisonValue = comparisonValue;
        this.comparisonVarGlobal = comparisonVarGlobal;
        this.comparisonVarUID = comparisonVarUID;
        this.comparisumVarName = comparisumVarName;
        AllPlayers.addVar(comparisonVarGlobal, new Variable(comparisonVarUID, comparisumVarName));
    }

    public VariableComparision(ConfigurationSection config) {
        isNoted = config.getBoolean(YMLBooleanNavigate.IS_NOTED);
        comparisonType = config.getInt(YMLBooleanNavigate.COMPARISON_TYPE);
        comparisonValue = config.getInt(YMLBooleanNavigate.COMPARISON_VALUE);
        if (config.contains(YMLBooleanNavigate.IS_CONCLUSION_VAR) && config.getBoolean(YMLBooleanNavigate.IS_CONCLUSION_VAR)) {
            isConclusionVar = true;
        } else {
            isConclusionVar = false;
            comparisonVarGlobal = config.getString(String.format("%s%c%s", YMLBooleanNavigate.COMPARISON_VAR, '.', YMLBooleanNavigate.GLOBAL_CATEGORY));
            comparisonVarUID = config.getInt(String.format("%s%c%s", YMLBooleanNavigate.COMPARISON_VAR, '.', YMLBooleanNavigate.VAR_UID));
            comparisumVarName = config.getString(String.format("%s%c%s", YMLBooleanNavigate.COMPARISON_VAR, '.', YMLBooleanNavigate.VAR_NAME));
            // every time a new variable comparison is read, try to put it in player variables list of variables
            AllPlayers.addVar(comparisonVarGlobal, new Variable(comparisonVarUID, comparisumVarName));
        }
    }

    @Override
    public boolean evaluate(String playerUID, int currentConclusion, long timeLastTalked) {
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


    public boolean isNot() {
        return isNoted;
    }

    public boolean isConclusionVar() {
        return isConclusionVar;
    }

    public int getComparisonType() {
        return comparisonType;
    }

    public int getComparisonValue() {
        return comparisonValue;
    }

    public String getComparisonVarGlobal() {
        return comparisonVarGlobal;
    }

    public int getComparisonVarUID() {
        return comparisonVarUID;
    }

    public String getComparisumVarName() {
        return comparisumVarName;
    }

    public String getComparisonVarName() {
        return comparisumVarName;
    }
}
