package dev.dunkleente.subtiers.cache;

import dev.dunkleente.mctiers.enums.PlayerTier;
import dev.dunkleente.subtiers.SubTierlistPlayer;
import dev.dunkleente.subtiers.enums.SubTierMode;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SubTierCache
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
@UtilityClass
public class SubTierCache {

    private final Map<UUID, Map<SubTierMode, PlayerTier>> tiers = new ConcurrentHashMap<>();

    public void addPlayer(final @NotNull SubTierlistPlayer player) {
        tiers.put(player.uuid(), new EnumMap<>(player.tiers()));
    }

    public void removePlayer(final @NotNull UUID uuid) {
        tiers.remove(uuid);
    }

    public @Nullable SubTierlistPlayer getPlayer(final @NotNull UUID uuid) {
        return Optional.ofNullable(tiers.get(uuid))
                .map(map -> new SubTierlistPlayer(uuid, map))
                .orElse(null);
    }

    public boolean isCached(final @NotNull UUID uuid) {
        return tiers.containsKey(uuid);
    }
}