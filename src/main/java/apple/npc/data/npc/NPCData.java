package apple.npc.data.npc;

import apple.npc.creation.npc.components.CreateNpcPlayerData;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
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
        boolean playerLeftEarlier; // false if we want to move on to the next conversation found in defaultPostResponse
        // if we talked to the player before
        if (playerDataMap.containsKey(playerUID)) {
            if (System.currentTimeMillis() - playerDataMap.get(playerUID).lastTalked > maxTimeSinceTalk) {
                // the player waited too long
                currentOpinion = doConclusion(playerUID);
                playerLeftEarlier = true;
            } else {
                currentOpinion = playerDataMap.get(playerUID).opinion.opinionUID;
                playerLeftEarlier = false; // we want to go to the next conversation (from defaultPostResponse)
            }
        } else {
            // check what we should say
            playerLeftEarlier = true; // this is made true to stay at this conversation
            currentOpinion = doConclusion(playerUID);
        }
        // if we want to go to the next conversation in sequence regardless of opinion
        NpcConvoID conversation;
        if (!playerLeftEarlier && playerDataMap.containsKey(playerUID)) {
            System.out.println("Changing conversation");
            System.out.println(playerDataMap.get(playerUID).currentConvoUID.global);
            System.out.println(playerDataMap.get(playerUID).currentConvoUID.local);
            System.out.println(playerDataMap.get(playerUID).currentConvoUID.uid);
            conversation = AllConversations.get(playerDataMap.get(playerUID).currentConvoUID).immediateConvo.toNpcConvoID();
        } else {
            conversation = doConclusionToConvo(currentOpinion);
        }
        AllNPCs.setPlayerData(this.uid, this.name, playerUID, conversation, currentOpinion, "name will be made eventually");


        if (conversation != null) {
            talkAtPlayer(realPlayer, conversation);
            if (!playerDataMap.containsKey(playerUID)) {
                NPCPlayerData npcPlayerData = playerDataMap.get(playerUID);
                givePlayerResponses(playerData, realPlayer, conversation, npcPlayerData.lastTalked);
            } else {
                givePlayerResponses(playerData, realPlayer, conversation, -1);
            }
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


    private ConversationResponse givePlayerResponses(PlayerData playerData, Player realPlayer, NpcConvoID convoId, long timeLastTalked) {
        String playerUID = realPlayer.getUniqueId().toString();
        List<ConversationResponse> responses = AllConversations.get(convoId).responses;
        for (ConversationResponse resp : responses) {
            if (resp.evaluate(playerUID, playerDataMap.get(playerUID).opinion.opinionUID, timeLastTalked)) {
                for (String textToSay : resp.response) {
                    TextComponent message = new TextComponent(textToSay);
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ping"));
                    realPlayer.spigot().sendMessage(message);
                }
            }
        }
        return null;
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
        long timeLastTalked;
        if (playerDataMap.containsKey(playerUID)) {
            NPCPlayerData npcPlayerData = playerDataMap.get(playerUID);
            opinion = npcPlayerData.opinion.opinionUID;
            timeLastTalked = npcPlayerData.lastTalked;
        } else {
            opinion = startingConclusion;
            timeLastTalked = -1;
        }
        for (VarsConclusionMap map : varsToConclusion) {
            if (map.evaluate(playerUID, opinion, timeLastTalked)) {
                // return the conclusion
                return map.conclusionResult;
            }
        }
        // otherwise do nothing to the conclusion
        return opinion;
    }

}