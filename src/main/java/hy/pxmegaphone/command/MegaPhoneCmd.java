package hy.pxmegaphone.command;

import hy.pxmegaphone.PXMegaPhone;
import hy.pxmegaphone.message.Message;
import hy.pxmegaphone.message.MessageConfig;
import hy.pxmegaphone.message.MessageKey;
import hy.pxmegaphone.util.ColorCode;
import hy.pxmegaphone.valid.PermissionValidator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MegaPhoneCmd implements CommandExecutor {

    private final JavaPlugin plugin;

    public MegaPhoneCmd(PXMegaPhone plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Message msgData = Message.getInstance();

        FileConfiguration config = PXMegaPhone.getInstance().getConfig();

        if (!(sender instanceof Player)) {
            sender.sendMessage(msgData.getMessage(MessageKey.PLAYER_ONLY));
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
            return false;
        }


        ConfigurationSection megaPhoneSec = config.getConfigurationSection("megaPhone");
        switch (args[0]) {
            case "리로드", "reload" -> {
                if (!PermissionValidator.hasPermission(player, "reload")) return false;

                plugin.reloadConfig();
                MessageConfig.reload();

                player.sendMessage(msgData.getMessage(MessageKey.RELOAD_CONFIG));
            }
            case "아이템설정", "setitem" -> {
                if (!PermissionValidator.hasPermission(player, "setitem")) return false;

                ConfigurationSection megaPhoneItemSec = config.getConfigurationSection("megaPhoneItem");

                ItemStack itemStack = player.getItemInHand().clone();
                itemStack.setAmount(1);

                if (itemStack.getType().equals(Material.AIR)) {
                    player.sendMessage(msgData.getMessage(MessageKey.SET_ITEM_IN_HAND));
                    return false;
                }
                megaPhoneItemSec.set("item", itemStack);
                plugin.saveConfig();

                player.sendMessage(msgData.getMessage(MessageKey.SET_ITEM_SUCCESSFUL));

            }
            case "타이틀", "title" -> {
                if (!PermissionValidator.hasPermission(player, "title")) return false;
                if (args.length < 2) {
                    player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                    return false;
                }
                String message = ColorCode.colorCodes(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                String sendTitle = ColorCode.colorCodes(megaPhoneSec.getString("title.title").replace("{message}", message));
                String sendSubTitle = ColorCode.colorCodes(megaPhoneSec.getString("title.subTitle").replace("{message}", message));

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendTitle(sendTitle, sendSubTitle);
                }
            }
            case "채팅", "chat" -> {
                if (!PermissionValidator.hasPermission(player, "chat")) return false;
                if (args.length < 2) {
                    player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                    return false;
                }
                String message = ColorCode.colorCodes(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                String sendChat =  ColorCode.colorCodes(megaPhoneSec.getString("chat.message").replace("{message}", message));

                Bukkit.broadcastMessage(sendChat);
            }

            case "자동공지", "auto" -> {
                if (!PermissionValidator.hasPermission(player, "auto")) return false;

                ConfigurationSection autoAnnounceSec = config.getConfigurationSection("autoAnnounce");

                // 자동공지 set <모드> <주기>
                if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("설정")) {
                    autoAnnounceSec.set("mode", args[2].toUpperCase());
                    autoAnnounceSec.set("cycle", Integer.parseInt(args[3]));
                    plugin.saveConfig();
                    player.sendMessage(msgData.getMessage(MessageKey.SET_AUTO_SUCCESSFUL));
                } else if (args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("켜기")) {

                    PXMegaPhone.getAutoScheduler().startAnnounce();
                    player.sendMessage(msgData.getMessage(MessageKey.AUTO_ON));

                } else if (args[1].equalsIgnoreCase("off") || args[1].equalsIgnoreCase("끄기")) {

                    PXMegaPhone.getAutoScheduler().stop();
                    player.sendMessage(msgData.getMessage(MessageKey.AUTO_OFF));
                }


            }
            default -> {
                player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                return false;
            }
        }
        return false;
    }
}
