package dev.dunkleente.mctiers;

import com.google.gson.JsonParser;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public final class TierWrapper {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String BASE_URL = "https://mctiers.com/api/v2/profile/";
    private static final String MODE = "vanilla";

    public static @NotNull CompletableFuture<TierlistPlayer> fetch(final @NotNull UUID uuid) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + uuid + "/rankings"))
                .GET()
                .build();

        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            final var root = JsonParser.parseString(response.body()).getAsJsonObject();

                            if (!root.has(MODE)) {
                                return new TierlistPlayer(uuid, PlayerTier.UNRANKED);
                            }

                            final var data = root.getAsJsonObject(MODE);
                            final PlayerTier mapped = PlayerTier.from(PlayerTier.wrap(data.get("tier").getAsInt(), data.get("pos").getAsInt()));

                            return new TierlistPlayer(uuid, mapped);
                        } catch (final Exception e) {
                            return new TierlistPlayer(uuid, PlayerTier.UNRANKED);
                        }
                    }
                    return new TierlistPlayer(uuid, PlayerTier.UNRANKED);
                })
                .exceptionally(throwable -> new TierlistPlayer(uuid, PlayerTier.UNRANKED));
    }
}