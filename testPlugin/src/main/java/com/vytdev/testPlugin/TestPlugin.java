package com.vytdev.testPlugin;

import org.bukkit.plugin.java.JavaPlugin;
import com.vytdev.testPlugin.RemoveWitherSkeletonsEvent;

// initialize plugin
public class TestPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		// register the event listener
		getServer().getPluginManager().registerEvents(new RemoveWitherSkeletonsEvent(this), this);
	}
}
