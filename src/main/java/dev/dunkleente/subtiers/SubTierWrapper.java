package dev.dunkleente.subtiers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.dunkleente.mctiers.enums.GameMode;
import dev.dunkleente.mctiers.enums.PlayerTier;
import dev.dunkleente.subtiers.enums.SubTierMode;
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
 * SubTierWrapper
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
public final class SubTierWrapper {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String BASE_URL = "https://subtiers.net/api/rankings/";
    
    public static @NotNull CompletableFuture<SubTierlistPlayer> fetch(final @NotNull UUID uuid) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + uuid.toString().replace("-", "")))
                .GET()
                .build();

        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    final Map<SubTierMode, PlayerTier> result = new EnumMap<>(SubTierMode.class);

                    if (response.statusCode() == 200) {
                        try {
                            final JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

                            for (final SubTierMode mode : SubTierMode.values()) {
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

                    return new SubTierlistPlayer(uuid, result);
                })
                .exceptionally(throwable -> {
                    final Map<SubTierMode, PlayerTier> fallback = new EnumMap<>(SubTierMode.class);
                    fillUnranked(fallback);
                    return new SubTierlistPlayer(uuid, fallback);
                });
    }

    private static void fillUnranked(final @NotNull Map<SubTierMode, PlayerTier> map) {
        for (final SubTierMode mode : SubTierMode.values()) {
            map.put(mode, PlayerTier.UNRANKED);
        }
    }
}