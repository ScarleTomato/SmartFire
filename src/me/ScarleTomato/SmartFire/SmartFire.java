package me.ScarleTomato.SmartFire;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

public class SmartFire extends JavaPlugin {
    public SFConfig config; // variable where we're going to store the config
	SFEventListener listener;
	LinkedList<SFLocationInfo> fireQueue = new LinkedList<SFLocationInfo>();
 
    @Override
    public void onEnable() {
    	loadConfig();
    	loadListener();

    	new ScoutFiresTask(this).runTaskLater(this, 20);
    }
 
    @Override
    public void onDisable() {
//		try {
//			config.save();
//		} catch (InvalidConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    private void loadConfig(){
        config = new SFConfig(this); // create config
        try {
            config.init(); // load config file if it exists, create it if it doesn't
        } catch (InvalidConfigurationException e) {
        	config.configFile.delete();
        	try {
				config.init();
			} catch (IOException | InvalidConfigurationException e1) {
				e1.printStackTrace();
			}
		} catch(Exception ex) {
            getLogger().log(Level.SEVERE, "FAILED TO LOAD CONFIG!!!", ex);
            //getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }
    
    private void loadListener(){
		listener = new SFEventListener(this);
		getServer().getPluginManager().registerEvents(this.listener, this);
    }
    
}
