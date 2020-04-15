package apple.npc.afer_boolean;

import apple.npc.data.all.AllConversations;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
import org.bukkit.entity.Player;

public class AfterPreResponse implements AfterVar {
    private String global;
    private int local;
    private int convo;
    private int responseUID;

    public AfterPreResponse(String global, int local, int convo, int response) {
        this.global = global;
        this.local = local;
        this.convo = convo;
        this.responseUID = response;
    }

    @Override
    public void dealWithDone(Player player, Evaluateable exp) {
        ConversationData conversation = AllConversations.get(new ConvoID(global, local, convo));
        ConversationResponse response = conversation.responses.get(responseUID);
        response.setPreResponseRequirement(exp);
    }
}
