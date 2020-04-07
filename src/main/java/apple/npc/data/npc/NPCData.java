package apple.npc.data.npc;

import apple.npc.data.all.AllConversations;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.NpcConvoID;
import apple.npc.data.player.PlayerData;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class NPCData {
    public int uid;
    public String name;
    public String gameUID;
    private int startingConclusion;
    private ArrayList<VarsConclusionMap> varsToConclusion;
    private Map<Integer, NpcConvoID> conclusionsToConvo;
    private Map<String, NPCPlayerData> playerDataMap;
    private long maxTimeSinceTalk;

    public NPCData(YamlConfiguration config) {
        playerDataMap = new HashMap<>();
        uid = config.getInt(YMLNpcNavigate.NPC_UID);
        name = config.getString(YMLNpcNavigate.NPC_NAME);
        gameUID = config.getString(YMLNpcNavigate.NPC_GAME_UID);
        startingConclusion = config.getInt(YMLNpcNavigate.STARTING_CONCLUSION);
        maxTimeSinceTalk = config.getInt(YMLNpcNavigate.MAX_TIME_SINCE_LAST_TALK);
        varsToConclusion = new ArrayList<>();
        ConfigurationSection varConcluConfig = config.getConfigurationSection(YMLNpcNavigate.VARS_TO_CONCLUSIONS);
        Set<String> varConcluKeys = varConcluConfig.getKeys(false);
        for (String varConcluKey : varConcluKeys) {
            varsToConclusion.add(new VarsConclusionMap(varConcluConfig.getConfigurationSection(varConcluKey)));
        }
        conclusionsToConvo = mapConclusionsToConvo(config.getConfigurationSection(YMLNpcNavigate.CONCLUSIONS_TO_CONVO));

        ConfigurationSection playersUIDsConfig = config.getConfigurationSection(YMLNpcNavigate.PLAYERS_UID);
        Set<String> playersUIDs = playersUIDsConfig.getKeys(false);
        for (String uid : playersUIDs) {
            playerDataMap.put(uid, new NPCPlayerData(playersUIDsConfig.getConfigurationSection(uid)));
        }
    }

    private Map<Integer, NpcConvoID> mapConclusionsToConvo(ConfigurationSection config) {
        Map<Integer, NpcConvoID> map = new HashMap<>();
        Set<String> conclusions = config.getKeys(false);
        for (String conclusion : conclusions) {
            int conclusionNum;
            try {
                conclusionNum = Integer.parseInt(conclusion);
            } catch (NumberFormatException e) {
                System.out.println("there was a conclusion that was not a number");
                continue;
            }
            map.put(conclusionNum, new NpcConvoID(config.getConfigurationSection(conclusion)));
        }
        return map;
    }

    public String toString() {
        return String.format("uid:%d, name:%s, gameUID:%s", uid, name, gameUID);
    }

    /**
     * do a conversation
     * if the time limit is up, recheck what we should do
     * else do a normal conversation segment
     *
     * @param playerData
     * @param realPlayer
     */
    public void doConversation(PlayerData playerData, Player realPlayer) {
        String playerUID = realPlayer.getUniqueId().toString();

        int currentOpinion;
        // if we talked to the player before
        if (playerDataMap.containsKey(playerUID)) {
            if (System.currentTimeMillis() - playerDataMap.get(playerUID).lastTalked > maxTimeSinceTalk) {
                // the player waited too long
                currentOpinion = doConclusion(playerUID);
            } else {
                currentOpinion = playerDataMap.get(playerUID).opinion.opinionUID;
            }
        } else {
            // check what we should say
            currentOpinion = doConclusion(playerUID);
        }
        System.out.println(currentOpinion);
        NpcConvoID conversation = doConclusionToConvo(currentOpinion);
        if (!playerDataMap.containsKey(playerUID)) {
            playerDataMap.put(playerUID, new NPCPlayerData(playerUID, conversation, System.currentTimeMillis(),
                    new Opinion(currentOpinion, "name will be made eventually")));
        }

        if (conversation != null) {
            conversation = conclusionsToConvo.get(playerDataMap.get(playerUID).opinion.opinionUID);
            talkAtPlayer(realPlayer, conversation);
            givePlayerResponses(playerData, realPlayer, conversation);
        } else {
            // the author said the player should never talk to this npc again
            realPlayer.sendMessage(ChatColor.RED + "sorry, author said no talking right now");
        }
    }

    /**
     * figure out what convo we need based on the convo
     *
     * @param currentOpinion
     */
    private NpcConvoID doConclusionToConvo(int currentOpinion) {
        return conclusionsToConvo.getOrDefault(currentOpinion, null);
    }


    private void givePlayerResponses(PlayerData playerData, Player realPlayer, NpcConvoID convoId) {
        String playerUID = realPlayer.getUniqueId().toString();
        List<ConversationResponse> responses = AllConversations.get(convoId).responses;
        for (ConversationResponse resp : responses) {
            if (resp.evaluate(playerUID, playerDataMap.get(playerUID).opinion.opinionUID)) {
                for (String textToSay : resp.response) {
                    TextComponent message = new TextComponent(textToSay);
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ping"));
                    realPlayer.spigot().sendMessage(message);
                }
            }
        }
        realPlayer.sendMessage("i sent you stuff");
    }

    private void talkAtPlayer(Player realPlayer, NpcConvoID convoID) {
        ConversationData convo = AllConversations.get(convoID);
        for (String text : convo.conversationText) {
            realPlayer.sendMessage(ChatColor.GREEN + text);
        }
    }

    /**
     * never talked to the player or the time limit was reached
     * so we need to recheck what the npc should say
     *
     * @param playerUID the uid of the player
     * @return the value of the new opinion
     */
    private int doConclusion(String playerUID) {
        int opinion;
        if (playerDataMap.containsKey(playerUID)) {
            opinion = playerDataMap.get(playerUID).opinion.opinionUID;
        } else {
            opinion = startingConclusion;
        }
        for (VarsConclusionMap map : varsToConclusion) {
            if (map.evaluate(playerUID, opinion)) {
                // return the conclusion
                return map.conclusionResult;
            }
        }
        // otherwise do nothing to the conclusion
        return opinion;
    }

}