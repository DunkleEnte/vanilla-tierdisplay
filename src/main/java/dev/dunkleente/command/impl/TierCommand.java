package dev.dunkleente.command.impl;

import dev.dunkleente.command.BukkitCommand;
import dev.dunkleente.mctiers.PlayerTier;
import dev.dunkleente.mctiers.TierWrapper;
import dev.dunkleente.mctiers.cache.TierCache;
import dev.dunkleente.utility.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class TierCommand extends BukkitCommand {

    public TierCommand() {
        super("tier", null, "vanilla");
    }

    @Override
    public void onCommand(@NotNull final CommandSender sender, @NotNull final String label, @NotNull final String[] args) {
        if (!(sender instanceof final Player player)) return;

        if (args.length != 1) {
            player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Wrong Usage! /tier <player>");
            player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Wrong Usage! /tier <player>"));
            player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            return;
        }

        final Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Player not found!");
            player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Player not found!"));
            player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
            return;
        }

        final var cached = TierCache.getPlayer(target.getUniqueId());

        if (cached != null && cached.tier() != PlayerTier.UNRANKED) {
            player.sendActionBar(ColorUtil.parse("<#FFEE00><b>TIER</b> <dark_gray>▶ <#FFEE00>" + target.getName() + "'s <white>Tier is " + cached.tier().asString()));
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        } else {
            TierWrapper.fetch(target.getUniqueId()).thenAccept(tierPlayer -> {
                TierCache.addPlayer(target.getUniqueId(), tierPlayer.tier());

                Bukkit.getScheduler().runTask(JavaPlugin.getProvidingPlugin(getClass()), () -> {
                    player.sendActionBar(ColorUtil.parse("<#FFEE00><b>TIER</b> <dark_gray>▶ <#FFEE00>" + target.getName() + "'s <white>Tier is " + tierPlayer.tier().asString()));
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                });
            });
        }
    }
}