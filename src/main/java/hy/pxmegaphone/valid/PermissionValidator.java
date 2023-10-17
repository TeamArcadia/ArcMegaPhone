package hy.pxmegaphone.valid;

import hy.pxmegaphone.message.Message;
import hy.pxmegaphone.message.MessageKey;
import org.bukkit.entity.Player;

public class PermissionValidator {

    public static Boolean hasPermission(Player player, String permission) {
        Message msgData = Message.getInstance();
         if (player.hasPermission("px.megaphone." + permission)) {
            return true;
         } else {
             player.sendMessage(msgData.getMessage(MessageKey.NO_PERMISSION));
             return false;
         }
    }
}