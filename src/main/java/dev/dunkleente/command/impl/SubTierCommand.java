package dev.dunkleente.command.impl;

import dev.dunkleente.Economy;
import dev.dunkleente.command.BukkitCommand;
import dev.dunkleente.mctiers.TierWrapper;
import dev.dunkleente.mctiers.cache.TierCache;
import dev.dunkleente.mctiers.enums.GameMode;
import dev.dunkleente.subtiers.SubTierWrapper;
import dev.dunkleente.subtiers.cache.SubTierCache;
import dev.dunkleente.subtiers.enums.SubTierMode;
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
public final class SubTierCommand extends BukkitCommand {

    public SubTierCommand() {
        super("subtier", null, "sub-tiers");
    }

    @Override
    public void onCommand(@NotNull final CommandSender sender, @NotNull final String label, @NotNull final String[] args) {
        if (!(sender instanceof final Player player)) return;

        if (args.length == 0) {
            player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Wrong Usage! /subtier <player> [mode]");
            player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Wrong Usage! /subtier <player> [mode]"));
            player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            return;
        }


        final SubTierMode mode;

        if (args.length == 2) {
            mode = SubTierMode.fromString(args[1]);
            if (mode == null) {
                player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Unknown Gamemode!");
                player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Unknown Gamemode!"));

                player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
                return;
            }
        } else {
            mode = SubTierMode.DIA_CRYSTAL;
        }
        final Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Player not found!");
            player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Player not found!"));
            player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            return;
        }

        final var cached = SubTierCache.getPlayer(target.getUniqueId());

        if (cached != null) {
            player.sendActionBar(ColorUtil.parse("<#00C1FF><b>SUB-TIER</b> <dark_gray>▶ <#FFEE00>" + target.getName() + "'s <white>Sub-Tier is " + mode.getIcon() + " " + cached.getTier(mode).asString()));
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        } else {
            SubTierWrapper.fetch(target.getUniqueId()).thenAccept(tierPlayer -> {
                SubTierCache.addPlayer(tierPlayer);

                Bukkit.getScheduler().runTask(Economy.getInstance(), () -> {
                    player.sendActionBar(ColorUtil.parse("<#00C1FF><b>SUB-TIER</b> <dark_gray>▶ <#FFEE00>" + target.getName() + "'s <white>Sub-Tier is " + mode.getIcon() + " " + tierPlayer.getTier(mode).asString()));
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
            return Arrays.stream(SubTierMode.values())
                    .map(SubTierMode::getApiKey)
                    .filter(key -> key.toLowerCase().startsWith(args[1].toLowerCase()))
                    .toList();
        }

        return List.of();
    }
}