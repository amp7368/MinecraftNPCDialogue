package apple.npc.defaults;


import apple.npc.creation.from_scratch.convo.components.CreatePostDefault;
import apple.npc.creation.from_scratch.convo.info.ConvoRespPostInfo;

import java.util.ArrayList;

public class CreateConvoResponseRedirectDefault {
    public static void create(String path, String global, int local, int convo, int reponseUID) {
        CreatePostDefault.set(path, global, local, convo, reponseUID,
                new ConvoRespPostInfo(reponseUID, global, local, convo, new ArrayList<>()));
    }
}
