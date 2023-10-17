package hy.pxmegaphone.message;

import hy.pxmegaphone.PXMegaPhone;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageConfig {

    private static File messageFile;
    public static FileConfiguration messageConfig;


    public static void setup() {

        if (messageFile == null) {
            messageFile = new File(PXMegaPhone.getInstance().getDataFolder(), "message.yml");
        }

        if (!messageFile.exists()) {
            PXMegaPhone.getInstance().saveResource("message.yml", false);
        }

        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
        Message.instance = new Message(MessageConfig.messageConfig);
    }


    public static FileConfiguration getMessageConfig() {
        return YamlConfiguration.loadConfiguration(messageFile);
    }

    public static void reload() {
        try {
            if (messageFile == null || !messageFile.exists()) {
                setup();
            } else {
                messageConfig = YamlConfiguration.loadConfiguration(messageFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}