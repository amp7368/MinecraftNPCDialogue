package apple.npc.data.npc;

import apple.npc.MessageUtils;
import apple.npc.data.all.AllConversations;
import apple.npc.data.all.AllNPCs;
import apple.npc.data.all.AllPlayers;
import apple.npc.data.booleanAlgebra.Evaluateable;
import apple.npc.data.convo.ConversationData;
import apple.npc.data.convo.ConversationResponse;
import apple.npc.data.convo.ConvoID;
import apple.npc.data.player.PlayerData;
import apple.npc.data.player.Variable;
import apple.npc.ymlNavigate.YMLNpcNavigate;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class NPCData {
    private static final String NAME_REGEX = "\\*name\\*";
    public int uid;
    public String name;
    public String gameUID;
    private int startingConclusion;
    private ArrayList<VarsConclusionMap> varsToConclusion;
    private Map<Integer, ConvoID> conclusionsToConvo;
    private Map<String, NPCPlayerData> playerDataMap;
    private long maxTimeSinceTalk;

    private static TextComponent oneLineSplit = new TextComponent();
    private static TextComponent multipleLineSplit = new TextComponent();

    static {
        oneLineSplit.setUnderlined(false);
        oneLineSplit.setText(" | ");
        oneLineSplit.setColor(net.md_5.bungee.api.ChatColor.GRAY);

        multipleLineSplit.setText("\n=====\n");
        multipleLineSplit.setColor(net.md_5.bungee.api.ChatColor.GRAY);
    }


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
     * @param npc
     * @param playerData
     * @param realPlayer
     */
    public void doEntireConversation(NPCData npc, PlayerData playerData, Player realPlayer) {
        String playerUID = realPlayer.getUniqueId().toString();
        int currentOpinion;
        boolean playerLeftEarlier; // false if we want to move on to the next conversation found in defaultPostResponse
        // if we talked to the player before
        if (playerDataMap.containsKey(playerUID)) {
            if (System.currentTimeMillis() - playerDataMap.get(playerUID).lastTalked > maxTimeSinceTalk) {
                // the player waited too long
                currentOpinion = doConclusion(realPlayer);
                playerLeftEarlier = true;
            } else {
                currentOpinion = playerDataMap.get(playerUID).opinion.opinionUID;
                playerLeftEarlier = false; // we want to go to the next conversation (from defaultPostResponse)
            }
        } else {
            // check what we should say
            playerLeftEarlier = true; // this is made true to stay at this conversation
            currentOpinion = doConclusion(realPlayer);
        }
        // if we want to go to the next conversation in sequence regardless of opinion
        ConvoID conversation;
        if (!playerLeftEarlier && playerDataMap.containsKey(playerUID)) {
            conversation = AllConversations.get(playerDataMap.get(playerUID).currentConvoUID).immediateConvo.toNpcConvoID();
        } else {
            conversation = doConclusionToConvo(currentOpinion);
        }
        doConversation(npc, playerUID, conversation, currentOpinion, realPlayer, playerData);
    }

    private void doConversation(NPCData npc, String playerUID, ConvoID conversation, int currentOpinion, Player realPlayer, PlayerData playerData) {
        AllNPCs.setPlayerData(this.uid, playerUID, conversation, currentOpinion, "name will be made eventually");


        if (conversation != null) {
            talkAtPlayer(npc, realPlayer, conversation);
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
        boolean isMultipleLines = false;
        for (ConversationResponse resp : responses) {
            if (resp.response.size() == 1) {
                if (resp.response.get(0).length() > 20) {
                    isMultipleLines = true;
                }
            } else {
                isMultipleLines = true;
            }
        }
        List<List<TextComponent>> messages = new ArrayList<>();
        for (ConversationResponse resp : responses) {
            int opinion;
            if (playerDataMap.containsKey(playerUID))
                opinion = playerDataMap.get(playerUID).opinion.opinionUID;
            else
                opinion = startingConclusion;
            List<TextComponent> messagesThisResponse = new ArrayList<>();
            if (resp.evaluate(realPlayer, opinion, timeLastTalked)) {
                for (String textToSay : resp.response) {
                    TextComponent message = new TextComponent(textToSay);
                    message.setUnderlined(true);
                    message.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/npc_respond %d %d", uid, resp.uid)));
                    messagesThisResponse.add(message);
                }
                messages.add(messagesThisResponse);
            }
        }
        if (isMultipleLines) {
            if (!messages.isEmpty())
                realPlayer.sendMessage("");
            // do it on multiple lines
            for (int i = 0; i < messages.size(); i++) {
                if (i != 0) {
                    realPlayer.spigot().sendMessage(multipleLineSplit);
                }
                for (TextComponent message : messages.get(i)) {
                    realPlayer.spigot().sendMessage(message);
                }
            }
            if (!messages.isEmpty())
                realPlayer.sendMessage("");
        } else {
            List<TextComponent> condensedMessages = new ArrayList<>();
            for (List<TextComponent> messagesThisResponse : messages) {
                if (!messagesThisResponse.isEmpty()) {
                    condensedMessages.add(messagesThisResponse.get(0));
                }
            }
            if (condensedMessages.isEmpty()) {
                return null;
            }
            // condense the messages into one TextComponent
            ComponentBuilder condensedMessage = new ComponentBuilder(condensedMessages.get(0));
            for (int i = 1; i < condensedMessages.size(); i++) {
                condensedMessage.append(oneLineSplit);
                condensedMessage.append(condensedMessages.get(i));
            }
            realPlayer.sendMessage("");
            realPlayer.spigot().sendMessage(condensedMessage.create());
            realPlayer.sendMessage("");
        }
        return null;
    }

    private void talkAtPlayer(NPCData npc, Player realPlayer, ConvoID convoID) {
        ConversationData convo = AllConversations.get(convoID);
        if (convo == null) {
            // just ignore fails. nothing will happen
            return;
        }
        for (String text : convo.conversationText) {
            text = ChatColor.YELLOW + npc.name + ChatColor.GOLD + "> " + ChatColor.GREEN + text.replaceAll(NAME_REGEX, realPlayer.getDisplayName());
            realPlayer.sendMessage(text);
        }
    }

    /**
     * never talked to the player or the time limit was reached
     * so we need to recheck what the npc should say
     *
     * @return the value of the new opinion
     */
    private int doConclusion(Player player) {
        String playerUID = player.getUniqueId().toString();
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
            if (map.evaluate(player, opinion, timeLastTalked)) {
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
                if (!response.evaluate(player, npcPlayerData.opinion.opinionUID, npcPlayerData.lastTalked)) {
                    player.sendMessage("Good try, but you don't have the prerequisites to do this response");
                    return;
                }
                ConvoID redirect = response.doGetPostResponse(this, npcPlayerData.opinion, npcPlayerData.lastTalked, player);
                doConversation(this, playerUID, redirect, npcPlayerData.opinion.opinionUID, player, AllPlayers.getPlayer(playerUID));
            }


        }
    }

    public void setConcluToConvo(int concluNum, String global, int local, int convo) {
        conclusionsToConvo.put(concluNum, new ConvoID(global, local, convo));
    }

    public void setPlayerData(String playerUID, ConvoID conversation, int currentOpinion, String opinionName) {
        playerDataMap.put(playerUID, new NPCPlayerData(playerUID, conversation, System.currentTimeMillis(), new Opinion(currentOpinion, opinionName)));
    }

    public void setConclusionVar(String uid, Variable variable) {
        if (playerDataMap.containsKey(uid)) {
            if (variable.name.equals("conclusion")) {
                playerDataMap.get(uid).opinion = new Opinion(variable.value, "This will be implemented eventually");
            } else if (variable.name.equals("time")) {
                playerDataMap.get(uid).lastTalked = variable.value;
            }
        }
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

    public void setName(String name) {
        AllNPCs.deleteFile(this);
        this.name = name;
        Entity entity = Bukkit.getEntity(UUID.fromString(gameUID));
        if (entity != null)
            entity.setCustomName(name);
    }

    public Collection<Integer> getConclusionList() {
        return conclusionsToConvo.keySet();
    }

    public void setVarToConclu(int conclusionResult, Evaluateable finished) {
        for (VarsConclusionMap varMap : varsToConclusion) {
            if (conclusionResult == varMap.conclusionResult) {
                varMap.setExpression(finished);
                return;
            }
        }
        varsToConclusion.add(new VarsConclusionMap(conclusionResult, finished));
    }

}