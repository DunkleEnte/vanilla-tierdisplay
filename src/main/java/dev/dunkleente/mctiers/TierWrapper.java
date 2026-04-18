package dev.dunkleente.mctiers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.dunkleente.mctiers.enums.GameMode;
import dev.dunkleente.mctiers.enums.PlayerTier;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * TierWrapper
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
public final class TierWrapper {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String BASE_URL = "https://mctiers.com/api/v2/profile/";

    private TierWrapper() {}

    public static @NotNull CompletableFuture<TierlistPlayer> fetch(final @NotNull UUID uuid) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + uuid + "/rankings"))
                .GET()
                .build();

        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    final Map<GameMode, PlayerTier> result = new EnumMap<>(GameMode.class);

                    if (response.statusCode() == 200) {
                        try {
                            final JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

                            for (final GameMode mode : GameMode.values()) {
                                if (!root.has(mode.getApiKey())) {
                                    result.put(mode, PlayerTier.UNRANKED);
                                    continue;
                                }

                                final JsonObject data = root.getAsJsonObject(mode.getApiKey());
                                final int mapped = PlayerTier.wrap(
                                        data.get("tier").getAsInt(),
                                        data.get("pos").getAsInt()
                                );
                                result.put(mode, PlayerTier.from(mapped));
                            }
                        } catch (final Exception e) {
                            fillUnranked(result);
                        }
                    } else {
                        fillUnranked(result);
                    }

                    return new TierlistPlayer(uuid, result);
                })
                .exceptionally(throwable -> {
                    final Map<GameMode, PlayerTier> fallback = new EnumMap<>(GameMode.class);
                    fillUnranked(fallback);
                    return new TierlistPlayer(uuid, fallback);
                });
    }

    private static void fillUnranked(final @NotNull Map<GameMode, PlayerTier> map) {
        for (final GameMode mode : GameMode.values()) {
            map.put(mode, PlayerTier.UNRANKED);
        }
    }
}