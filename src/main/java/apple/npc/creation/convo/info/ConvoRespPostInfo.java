package apple.npc.creation.convo.info;

import java.util.List;

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
