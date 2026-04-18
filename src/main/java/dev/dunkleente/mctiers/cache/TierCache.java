package dev.dunkleente.mctiers.cache;

import dev.dunkleente.mctiers.PlayerTier;
import dev.dunkleente.mctiers.TierlistPlayer;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class TierCache {

    private final Map<UUID, PlayerTier> tiers = new ConcurrentHashMap<>();

    public void addPlayer(final @NotNull UUID uuid, final @NotNull PlayerTier tier) {
        tiers.put(uuid, tier);
    }

    public void removePlayer(final @NotNull UUID uuid) {
        tiers.remove(uuid);
    }

    public @Nullable TierlistPlayer getPlayer(final @NotNull UUID uuid) {
        if (!tiers.containsKey(uuid)) {
            return null;
        }
        return new TierlistPlayer(uuid, tiers.get(uuid));
    }
}