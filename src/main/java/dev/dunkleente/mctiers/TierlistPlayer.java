package dev.dunkleente.mctiers;

import java.util.UUID;

/**
 * TierlistPlayer
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
public record TierlistPlayer(UUID uuid, PlayerTier tier) { }
