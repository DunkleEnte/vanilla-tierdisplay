package dev.dunkleente.mctiers;

import dev.dunkleente.mctiers.enums.GameMode;
import dev.dunkleente.mctiers.enums.PlayerTier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * TierlistPlayer
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
public record TierlistPlayer(@NotNull UUID uuid, @NotNull Map<GameMode, PlayerTier> tiers){
    public @NotNull PlayerTier getTier(final @NotNull GameMode mode) {
        return tiers.getOrDefault(mode, PlayerTier.UNRANKED);
    }
}