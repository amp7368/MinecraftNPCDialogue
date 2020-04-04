package apple.npc.data.booleanAlgebra;

import apple.npc.data.all.AllPlayers;
import org.bukkit.configuration.ConfigurationSection;

public class VariableComparision implements Evaluateable {
    private boolean isNoted;
    private int comparisonType;
    private int comparisonValue;
    private String comparisonVarGlobal;
    private int comparisonVarUID;

    public VariableComparision(ConfigurationSection config) {
        isNoted = config.getBoolean("isNoted");
        comparisonType = config.getInt("comparisonType");
        comparisonValue = config.getInt("comparisonValue");
        comparisonVarGlobal = config.getString("comparisonVar:globalCategory");
        comparisonVarUID = config.getInt("comparisonVar.varUID");
    }

    @Override
    public boolean evaluate(String playerUID) {
        int varVal = AllPlayers.getVarVal(playerUID, comparisonVarGlobal, comparisonVarUID);
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
