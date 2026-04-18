package dev.dunkleente.utility.placeholder;

import dev.dunkleente.mctiers.enums.GameMode;
import dev.dunkleente.mctiers.enums.PlayerTier;
import dev.dunkleente.mctiers.cache.TierCache;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * PlaceholderHook
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
public class PlaceholderHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "vanillaTiers";
    }

    @Override
    public @NotNull String getAuthor() {
        return "DunkleEnte";

    }
    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(final @NotNull Player player, final @NotNull String params) {

        for (final GameMode mode : GameMode.values()) {
            if (params.equalsIgnoreCase("tier_" + mode.getApiKey())) {
                final var tierlistPlayer = TierCache.getPlayer(player.getUniqueId());
                if (tierlistPlayer == null) return mode.getIcon() + " " + PlayerTier.UNRANKED.asString();

                final PlayerTier tier = tierlistPlayer.getTier(mode);
                return mode.getIcon() + " " + tier.asString();
            }
        }

        return null;
    }
}
