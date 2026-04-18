package dev.dunkleente.command.impl;

import dev.dunkleente.Economy;
import dev.dunkleente.command.BukkitCommand;
import dev.dunkleente.mctiers.TierWrapper;
import dev.dunkleente.mctiers.cache.TierCache;
import dev.dunkleente.mctiers.enums.GameMode;
import dev.dunkleente.utility.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * TierCommand
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
public final class TierCommand extends BukkitCommand {

    public TierCommand() {
        super("tier", null, "vanilla");
    }

    @Override
    public void onCommand(@NotNull final CommandSender sender, @NotNull final String label, @NotNull final String[] args) {
        if (!(sender instanceof final Player player)) return;

        if (args.length == 0) {
            player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Wrong Usage! /tier <player>");
            player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Wrong Usage! /tier <player>"));
            player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            return;
        }


        final GameMode mode;

        if (args.length == 2) {
            mode = GameMode.fromString(args[1]);
            if (mode == null) {
                player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Unknown GameMode!");
                player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Unknown GameMode!"));

                player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
                return;
            }
        } else {
            mode = GameMode.VANILLA;
        }
        final Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Player not found!");
            player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Player not found!"));
            player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            return;
        }

        final var cached = TierCache.getPlayer(target.getUniqueId());

        if (cached != null) {
            player.sendActionBar(ColorUtil.parse("<#FFEE00><b>TIER</b> <dark_gray>▶ <#FFEE00>" + target.getName() + "'s <white>" + mode.getDisplayName() + " Tier is " + cached.getTier(mode).asString()));
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        } else {
            TierWrapper.fetch(target.getUniqueId()).thenAccept(tierPlayer -> {
                TierCache.addPlayer(tierPlayer);

                Bukkit.getScheduler().runTask(Economy.getInstance(), () -> {
                    player.sendActionBar(ColorUtil.parse("<#FFEE00><b>TIER</b> <dark_gray>▶ <#FFEE00>" + target.getName() + "'s <white>Tier is " + tierPlayer.getTier(mode).asString()));
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                });
            });
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }

        if (args.length == 2) {
            return Arrays.stream(GameMode.values())
                    .map(GameMode::getApiKey)
                    .filter(key -> key.toLowerCase().startsWith(args[1].toLowerCase()))
                    .toList();
        }

        return List.of();
    }
}