package dev.dunkleente.menu;

import dev.dunkleente.mctiers.TierlistPlayer;
import dev.dunkleente.mctiers.cache.TierCache;
import dev.dunkleente.mctiers.enums.GameMode;
import dev.dunkleente.mctiers.enums.PlayerTier;
import dev.dunkleente.utility.inventory.InventoryBuilder;
import dev.dunkleente.utility.inventory.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

/**
 * TierStatsMenu
 *
 * @author DunkleEnte
 * @since 18.04.2026
 */
public class TierStatsMenu extends InventoryBuilder {

    public TierStatsMenu(final @NotNull Player player, final @NotNull Player target) {
        super(36, Component.text(target.getName() + "'s Tier Stats"), true);

        final TierlistPlayer tierlistPlayer = TierCache.getPlayer(target.getUniqueId());

        setItem(4, ItemBuilder.item(Material.PLAYER_HEAD)
                .name("<#00C1FF>" + target.getName())
                .skullOwner(target)
                .build(), event -> event.setCancelled(true)
        );

        setItem(10, ItemBuilder.item(Material.END_CRYSTAL)
                .name("<#FF7CCC>♦ <b>VANILLA</b> <dark_gray>▶ " + (tierlistPlayer != null ? tierlistPlayer.getTier(GameMode.VANILLA).asString() : PlayerTier.UNRANKED.asString()))
                .build(), event -> event.setCancelled(true)
        );

        setItem(11, ItemBuilder.item(Material.GOLDEN_APPLE)
                .name("<#FFEE00>⛨ <b>UHC</b> <dark_gray>▶ " + (tierlistPlayer != null ? tierlistPlayer.getTier(GameMode.UHC).asString() : PlayerTier.UNRANKED.asString()))
                .build(), event -> event.setCancelled(true)
        );

        setItem(12, ItemBuilder.item(Material.POTION)
                .potionType(PotionType.HEALING)
                .name("<#FF0000>\uD83E\uDDEA <b>POT</b> <dark_gray>▶ " + (tierlistPlayer != null ? tierlistPlayer.getTier(GameMode.POT).asString() : PlayerTier.UNRANKED.asString()))
                .build(), event -> event.setCancelled(true)
        );

        setItem(13, ItemBuilder.item(Material.NETHERITE_HELMET)
                .name("<#C285FF>\uD83C\uDFF9 <b>NETHOP</b> <dark_gray>▶ " + (tierlistPlayer != null ? tierlistPlayer.getTier(GameMode.NETHOP).asString() : PlayerTier.UNRANKED.asString()))
                .hideAttributes()
                .build(), event -> event.setCancelled(true)
        );

        setItem(14, ItemBuilder.item(Material.ENDER_PEARL)
                .name("<#FF7200>⚑ <b>SMP</b> <dark_gray>▶ " + (tierlistPlayer != null ? tierlistPlayer.getTier(GameMode.SMP).asString() : PlayerTier.UNRANKED.asString()))
                .build(), event -> event.setCancelled(true)
        );

        setItem(15, ItemBuilder.item(Material.DIAMOND_SWORD)
                .name("<#00C1FF>\uD83D\uDDE1 <b>SWORD</b> <dark_gray>▶ " + (tierlistPlayer != null ? tierlistPlayer.getTier(GameMode.SWORD).asString() : PlayerTier.UNRANKED.asString()))
                .hideAttributes()
                .build(), event -> event.setCancelled(true)
        );

        setItem(16, ItemBuilder.item(Material.DIAMOND_AXE)
                .name("<#8DDAFF>\uD83E\uDE93 <b>AXE</b> <dark_gray>▶ " + (tierlistPlayer != null ? tierlistPlayer.getTier(GameMode.AXE).asString() : PlayerTier.UNRANKED.asString()))
                .hideAttributes()
                .build(), event -> event.setCancelled(true)
        );

        setItem(19, ItemBuilder.item(Material.MACE)
                .name("<#BFADFF>\uD83D\uDEE1 <b>MACE</b> <dark_gray>▶ " + (tierlistPlayer != null ? tierlistPlayer.getTier(GameMode.MACE).asString() : PlayerTier.UNRANKED.asString()))
                .hideAttributes()
                .build(), event -> event.setCancelled(true)
        );

        open(player);
    }
}
