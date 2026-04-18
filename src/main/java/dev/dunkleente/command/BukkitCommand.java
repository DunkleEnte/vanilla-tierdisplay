package dev.dunkleente.command;

import dev.dunkleente.utility.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * BukkitCommand
 *
 * @author DunkleEnte
 * @since 17.04.2026
 * Credits JavaMio :3
 */
public abstract class BukkitCommand extends Command {

    private final String permission;

    public BukkitCommand(String name, String permission, String... aliases) {
        super(name, name + " command", "/" + name, aliases[0].isEmpty() ? Collections.singletonList(name) : Stream.concat(Stream.of(name), Arrays.stream(aliases)).toList());
        this.permission = permission;
        registerCommand(this);
    }

    public BukkitCommand(String name, String permission) {
        this(name, permission, "");
    }

    public abstract void onCommand(CommandSender sender, String label, String[] args);

    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return super.tabComplete(sender, label, args);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        if (permission == null || sender.hasPermission(permission)) {
            onCommand(sender, label, args);
        } else {
            sender.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>You don't have permission for this!");

            if(sender instanceof final Player player) {
                player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>You don't have permission for this!"));

                player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            }
        }
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) throws IllegalArgumentException {
        return onTabComplete(sender, alias, args);
    }

    private void registerCommand(Command command) {
        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("vanilla-tiers", command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Cannot register command", e);
        }
    }

}