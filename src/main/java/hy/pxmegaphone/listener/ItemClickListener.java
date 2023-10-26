package hy.pxmegaphone.listener;

import hy.pxmegaphone.PXMegaPhone;
import hy.pxmegaphone.message.Message;
import hy.pxmegaphone.message.MessageKey;
import hy.pxmegaphone.util.ColorCode;
import hy.pxmegaphone.valid.PermissionValidator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemClickListener implements Listener {

    FileConfiguration config = PXMegaPhone.getInstance().getConfig();
    static Map<UUID, Consumer<String>> chatListeners = new HashMap<>();
    Message msgData = Message.getInstance();

    @EventHandler
    public void itemClickEvent(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;

        ConfigurationSection megaPhoneItemSec = config.getConfigurationSection("megaPhoneItem");

        ItemStack megaPhoneItem = megaPhoneItemSec.getItemStack("item");

        if (itemStack.isSimilar(megaPhoneItem)) {
            if (!PermissionValidator.hasPermission(player, "event.useitem")) return;

            chatListeners.put(player.getUniqueId(), message -> {
                    String sendMessage = ColorCode.colorCodes(megaPhoneItemSec.getString("chat.message")
                            .replace("{message}", message)
                            .replace("{player}", player.getName()));
                    Bukkit.broadcastMessage(sendMessage);

            });
            player.sendMessage(msgData.getMessage(MessageKey.SET_MESSAGE_ON_CHAT));
            itemStack.setAmount(itemStack.getAmount() - 1);

        }
    }
}
