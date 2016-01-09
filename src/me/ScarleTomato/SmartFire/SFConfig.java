package me.ScarleTomato.SmartFire;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SFConfig {
	
	Plugin plugin;
	YamlConfiguration config = new YamlConfiguration();
	File configFile;

	SFBiomeConfig defaultSettings = new SFBiomeConfig();
	HashMap<String, SFBiomeConfig> biomeSettings;
	ArrayList<String> ignitions = new ArrayList<String>();
	int maxFiresPerTask = 10, tickDelayBetweenTasks = 30;
	
	
	public SFConfig(Plugin p){
		plugin = p;
	}
	
	public void init() throws FileNotFoundException, IOException, InvalidConfigurationException{
		configFile = new File(plugin.getDataFolder(), "config.yml");
		if(!configFile.exists()){
			createNewConfig();
		}
		config.load(configFile);
		getDefaultSettings();
		getBiomeSettings();
	}
	
	private void getDefaultSettings(){
		defaultSettings = new SFBiomeConfig(config.getConfigurationSection("defaultSettings"));
		ignitions = new ArrayList<String>(config.getStringList("ignitions"));
		maxFiresPerTask = config.getInt("maxFiresPerTask");
		tickDelayBetweenTasks = config.getInt("tickDelayBetweenTasks");
	}
	
	private void getBiomeSettings(){
		biomeSettings = new HashMap<String, SFBiomeConfig>();
		if(config.contains("biomeSettings")){
			for(String biomeName:config.getConfigurationSection("biomeSettings").getKeys(false)){
				ConfigurationSection biomeCS = config.getConfigurationSection("biomeSettings." + biomeName);
				biomeSettings.put(biomeName, new SFBiomeConfig(biomeCS));
			}
		}
	}
	
	private void createNewConfig(){
		configFile.getParentFile().mkdirs();
		
	//TEMP CODE, eventually replaced with a copyFile method
		config.set("maxFiresPerTask", maxFiresPerTask);
		config.set("tickDelayBetweenTasks", tickDelayBetweenTasks);
		
		ignitions.add("FLINT_AND_STEEL");
		ignitions.add("LAVA");
		config.set("ignitions", ignitions);
		
		defaultSettings = new SFBiomeConfig();
		defaultSettings.flammableMaterials.add("NETHERRACK");
		config.set("defaultSettings", defaultSettings.getCS());
		
		HashMap<String, ConfigurationSection> h = new HashMap<String, ConfigurationSection>();
		h.put("PLAINS", new SFBiomeConfig().getCS());
		h.put("FOREST", new SFBiomeConfig().getCS());
		h.put("DESERT", new SFBiomeConfig().getCS());
		config.set("biomeSettings", h);
		try {
			save();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	//end temp code
	}
	
	public void save() throws InvalidConfigurationException{
		try {
			config.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
