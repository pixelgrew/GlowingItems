package com.li2424.glowingitems;

import com.li2424.glowingitems.config.Config;
import com.li2424.glowingitems.event.PlayerEventListener;
import com.li2424.glowingitems.light.PlacedLight;
import com.li2424.glowingitems.util.Messages;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class GlowingItems extends JavaPlugin {
    public ArrayList<PlacedLight> savedBlockStates;

    @Override
    public void onEnable() {
        savedBlockStates = new ArrayList<>();

        init();
        if (Config.isEnabled(this)) {
            Messages.showEnableMessage(getLogger());
        } else {
            Messages.showDisabledMessage(getLogger());
        }
    }

    @Override
    public void onDisable() {
        Messages.showDisableMessage(getLogger());
    }

    public void init() {
        PluginManager manager = getServer().getPluginManager();
        Config.init(this);

        //register events
        manager.registerEvents(new PlayerEventListener(this), this);
    }
}
