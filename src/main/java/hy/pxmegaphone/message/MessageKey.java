package hy.pxmegaphone.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageKey {
    /* --------------- NORMAL ---------------*/
    PREFIX("normal.prefix"),
    RELOAD_CONFIG("normal.reload_config"),

    /* --------------- ERROR ---------------*/
    PLAYER_ONLY("error.player_only"),
    NO_PERMISSION("error.no_permission"),
    WRONG_COMMAND("error.wrong_command"),

    /* --------------- MAIN ---------------*/
    SET_ITEM_IN_HAND("main.set_item_in_hand"),
    SET_ITEM_SUCCESSFUL("main.set_item_successful"),
    SET_MESSAGE_ON_CHAT("main.set_message_on_chat"),
    SET_AUTO_SUCCESSFUL("main.set_auto_successful"),
    AUTO_ON("main.auto_on"),
    AUTO_OFF("main.auto_off");




    private final String key;

}