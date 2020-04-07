package apple.npc.data.convo;

import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConversationResponse implements Evaluateable {
    private final ConversationData.PostPlayerResponse defaultPostReponse;
    public int uid;
    private Evaluateable preResponseRequirement;
    public List<String> response;
    private List<ConversationData.PostPlayerResponse> postResponses;

    public ConversationResponse(ConfigurationSection config) {
        this.uid = config.getInt(YMLConversationNavigate.RESPONSE_UID);
        this.preResponseRequirement = new BooleanExpRequirement(config.getConfigurationSection(YMLConversationNavigate.PRE_RESPONSE_REQUIREMENT));
        this.response = config.getStringList(YMLConversationNavigate.RESPONSE_TEXT);
        this.postResponses = getPostResponses(config.getConfigurationSection(YMLConversationNavigate.POST_RESPONSES));
        this.defaultPostReponse = new ConversationData.PostPlayerResponse(config.getConfigurationSection(String.format("%s%c%s",
                YMLConversationNavigate.DEFAULT_POST_RESPONSE, '.', YMLBooleanNavigate.EXPRESSION)));
    }

    private List<ConversationData.PostPlayerResponse> getPostResponses(ConfigurationSection config) {
        List<ConversationData.PostPlayerResponse> post = new ArrayList<>();
        Set<String> responseKeys = config.getKeys(false);
        for (String key : responseKeys) {
            post.add(new ConversationData.PostPlayerResponse(config.getConfigurationSection(key)));
        }
        return post;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("uid: ");
        string.append(uid);
        string.append("<boolean>");
        string.append("text: {");
        for (String text : response) {
            string.append(text);
            string.append(" ");
        }
        string.append("}");
        string.append("postResponses:\n");
        for (ConversationData.PostPlayerResponse post : postResponses) {
            string.append(post.toString());
            string.append("\n");
        }
        string.append("\n");
        string.append("defaultPostResponse ");
        string.append(defaultPostReponse.toString());
        string.append("\n");
        return string.toString();
    }

    @Override
    public boolean evaluate(String playerUID, int currentConclusion) {
        return preResponseRequirement.evaluate(playerUID, currentConclusion);
    }

}