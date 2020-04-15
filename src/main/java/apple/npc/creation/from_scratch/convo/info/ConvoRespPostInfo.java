package apple.npc.creation.from_scratch.convo.info;

import java.util.List;

/**
 * The info for a base ConversationResponsePost (does not include the boolean expression for redirect requirements)
 */
public class ConvoRespPostInfo {
    public String responseId;
    public String globalCategory;
    public int localCategoryUID;
    public int conversationUID;
    public List<VariableChangeInfo> variableChanges;

    public ConvoRespPostInfo(int responseId, String globalCategory, int localCategoryUID, int conversationUID,
                             List<VariableChangeInfo> variableChanges) {
        this.responseId = String.valueOf(responseId);
        this.globalCategory = globalCategory;
        this.localCategoryUID = localCategoryUID;
        this.conversationUID = conversationUID;
        this.variableChanges = variableChanges;
    }
}
