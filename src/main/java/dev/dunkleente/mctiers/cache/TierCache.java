package dev.dunkleente.mctiers.cache;

import dev.dunkleente.mctiers.TierlistPlayer;
import dev.dunkleente.mctiers.enums.GameMode;
import dev.dunkleente.mctiers.enums.PlayerTier;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TierCache
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
@UtilityClass
public class TierCache {

    private final Map<UUID, Map<GameMode, PlayerTier>> tiers = new ConcurrentHashMap<>();

    public void addPlayer(final @NotNull TierlistPlayer player) {
        tiers.put(player.uuid(), new EnumMap<>(player.tiers()));
    }

    public void removePlayer(final @NotNull UUID uuid) {
        tiers.remove(uuid);
    }

    public @Nullable TierlistPlayer getPlayer(final @NotNull UUID uuid) {
        return Optional.ofNullable(tiers.get(uuid))
                .map(map -> new TierlistPlayer(uuid, map))
                .orElse(null);
    }

    public boolean isCached(final @NotNull UUID uuid) {
        return tiers.containsKey(uuid);
    }
}