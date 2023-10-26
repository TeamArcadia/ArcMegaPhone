package hy.pxmegaphone.command.tabcomplete;

import hy.pxmegaphone.valid.PermissionValidator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MegaPhoneCmdTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (label.equalsIgnoreCase("mp")) {
                if (PermissionValidator.hasPermission((Player) sender, "reload")) completions.add("reload");
                if (PermissionValidator.hasPermission((Player) sender, "title")) completions.add("title");
                if (PermissionValidator.hasPermission((Player) sender, "chat")) completions.add("chat");
                if (PermissionValidator.hasPermission((Player) sender, "auto")) completions.add("auto");
                if (PermissionValidator.hasPermission((Player) sender, "setitem")) completions.add("setitem");

            } else if (label.equalsIgnoreCase("확성기")) {
                if (PermissionValidator.hasPermission((Player) sender, "reload")) completions.add("리로드");
                if (PermissionValidator.hasPermission((Player) sender, "title")) completions.add("타이틀");
                if (PermissionValidator.hasPermission((Player) sender, "chat")) completions.add("채팅");
                if (PermissionValidator.hasPermission((Player) sender, "auto")) completions.add("자동공지");
                if (PermissionValidator.hasPermission((Player) sender, "setitem")) completions.add("아이템설정");
            }
        } else if (args.length == 2) {
            if ("auto".equalsIgnoreCase(args[0])) {
                completions.add("set");
                completions.add("on");
                completions.add("off");
            } else if ("자동공지".equalsIgnoreCase(args[0])) {
                completions.add("설정");
                completions.add("켜기");
                completions.add("끄기");
            }
        } else if (args.length == 3) { //모드 주기
            if (("auto".equalsIgnoreCase(args[0]) && "set".equalsIgnoreCase(args[1]))||
                    ("자동공지".equalsIgnoreCase(args[0]) && "설정".equalsIgnoreCase(args[1]))) {
                completions.add("chat");
                completions.add("title");
            }
        } else if (args.length == 4) {
            if ("auto".equalsIgnoreCase(args[0]) && "set".equalsIgnoreCase(args[1])) {
                completions.add("<cycle>");
            } else if ("자동공지".equalsIgnoreCase(args[0]) && "설정".equalsIgnoreCase(args[1])) {
                completions.add("<주기>");
            }
        }

        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}