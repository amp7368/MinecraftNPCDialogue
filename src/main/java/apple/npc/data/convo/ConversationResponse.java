package apple.npc.data.convo;

import apple.npc.data.all.AllConversations;
import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.BooleanRedirect;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.npc.Opinion;
import apple.npc.data.player.Variable;
import apple.npc.ymlNavigate.YMLBooleanNavigate;
import apple.npc.ymlNavigate.YMLConversationNavigate;
import apple.npc.ymlNavigate.YMLPlayerVariable;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConversationResponse implements Evaluateable {
    private PostPlayerResponse defaultPostReponse;
    public int uid;
    private Evaluateable preResponseRequirement;
    public List<String> response;
    private List<PostPlayerResponse> postResponses;
    private Map<Variable,Integer> varChanges;

    public ConversationResponse(ConfigurationSection config) {
        this.uid = config.getInt(YMLConversationNavigate.RESPONSE_UID);
        this.preResponseRequirement = BooleanRedirect.make(config.getConfigurationSection(YMLConversationNavigate.PRE_RESPONSE_REQUIREMENT + "." + YMLBooleanNavigate.EXPRESSION));
        this.response = config.getStringList(YMLConversationNavigate.RESPONSE_TEXT);
        this.postResponses = getPostResponses(config.getConfigurationSection(YMLConversationNavigate.POST_RESPONSES));
        this.defaultPostReponse = new PostPlayerResponse(config.getConfigurationSection(String.format("%s",
                YMLConversationNavigate.DEFAULT_POST_RESPONSE)));
    }

    public ConversationResponse(String global, int local, int convo, int responseUID, List<String> text) {
        uid = responseUID;
        preResponseRequirement = new BooleanExpRequirement(true);
        response = text;
        postResponses = new ArrayList<>();
        defaultPostReponse = new PostPlayerResponse(global, local, convo);
    }

    private List<PostPlayerResponse> getPostResponses(ConfigurationSection config) {
        List<PostPlayerResponse> post = new ArrayList<>();
        Set<String> responseKeys = config.getKeys(false);
        for (String key : responseKeys) {
            post.add(new PostPlayerResponse(config.getConfigurationSection(key)));
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
        for (PostPlayerResponse post : postResponses) {
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
    public boolean evaluate(String playerUID, int currentConclusion, long timeLastTalked) {
        return preResponseRequirement.evaluate(playerUID, currentConclusion, timeLastTalked);
    }

    public ConvoID getPostResponse(Opinion opinion, long lastTalked, String playerUID) {
        for (PostPlayerResponse postResponse : postResponses) {
            if (postResponse.evaluate(playerUID, opinion.opinionUID, lastTalked)) {
                return postResponse.toNpcConvoID();
            }
        }
        if (defaultPostReponse.evaluate(playerUID, opinion.opinionUID, lastTalked)) {
            return defaultPostReponse.toNpcConvoID();
        }
        return null;
    }

    public PostPlayerResponse getDefaultPostReponse() {
        return defaultPostReponse;
    }

    public Evaluateable getPreResponseRequirement() {
        return preResponseRequirement;
    }

    public List<PostPlayerResponse> getPostResponses() {
        return postResponses;
    }

    public void setPreResponseRequirement(Evaluateable exp) {
        this.preResponseRequirement = exp;
        AllConversations.writeAll();
    }
}
