package hy.pxmegaphone;

import hy.pxmegaphone.command.MegaPhoneCmd;
import hy.pxmegaphone.command.tabcomplete.MegaPhoneCmdTab;
import hy.pxmegaphone.listener.ItemClickListener;
import hy.pxmegaphone.listener.OnChatListener;
import hy.pxmegaphone.message.MessageConfig;
import hy.pxmegaphone.scheduler.AutoAnnounceScheduler;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PXMegaPhone extends JavaPlugin {

    private static PXMegaPhone instance;
    private static AutoAnnounceScheduler autoAnnounceScheduler;

    @Override
    public void onEnable() {
        instance = this;
        /*--------------- CONFIG ---------------*/
        saveDefaultConfig();
        MessageConfig.setup();

        /* --------------- COMMAND ---------------*/
        PluginCommand mainCommand = getCommand("mp");
        Objects.requireNonNull(mainCommand).setExecutor(new MegaPhoneCmd(this));
        mainCommand.setTabCompleter(new MegaPhoneCmdTab());

        /* --------------- LISTENER ---------------*/
        getServer().getPluginManager().registerEvents(new ItemClickListener(), this);
        getServer().getPluginManager().registerEvents(new OnChatListener(), this);

        /* --------------- SCHEDULER ---------------*/
        autoAnnounceScheduler = new AutoAnnounceScheduler();

    }

    @Override
    public void onDisable() {
        autoAnnounceScheduler.stop();
    }

    public static PXMegaPhone getInstance() {
        return instance;
    }

    public static AutoAnnounceScheduler getAutoScheduler() {
        return autoAnnounceScheduler;
    }
}
