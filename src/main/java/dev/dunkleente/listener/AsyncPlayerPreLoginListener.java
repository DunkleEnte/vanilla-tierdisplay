package dev.dunkleente.listener;

import dev.dunkleente.Economy;
import dev.dunkleente.mctiers.TierWrapper;
import dev.dunkleente.mctiers.cache.TierCache;
import dev.dunkleente.subtiers.SubTierWrapper;
import dev.dunkleente.subtiers.cache.SubTierCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

/**
 * AsyncPlayerPreLoginListener
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
public class AsyncPlayerPreLoginListener implements Listener {

    public AsyncPlayerPreLoginListener() {
        Economy.getInstance().getServer().getPluginManager().registerEvents(this, Economy.getInstance());
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        final UUID uuid = event.getUniqueId();

        TierWrapper.fetch(uuid)
                .thenAccept(player -> {
                    if (player == null) return;

                    TierCache.addPlayer(player);
                });

        SubTierWrapper.fetch(uuid)
                .thenAccept(player -> {
                    if (player == null) return;

                    SubTierCache.addPlayer(player);
                });
    }
}
