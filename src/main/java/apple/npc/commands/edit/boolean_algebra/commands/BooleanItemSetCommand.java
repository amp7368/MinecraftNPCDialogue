package apple.npc.commands.edit.boolean_algebra.commands;

import apple.npc.MessageUtils;
import apple.npc.commands.CommandReferences;
import apple.npc.commands.edit.boolean_algebra.BooleanSessionStart;
import apple.npc.commands.edit.boolean_algebra.data.BooleanDataStore;
import apple.npc.data.booleanEditing.forced.BooleanEditForced;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedDouble;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedExpBase;
import apple.npc.data.booleanEditing.forced.BooleanEditForcedItem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BooleanItemSetCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public BooleanItemSetCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand(CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_SET);
        if (command == null) {
            System.err.println(String.format("[NPCDialogue] could not get the %s command", CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_SET));
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            commandSender.sendMessage("nope");
            return false;
        }
        if (args.length != 3) {
            player.sendMessage(MessageUtils.BAD + String.format("An argument length of %d is invalid", args.length));
            return false;
        }
        boolean isNot;
        int trackingTye;
        int isSure;
        try {
            int isNotInt = Integer.parseInt(args[0]);
            isNot = isNotInt == 1;
            trackingTye = Integer.parseInt(args[1]);
            isSure = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtils.BAD + "The first, second, and third arguments must be numeric.");
            return false;
        }
        if (isSure == 0) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.AIR) {
                player.sendMessage(MessageUtils.EDITING + "Please be holding something in your hand when you try again.");

                TextComponent again = new TextComponent();
                again.setText("(Try again)");
                again.setUnderlined(true);
                again.setColor(MessageUtils.EDITING_OPTION);
                again.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %s %d",
                        CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_SET, args[0], args[1], 0)));
                player.spigot().sendMessage(again);
                return false;
            }
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta == null) {
                player.sendMessage(MessageUtils.BAD + "Sorry, but the item your holding somehow doesn't have itemMeta! O.o");
                return false;
            }
            TextComponent sure;
            switch (trackingTye) {
                case 0:
                    sure = new TextComponent();
                    sure.setText(String.format(
                            "Are you sure you want me to check if the player has an item in their inventory that hasthe type of \"%s\"",
                            item.getType().toString()));
                    sure.setColor(ChatColor.BLUE);
                    player.spigot().sendMessage(sure);
                    break;
                case 1:
                    sure = new TextComponent();
                    sure.setText(String.format(
                            "Are you sure you want me to check if the player has an item in their inventory that has the localized name of \"%s\" and the type of \"%s\"",
                            itemMeta.getLocalizedName(), item.getType().toString()));
                    sure.setColor(ChatColor.BLUE);
                    player.spigot().sendMessage(sure);
                    break;
                case 2:
                    sure = new TextComponent();
                    sure.setText(String.format(
                            "Are you sure you want me to check if the player has an item in their inventory that has the custom name of \"%s\" and the type of \"%s\"",
                            itemMeta.getDisplayName(), item.getType().toString()));
                    sure.setColor(ChatColor.BLUE);
                    player.spigot().sendMessage(sure);
                    break;
                case 3:
                    sure = new TextComponent();
                    sure.setText(String.format(
                            "Are you sure you want me to check if the player has an item in their inventory that has the localized name of \"%s\" and the custom name of \"%s\" and the type of \"%s\"",
                            itemMeta.getLocalizedName(), itemMeta.getDisplayName(), item.getType().toString()));
                    sure.setColor(ChatColor.BLUE);
                    player.spigot().sendMessage(sure);
            }
            TextComponent yes = new TextComponent();
            yes.setText("(Yes) - Don't change what item is in your hand!");
            yes.setUnderlined(true);
            yes.setColor(MessageUtils.EDITING_OPTION);
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %s %d",
                    CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM_SET, args[0], args[1], 1)));
            player.spigot().sendMessage(yes);

            player.sendMessage(MessageUtils.DASH);

            TextComponent no = new TextComponent();
            no.setText("(No)");
            no.setUnderlined(true);
            no.setColor(MessageUtils.EDITING_OPTION);
            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s",
                    CommandReferences.NPC_EDIT_VARS_SPECIFIC_ITEM)));
            player.spigot().sendMessage(no);
        } else {
            // make the variable

            BooleanEditForced root = BooleanDataStore.get(player.getUniqueId());
            // VarConcluObject has already been gotten
            BooleanEditForced exp = root.getLeftMost();
            // set exp to this variable comparison
            BooleanEditForced parent = exp.getParent();
            if (parent == null) {
                BooleanDataStore.put(player.getUniqueId(), new BooleanEditForcedItem(isNot, player.getInventory().getItemInMainHand(), trackingTye, null, 0));
            } else {
                // these should be the only possible parents besides no parent
                if (parent instanceof BooleanEditForcedDouble) {
                    ((BooleanEditForcedDouble) parent).set(isNot, player.getInventory().getItemInMainHand(), trackingTye, root.getBiggestName());
                } else if (parent instanceof BooleanEditForcedExpBase) {
                    ((BooleanEditForcedExpBase) parent).set(isNot, player.getInventory().getItemInMainHand(), trackingTye, root.getBiggestName());
                }
            }
            BooleanSessionStart.step(player);

        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}