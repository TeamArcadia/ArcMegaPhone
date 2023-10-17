package hy.pxmegaphone.command;

import hy.pxmegaphone.PXMegaPhone;
import hy.pxmegaphone.message.Message;
import hy.pxmegaphone.message.MessageConfig;
import hy.pxmegaphone.message.MessageKey;
import hy.pxmegaphone.util.ColorCode;
import hy.pxmegaphone.valid.PermissionValidator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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

        if (!(sender instanceof Player)) {
            sender.sendMessage(msgData.getMessage(MessageKey.PLAYER_ONLY));
            return false;
        }

        Player player = (Player) sender;


        if (args.length == 0) {
            player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
            return false;
        }

        FileConfiguration config = PXMegaPhone.getInstance().getConfig();
        ConfigurationSection megaphoneSec = config.getConfigurationSection("megaPhone");


        switch (args[0]) {
            case "리로드", "reload" -> {
                if (!PermissionValidator.hasPermission(player, "reload")) return false;

                plugin.reloadConfig();
                MessageConfig.reload();

                player.sendMessage(msgData.getMessage(MessageKey.RELOAD_CONFIG));
            }
            case "타이틀", "title" -> {
                if (!PermissionValidator.hasPermission(player, "title")) return false;
                if (args.length < 2) {
                    player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                    return false;
                }




                String message = ColorCode.colorCodes(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));

                String sendTitle = ColorCode.colorCodes(megaphoneSec.getString("title.title").replace("{message}", message));
                String sendSubTitle = ColorCode.colorCodes(megaphoneSec.getString("title.subTitle").replace("{message}", message));

                player.sendTitle(sendTitle, sendSubTitle);
            }
            case "채팅", "chat" -> {
                if (!PermissionValidator.hasPermission(player, "chat")) return false;
                if (args.length < 2) {
                    player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                    return false;
                }

                String message = ColorCode.colorCodes(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                String sendChat =  ColorCode.colorCodes(megaphoneSec.getString("chat").replace("{message}", message));

                player.sendMessage(sendChat);
            }

            default -> {
                player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                return false;
            }

        }
        return false;
    }

}
