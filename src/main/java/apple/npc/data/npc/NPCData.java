package apple.npc.data.npc;

import apple.npc.ColorScheme;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.all.AllPlayers;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
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
    private Map<Integer, ConvoID> conclusionsToConvo;
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
        System.out.println("Current path: " + config.getCurrentPath());
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

    private Map<Integer, ConvoID> mapConclusionsToConvo(ConfigurationSection config) {
        Map<Integer, ConvoID> map = new HashMap<>();
        Set<String> conclusions = config.getKeys(false);
        for (String conclusion : conclusions) {
            int conclusionNum;
            try {
                conclusionNum = Integer.parseInt(conclusion);
            } catch (NumberFormatException e) {
                System.err.println("there was a conclusion that was not a number");
                continue;
            }
            map.put(conclusionNum, new ConvoID(config.getConfigurationSection(conclusion)));
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
    public void doEntireConversation(PlayerData playerData, Player realPlayer) {
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
        ConvoID conversation;
        if (!playerLeftEarlier && playerDataMap.containsKey(playerUID)) {
            conversation = AllConversations.get(playerDataMap.get(playerUID).currentConvoUID).immediateConvo.toNpcConvoID();
        } else {
            conversation = doConclusionToConvo(currentOpinion);
        }
        doConversation(playerUID, conversation, currentOpinion, realPlayer, playerData);
    }

    private void doConversation(String playerUID, ConvoID conversation, int currentOpinion, Player realPlayer, PlayerData playerData) {
        AllNPCs.setPlayerData(this.uid, playerUID, conversation, currentOpinion, "name will be made eventually");


        if (conversation != null) {
            talkAtPlayer(realPlayer, conversation);
            if (playerDataMap.containsKey(playerUID)) {
                NPCPlayerData npcPlayerData = playerDataMap.get(playerUID);
                givePlayerResponses(realPlayer, conversation, npcPlayerData.lastTalked);
            } else {
                givePlayerResponses(realPlayer, conversation, -1);
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
    private ConvoID doConclusionToConvo(int currentOpinion) {
        return conclusionsToConvo.getOrDefault(currentOpinion, null);
    }


    private ConversationResponse givePlayerResponses(Player realPlayer, ConvoID convoId, long timeLastTalked) {
        String playerUID = realPlayer.getUniqueId().toString();
        List<ConversationResponse> responses = AllConversations.get(convoId).responses;
        for (ConversationResponse resp : responses) {
            int opinion;
            if (playerDataMap.containsKey(playerUID))
                opinion = playerDataMap.get(playerUID).opinion.opinionUID;
            else
                opinion = startingConclusion;
            if (resp.evaluate(playerUID, opinion, timeLastTalked)) {
                for (String textToSay : resp.response) {
                    TextComponent message = new TextComponent(textToSay);
                    message.setUnderlined(true);
                    message.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/npc_respond %d %d", uid, resp.uid)));
                    realPlayer.spigot().sendMessage(message);
                    realPlayer.sendMessage(ColorScheme.DASH);
                }
            }
        }
        realPlayer.sendMessage("");
        return null;
    }

    private void talkAtPlayer(Player realPlayer, ConvoID convoID) {
        realPlayer.sendMessage(ColorScheme.DASH);
        ConversationData convo = AllConversations.get(convoID);
        for (String text : convo.conversationText) {
            realPlayer.sendMessage(ChatColor.GREEN + text);
        }
        realPlayer.sendMessage(ColorScheme.DASH);

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

    public void respond(Player player, int responseUid) {
        String playerUID = player.getUniqueId().toString();
        if (playerDataMap.containsKey(playerUID)) {
            NPCPlayerData npcPlayerData = playerDataMap.get(playerUID);
            ConversationData currentConversation = AllConversations.get(npcPlayerData.currentConvoUID);
            if (currentConversation == null) {
                player.sendMessage("Sorry to ruin immersion, but something went wrong with the retrieving of this response..");
                return;
            }
            if (currentConversation.contains(responseUid)) {
                ConversationResponse response = currentConversation.get(responseUid);
                if (response == null) {
                    player.sendMessage("Sorry to ruin immersion, but something went wrong with the retrieving of this response");
                    return;
                }
                if (!response.evaluate(playerUID, npcPlayerData.opinion.opinionUID, npcPlayerData.lastTalked)) {
                    player.sendMessage("Good try, but you don't have the prerequisites to do this response");
                    return;
                }
                ConvoID redirect = response.getPostResponse(npcPlayerData.opinion, npcPlayerData.lastTalked, playerUID);
                doConversation(playerUID, redirect, npcPlayerData.opinion.opinionUID, player, AllPlayers.getPlayer(playerUID));
            }


        }
    }

    public void setConcluToConvo(int concluNum, String global, int local, int convo) {
        conclusionsToConvo.put(concluNum, new ConvoID(global, local, convo));
    }

    public void setPlayerData(String playerUID, ConvoID conversation, int currentOpinion, String opinionName) {
        playerDataMap.put(playerUID, new NPCPlayerData(playerUID, conversation, System.currentTimeMillis(), new Opinion(currentOpinion, opinionName)));
    }

    public int getStartingConclusion() {
        return startingConclusion;
    }

    public ArrayList<VarsConclusionMap> getVarsToConclusion() {
        return varsToConclusion;
    }

    public Map<Integer, ConvoID> getConclusionsToConvo() {
        return conclusionsToConvo;
    }

    public Map<String, NPCPlayerData> getPlayerDataMap() {
        return playerDataMap;
    }

    public long getMaxTimeSinceTalk() {
        return maxTimeSinceTalk;
    }

}