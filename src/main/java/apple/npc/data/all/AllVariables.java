package apple.npc.data.all;

import apple.npc.creation.from_data.vars.WriteGlobalAll;
import apple.npc.data.player.Variable;
import apple.npc.data.player.VariableCategory;
import apple.npc.ymlNavigate.YMLFileNavigate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllVariables {
    public static HashMap<String, VariableCategory> allVars = new HashMap<>();
    private static File folder;

    public static void initialize(File dataFolder) {
        allVars = new HashMap<>();
        folder = dataFolder;
        File directory = new File(String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.VARIABLE_FOLDER));
        String[] pathNameList = directory.list();
        if (pathNameList == null) {
            System.err.println(String.format("%s%s%s", "Could not get any files with path name of \"", String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.VARIABLE_FOLDER), "\""));
            return;
        }
        for (String pathName : pathNameList) {
            File file = new File(String.format("%s%s%s%s%s", dataFolder, File.separator, YMLFileNavigate.PLAYER_FOLDER, File.separator, pathName));
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            allVars.put(pathName.replace(".yml", ""), new VariableCategory(config));
        }

    }

    public static void addVar(String global, Variable local) {
        if (!allVars.containsKey(global)) {
            allVars.put(global, new VariableCategory());
        }
        allVars.get(global).addVar(local);
        writeVar(global);
    }


    public static List<Integer> getVarLocalUIDs(String global, String localName) {
        if (allVars.containsKey(global)) {
            return allVars.get(global).getVarLocalUIDs(localName);
        }
        return new ArrayList<>();
    }

    public static int getNextUID(String global) {
        if (allVars.containsKey(global)) {
            return allVars.get(global).getNextUID();
        }
        return 0;
    }

    public static VariableCategory getVarCategory(String catName) {
        if (!allVars.containsKey(catName))
            allVars.put(catName, new VariableCategory());
        return allVars.get(catName);
    }

    public static int getVarUID(String respGlobal, String varName) {
        if (!allVars.containsKey(respGlobal))
            allVars.put(respGlobal, new VariableCategory());
        return allVars.get(respGlobal).getNextUID();
    }

    public static void readAll() {
        initialize(folder);
    }

    public static void writeAll() {
        for (String global : allVars.keySet()) {
            writeVar(global);
        }
    }

    private static void writeVar(String global) {
        WriteGlobalAll.write(folder.getPath(), global, allVars.get(global));
    }

    public static String getVarName(String global, int uid) {
        if (allVars.containsKey(global))
            return allVars.get(global).getVarName(uid);
        else
            return "null";
    }
}
