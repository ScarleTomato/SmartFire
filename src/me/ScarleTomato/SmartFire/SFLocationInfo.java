package me.ScarleTomato.SmartFire;

import org.bukkit.Location;

/**
 * Dataset containing all relevant SmartFire info on a particular location
 * @author MikeJr
 *
 */
public class SFLocationInfo {
	Location location;
	String biomeName, fireStarterName;
	int generation;
	
	public SFLocationInfo(Location location, String biomeName, String fireStarterName, int generation){
		this.location = location;
		this.biomeName = biomeName;
		this.fireStarterName = fireStarterName;
		this.generation = generation;
	}
	
}
