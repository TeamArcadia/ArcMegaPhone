package hy.pxmegaphone.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Consumer;

public class OnChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Consumer<String> callback = ItemClickListener.chatListeners.remove(event.getPlayer().getUniqueId());
        if (callback != null) {
            event.setCancelled(true);
            callback.accept(event.getMessage());
        }
    }
}
