package apple.npc.creation.category;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateConvoGlobal {
    private static String dataFolder;

    public static void initialize(String folder) {
        dataFolder = folder;
    }

    public static boolean create(String globalName) {
        String folder = String.format("%s%s%s", dataFolder, File.separator, "conversationData");
        File directory = new File(folder);
        String[] pathNameList = directory.list();
        boolean doesContain = false;
        for (String pathName : pathNameList) {
            if (pathName.equals(globalName)) {
                doesContain = true;
                break;
            }
        }
        if (doesContain)
            return false;
        File newFile;
        try {
            newFile = new File(String.format("%s%s%s%s", folder, File.separator, globalName, ".yml"));
            boolean didCreate = newFile.createNewFile();
            if (!didCreate)
                return false;
            FileWriter writer = new FileWriter(newFile);
            writer.append("# global category names are unique");
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
