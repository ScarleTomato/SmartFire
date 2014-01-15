package me.ScarleTomato.SmartFire;

import org.bukkit.Bukkit;
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
			plugin.fireQueue.add(new SFLocationInfo(e.getBlock().getLocation(),e.getBlock().getBiome().toString(),e.getPlayer().getName(),0));
			Bukkit.getServer().broadcastMessage("added. Size = " + plugin.fireQueue.size());
		}
    }
}
