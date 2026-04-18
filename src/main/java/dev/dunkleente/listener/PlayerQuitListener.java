package dev.dunkleente.listener;

import dev.dunkleente.Economy;
import dev.dunkleente.mctiers.cache.TierCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * PlayerQuitListener
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
public class PlayerQuitListener implements Listener {

    public PlayerQuitListener() {
        Economy.getInstance().getServer().getPluginManager().registerEvents(this, Economy.getInstance());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TierCache.removePlayer(event.getPlayer().getUniqueId());
    }
}
