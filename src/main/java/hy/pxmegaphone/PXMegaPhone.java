package hy.pxmegaphone;

import hy.pxmegaphone.command.MegaPhoneCmd;
import hy.pxmegaphone.message.MessageConfig;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PXMegaPhone extends JavaPlugin {

    private static PXMegaPhone instance;

    @Override
    public void onEnable() {
        instance = this;
        /*--------------- CONFIG ---------------*/
        saveDefaultConfig();
        MessageConfig.setup();

        /* --------------- COMMAND ---------------*/
        PluginCommand mainCommand = getCommand("mg");
        Objects.requireNonNull(mainCommand).setExecutor(new MegaPhoneCmd(this));
      //  mainCommand.setTabCompleter(new ReturnTicketTab());

        /* --------------- LISTENER ---------------*/

    }

    public static PXMegaPhone getInstance() {
        return instance;
    }
}
