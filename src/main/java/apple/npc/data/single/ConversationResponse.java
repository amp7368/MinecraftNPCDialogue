package apple.npc.data.single;

import apple.npc.data.booleanAlgebra.BooleanExpRequirement;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.components.PostPlayerResponse;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConversationResponse {
    private final PostPlayerResponse defaultPostReponse;
    private int uid;
    private Evaluateable preResponseRequirement;
    private List<String> response;
    private List<PostPlayerResponse> postResponses;

    public ConversationResponse(ConfigurationSection config) {
        this.uid = config.getInt("uid");
        this.preResponseRequirement = new BooleanExpRequirement(config.getConfigurationSection("preResponseRequirement"));
        this.response = config.getStringList("response");
        this.postResponses = getPostResponses(config.getConfigurationSection("postResponses"));
        this.defaultPostReponse = new PostPlayerResponse(config.getConfigurationSection("defaultPostResponse"));
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
}
