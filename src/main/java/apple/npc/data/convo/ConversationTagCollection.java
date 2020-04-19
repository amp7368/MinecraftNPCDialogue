package apple.npc.data.convo;

import java.util.HashSet;
import java.util.Set;

public class ConversationTagCollection {
    public Set<ConversationTag> tags;

    public ConversationTagCollection(Set<ConversationTag> tags) {
        this.tags = tags;
    }

    public ConversationTagCollection() {
        tags = new HashSet<>();
    }

    public void add(ConversationTagCollection tags) {
        this.tags.addAll(tags.tags);
    }
}
