package me.ScarleTomato.SmartFire;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class SFBiomeConfig {
	int generationLimit = 0 , upSpread = 0 , downSpread = 0, sideSpread = 0;
	double probability = 0.0;
	ArrayList<String> flammableMaterials = new ArrayList<String>();
	
	public SFBiomeConfig(){
		
	}

	public SFBiomeConfig(ConfigurationSection cs){
		
		generationLimit = cs.getInt("generationLimit");
		upSpread = cs.getInt("upSpread");
		downSpread = cs.getInt("downSpread");
		sideSpread = cs.getInt("sideSpread");
		probability = cs.getDouble("probability");
		flammableMaterials = new ArrayList<String>(cs.getStringList("flammableMaterials"));
	}
	
	public ConfigurationSection getCS(){
		YamlConfiguration y = new YamlConfiguration();
		y.set("generationLimit", generationLimit);
		y.set("upSpread", upSpread);
		y.set("downSpread", downSpread);
		y.set("sideSpread", sideSpread);
		y.set("probability", probability);
		y.set("flammableMaterials", flammableMaterials);
		return y;
	}
	
	public boolean isFlammable(Material m){
		return flammableMaterials.contains(m.toString());
	}
}
