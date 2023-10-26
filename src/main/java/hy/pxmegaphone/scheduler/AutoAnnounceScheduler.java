package hy.pxmegaphone.scheduler;

import hy.pxmegaphone.PXMegaPhone;
import hy.pxmegaphone.util.ColorCode;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class AutoAnnounceScheduler {
    private BukkitTask task;

    public void startAnnounce() {
        if (task != null) {
            task.cancel();
        }

        ConfigurationSection autoAnnounceSec = PXMegaPhone.getInstance().getConfig().getConfigurationSection("autoAnnounce");

        String mode = autoAnnounceSec.getString("mode");
        int cycle = Integer.parseInt(autoAnnounceSec.getString("cycle"));

        task = Bukkit.getScheduler().runTaskTimer(PXMegaPhone.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (mode.equals("CHAT")) {
                    String sendChat = ColorCode.colorCodes(autoAnnounceSec.getString("chat.message"));
                    Bukkit.broadcastMessage(sendChat);

                } else if (mode.equals("TITLE")) {

                    String sendTitle = ColorCode.colorCodes(autoAnnounceSec.getString("title.title"));
                    String sendSubTitle = ColorCode.colorCodes(autoAnnounceSec.getString("title.subTitle"));

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendTitle(sendTitle, sendSubTitle);
                    }

                }
            }
        }, 0L, cycle * 20);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
