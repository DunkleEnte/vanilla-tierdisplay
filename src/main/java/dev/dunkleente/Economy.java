package dev.dunkleente;

import dev.dunkleente.command.impl.SubTierCommand;
import dev.dunkleente.command.impl.TierCommand;
import dev.dunkleente.command.impl.TierStatsCommand;
import dev.dunkleente.listener.AsyncPlayerPreLoginListener;
import dev.dunkleente.listener.PlayerQuitListener;
import dev.dunkleente.utility.inventory.InventoryManager;
import dev.dunkleente.utility.placeholder.PlaceholderHook;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Economy extends JavaPlugin {

    @Getter
    public static Economy instance;

    public Economy() {
        instance = this;
    }

    @Override
    public void onEnable() {

        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook().register();
        }

        new PlayerQuitListener();
        new AsyncPlayerPreLoginListener();

        new TierCommand();
        new TierStatsCommand();

        new SubTierCommand();

        InventoryManager.register(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
