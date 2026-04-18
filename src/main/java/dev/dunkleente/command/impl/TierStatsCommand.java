package dev.dunkleente.command.impl;

import dev.dunkleente.command.BukkitCommand;
import dev.dunkleente.menu.TierStatsMenu;
import dev.dunkleente.utility.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * TierStatsCommand
 *
 * @author DunkleEnte
 * @since 18.04.2026
 */
public class TierStatsCommand extends BukkitCommand {

    public TierStatsCommand() {
        super("tier-stats", null);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof final Player player)) return;

        if(args.length == 0) {
            new TierStatsMenu(player, player);
        } else if (args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                player.sendRichMessage("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Player not found!");
                player.sendActionBar(ColorUtil.parse("<#FF0000><b>ERROR</b> <dark_gray>▶ <white>Player not found!"));
                player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 1, 1);
                return;
            }

            new TierStatsMenu(player, target);
        }
    }
}
