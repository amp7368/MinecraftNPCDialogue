package apple.npc.data.booleanAlgebra;

import apple.npc.ymlNavigate.YMLBooleanNavigate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

public class BooleanExpRequirement implements Evaluateable {
    private boolean isDefault;
    private boolean defaultVal;
    private Evaluateable exp;

    public BooleanExpRequirement(boolean isDefault, boolean defaultVal, Evaluateable exp) {
        this.isDefault = isDefault;
        this.defaultVal = defaultVal;
        this.exp = exp;
    }

    public BooleanExpRequirement(@Nullable ConfigurationSection config) {
        if (config == null) {
            isDefault = true;
            defaultVal = false;
            return;
        }
        ConfigurationSection exp1 = config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION_1);
        if (exp1 == null) {
            // then we should get either the default value or the variable comparison
            if (!config.contains(YMLBooleanNavigate.COMPARISON_TYPE)) {
                // we should get the default value
                isDefault = true;
                defaultVal = config.getBoolean(YMLBooleanNavigate.DEFAULT);
            } else if (config.contains(YMLBooleanNavigate.EXPRESSION)) {
                this.exp = BooleanRedirect.make(config.getConfigurationSection(YMLBooleanNavigate.EXPRESSION));
            } else {
                isDefault = true;
                defaultVal = false;
            }
        } else {
            isDefault = true;
            defaultVal = false;
        }
    }

    public BooleanExpRequirement(boolean setDefault) {
        isDefault = true;
        defaultVal = setDefault;
    }

    @Override
    public boolean evaluate(Player player, int currentConclusion, long timeLastTalked) {
        if (isDefault)
            return defaultVal;
        return exp.evaluate(player, currentConclusion, timeLastTalked);
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isDefaultVal() {
        return defaultVal;
    }

    public Evaluateable getExp() {
        return exp;
    }
}
