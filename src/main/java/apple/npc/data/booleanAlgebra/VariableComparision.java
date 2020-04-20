package apple.npc.data.booleanAlgebra;

import apple.npc.data.all.AllPlayers;
import apple.npc.data.all.AllVariables;
import apple.npc.data.player.Variable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class VariableComparision implements Evaluateable {
    private boolean isNoted;
    private int comparisonType;
    private long comparisonValue;

    private String comparisonVarGlobal;
    private int comparisonVarUID;
    private String comparisumVarName;

    public VariableComparision(boolean isNoted, int comparisonType, long comparisonValue,
                               String comparisonVarGlobal, int comparisonVarUID, String comparisumVarName) {
        this.isNoted = isNoted;
        this.comparisonType = comparisonType;
        this.comparisonValue = comparisonValue;
        this.comparisonVarGlobal = comparisonVarGlobal;
        this.comparisonVarUID = comparisonVarUID;
        this.comparisumVarName = comparisumVarName;
        AllVariables.addVar(comparisonVarGlobal, new Variable(comparisonVarUID, comparisumVarName));
    }

    public VariableComparision(ConfigurationSection config) {
        isNoted = config.getBoolean(YMLBooleanNavigate.IS_NOTED);
        comparisonType = config.getInt(YMLBooleanNavigate.COMPARISON_TYPE);
        comparisonValue = config.getLong(YMLBooleanNavigate.COMPARISON_VALUE);
        comparisonVarGlobal = config.getString(String.format("%s%c%s", YMLBooleanNavigate.COMPARISON_VAR, '.', YMLBooleanNavigate.GLOBAL_CATEGORY));
        comparisonVarUID = config.getInt(String.format("%s%c%s", YMLBooleanNavigate.COMPARISON_VAR, '.', YMLBooleanNavigate.VAR_UID));
        comparisumVarName = config.getString(String.format("%s%c%s", YMLBooleanNavigate.COMPARISON_VAR, '.', YMLBooleanNavigate.VAR_NAME));
        // every time a new variable comparison is read, try to put it in player variables list of variables
        AllVariables.addVar(comparisonVarGlobal, new Variable(comparisonVarUID, comparisumVarName));
    }

    @Override
    public boolean evaluate(Player player, int currentConclusion, long timeLastTalked) {
        String playerUID = player.getUniqueId().toString();
        long varVal;
        switch (comparisonVarGlobal) {
            case "default":
                switch (comparisumVarName) {
                    case "conclusion":
                        varVal = currentConclusion;
                        break;
                    case "time":
                        varVal = System.currentTimeMillis() - timeLastTalked;
                        break;
                    default:
                        varVal = AllPlayers.getVarVal(playerUID, comparisonVarGlobal, comparisonVarUID);
                }
                break;
            default:
                varVal = AllPlayers.getVarVal(playerUID, comparisonVarGlobal, comparisonVarUID);
        }


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

    public int getComparisonType() {
        return comparisonType;
    }

    public long getComparisonValue() {
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
