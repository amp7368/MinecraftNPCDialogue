package apple.npc.data.single;

import apple.npc.data.all.AllConversations;
import apple.npc.data.components.VarsConclusionMap;
import apple.npc.data.reference.ConvoID;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_14_R1.PacketPlayOutBoss;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class NPCData {
    public int uid;
    private String name;
    public String gameUID;
    private int startingConclusion;
    private Map<String, Integer> conclusion;
    private ArrayList<VarsConclusionMap> varsToConclusion;
    private Map<Integer, ConvoID> conclusionsToConvo;
    private Map<String, NPCPlayerData> playerDataMap;

    public NPCData(YamlConfiguration config) {
        playerDataMap = new HashMap<>();
        uid = config.getInt(YMLNpcNavigate.NPC_UID);
        name = config.getString(YMLNpcNavigate.NPC_NAME);
        gameUID = config.getString(YMLNpcNavigate.NPC_GAME_UID);
        startingConclusion = config.getInt(YMLNpcNavigate.STARTING_CONCLUSION);

        varsToConclusion = new ArrayList<>();
        ConfigurationSection varConcluConfig = config.getConfigurationSection(YMLNpcNavigate.VARS_TO_CONCLUSIONS);
        Set<String> varConcluKeys = varConcluConfig.getKeys(false);
        for (String varConcluKey : varConcluKeys) {
            System.out.println(varConcluKey);
            varsToConclusion.add(new VarsConclusionMap(varConcluConfig.getConfigurationSection(varConcluKey)));
        }
        conclusionsToConvo = mapConclusionsToConvo(config.getConfigurationSection(YMLNpcNavigate.CONCLUSIONS_TO_CONVO));

        ConfigurationSection playersUIDsConfig = config.getConfigurationSection(YMLNpcNavigate.PLAYERS_UID);
        Set<String> playersUIDs = playersUIDsConfig.getKeys(false);
        for (String uid : playersUIDs) {
            playerDataMap.put(uid, new NPCPlayerData(playersUIDsConfig.getConfigurationSection(uid)));
        }
    }

    private Map<Integer, ConvoID> mapConclusionsToConvo(ConfigurationSection config) {
        Map<Integer, ConvoID> map = new HashMap<>();
        Set<String> conclusions = config.getKeys(false);
        for (String conclusion : conclusions) {
            map.put(config.getInt(conclusion), new ConvoID(config.getConfigurationSection(conclusion)));
        }
        return map;
    }

    public String toString() {
        return String.format("uid:%d, name:%s, gameUID:%s", uid, name, gameUID);
    }

    public void doConversation(PlayerData playerData, Player realPlayer) {
        doConclusion(playerData);
        ConvoID conversation = null;
        String playerUID = playerData.uid;
        if (conclusionsToConvo.containsKey(conclusion.get(playerUID))) {
            conversation = conclusionsToConvo.get(conclusion.get(playerUID));
        } else {
            // pick a random conclusion
            for (Integer key : conclusionsToConvo.keySet())
                conversation = conclusionsToConvo.get(key);
            if (conversation == null) {
                // todo really log this fail
                return;
            }
            // todo log this fail
        }
        talkAtPlayer(realPlayer, conversation);
        givePlayerResponses(playerData, realPlayer, conversation);
    }

    private void givePlayerResponses(PlayerData playerData, Player realPlayer, ConvoID convoId) {
        List<ConversationResponse> responses = AllConversations.get(convoId).responses;
        for (ConversationResponse resp : responses) {
            if (resp.evaluate(playerData.uid, conclusion.get(playerData.uid))) {
                for (String textToSay : resp.response) {
                    TextComponent message = new TextComponent(textToSay);
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ping"));
                    realPlayer.spigot().sendMessage(message);
                }
            }
        }
        realPlayer.sendMessage("i sent you stuff");
    }

    private void talkAtPlayer(Player realPlayer, ConvoID convoID) {
        ConversationData convo = AllConversations.get(convoID);
        for (String text : convo.conversationText) {
            realPlayer.sendMessage(ChatColor.GREEN + text);
        }
    }

    private void doConclusion(PlayerData playerData) {
        String playerUID = playerData.uid;
        for (VarsConclusionMap map : varsToConclusion) {
            if (map.evaluate(playerUID, conclusion.get(playerUID))) {
                // update the conclusion
                conclusion.put(playerUID, map.conclusionResult);
                return;
            }
        }
        // otherwise do nothing to the conclusion
    }
}