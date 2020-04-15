package apple.npc.afer_boolean;

import apple.npc.data.all.AllConversations;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.convo.PostPlayerResponse;
import org.bukkit.entity.Player;

public class AfterPostResponse implements AfterVar {
    private String global;
    private int local;
    private int convo;
    private int responseUID;
    private int redirectNum;

    public AfterPostResponse(String global, int local, int convo, int response, int redirect) {
        this.global = global;
        this.local = local;
        this.convo = convo;
        this.responseUID = response;
        this.redirectNum = redirect;
    }

    @Override
    public void dealWithDone(Player player, Evaluateable exp) {
        AllConversations.setRedirectRequirements(global,local,convo,responseUID,redirectNum,exp);

    }
}
