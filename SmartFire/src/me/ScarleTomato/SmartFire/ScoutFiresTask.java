package me.ScarleTomato.SmartFire;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Pull a configurable amount of new fire locations off the queue
 * for each location, search for new valid locations (NVLs) around it
 * for each NVL set fire to it and add it to the main queue
 * @author MikeJr
 *
 */
public class ScoutFiresTask extends BukkitRunnable{
	@SuppressWarnings("serial")
	private static final ArrayList<BlockFace> FACES = new ArrayList<BlockFace>(){{add(BlockFace.UP);add(BlockFace.DOWN);add(BlockFace.NORTH);add(BlockFace.SOUTH);add(BlockFace.EAST);add(BlockFace.WEST);}};
	Random r = new Random(System.currentTimeMillis());
	
	SmartFire plugin;
	SFConfig config;
	

	public ScoutFiresTask(SmartFire plugin) {
		this.plugin = plugin;
		config = plugin.config;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		int count = (plugin.fireQueue.size()>config.maxFiresPerTask?config.maxFiresPerTask:plugin.fireQueue.size());
		for(int i = 0;i<count;i++){
			findSurroundingFlammableBlocks(plugin.fireQueue.pop());
		}
		
		new ScoutFiresTask(plugin).runTaskLater(plugin, config.tickDelayBetweenTasks);
		this.cancel();
	}
	
	private void findSurroundingFlammableBlocks(SFLocationInfo sfli){
		Block b = sfli.location.getBlock();
		
		//get spread settings
		SFBiomeConfig bc = (config.biomeSettings.containsKey(sfli.biomeName)?config.biomeSettings.get(sfli.biomeName):config.defaultSettings);
		
		
		//if there is a valid generationLimit for this biome and
		//if this generation is the last, then don't search for any more places to spread
		if(bc.generationLimit > -1 && sfli.generation>bc.generationLimit) return;
		
		//if the block is not fire anymore don't spread
		if(b.getType() != Material.FIRE) return;
		
		//for each block in the specified surrounding area
		Block br;
		for(int y = -1*bc.downSpread;y<=bc.upSpread;y++){
			for(int x = -1*bc.sideSpread;x<=bc.sideSpread;x++){
				for(int z = -1*bc.sideSpread;z<=bc.sideSpread;z++){
					br = b.getRelative(x, y, z);
					//if it's a winning roll and if it's a valid fire location
					if(r.nextDouble() < bc.probability && isValid(br, bc.flammableMaterials)){
						br.setType(Material.FIRE);
						plugin.fireQueue.add(new SFLocationInfo(br.getLocation(), br.getBiome().toString(), sfli.fireStarterName, sfli.generation+1));
					}
				}
			}
		}
	}
	
	private boolean isValid(Block b, ArrayList<String> extraMats){
		Material m;
		if(b.getType() != Material.AIR) return false;
		for (BlockFace face:FACES){
			m = b.getRelative(face).getType();
			if(m.isFlammable() || extraMats.contains(m.toString())) return true;
		}
		return false;
	}

}
