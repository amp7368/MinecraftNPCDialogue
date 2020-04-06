package apple.npc.creation.convo.category;

import apple.npc.ymlNavigate.YMLFileNavigate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateConvoGlobal {


    /**
     * create a global category if it doesn't already exist
     *
     * @param dataFolder the datafolder the file should exist
     * @param globalName the name of the new file
     * @return whether the creation was successful
     */
    public static boolean create(String dataFolder, String globalName) {
        String folder = String.format("%s%s%s", dataFolder, File.separator, YMLFileNavigate.CONVERSATION_FOLDER);
        File directory = new File(folder);
        String[] pathNameList = directory.list();
        if (pathNameList != null)
            for (String pathName : pathNameList) {
                if (pathName.equals(globalName)) {
                    return false;
                }
            }
        File newFile;
        try {
            newFile = new File(String.format("%s%s%s%s", folder, File.separator, globalName, YMLFileNavigate.YML));
            boolean didCreate = newFile.createNewFile();
            if (!didCreate) {
                return false;
            }
            FileWriter writer = new FileWriter(newFile);
            writer.append("# global category names are unique");
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
