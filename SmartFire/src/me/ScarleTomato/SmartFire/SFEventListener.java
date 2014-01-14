package me.ScarleTomato.SmartFire;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class SFEventListener implements Listener {
	SmartFire plugin;
	SFConfig config;

    public SFEventListener(SmartFire plugin) {
    	this.plugin = plugin;
    	config = plugin.config;
	}

	@EventHandler(priority = EventPriority.MONITOR)
    public void catchIgnition(BlockIgniteEvent e){
		if	(config.ignitions.contains(e.getCause().toString())){
			
		}
    }
}
