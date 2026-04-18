package dev.dunkleente.subtiers;

import dev.dunkleente.mctiers.enums.PlayerTier;
import dev.dunkleente.subtiers.enums.SubTierMode;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * SubtierlistPlayer
 *
 * @author DunkleEnte
 * @since 18.04.2026
 */
public record SubTierlistPlayer(@NotNull UUID uuid, @NotNull Map<SubTierMode, PlayerTier> tiers) {

    public @NotNull PlayerTier getTier(final @NotNull SubTierMode mode) {
        return tiers.getOrDefault(mode, PlayerTier.UNRANKED);
    }
}
