package com.naturaliscraft.blasto.objects.arena;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import com.naturaliscraft.blasto.objects.GameState;
import com.naturaliscraft.blasto.util.CustomConfig;
import com.naturaliscraft.blasto.util.Util;

public class ArenaStorage {
	public static CustomConfig arenaStorage;
	public static CustomConfig arenaManagerStorage;
	
	public static void saveArena(Arena a) {
		String id = a.getID();
		setLoc1(id, a.getC1());
		setLoc2(id, a.getC2());
		setSpawnPoints(id, a.getSpawnPoints());
		//setLobby(id, a.getLobby());
	}
	
	public static Arena getArena(String id) {
		FileConfiguration config = arenaStorage.getConfig();
		if(!config.contains(id)) return null;
		List<Location> sp = Util.deserializeLocList(config.getStringList(id + ".spawnpoints"));
		
		Arena a = new Arena(id,
				Util.deserializeLoc(config.getString(id + ".loc1")), 
				Util.deserializeLoc(config.getString(id + ".loc2")), 
				sp);
		
		return a;
	}
	
	public static void setLoc1(String id, Location loc) {
		arenaStorage.getConfig().set(id + ".loc1", Util.serializeLoc(loc));
		arenaStorage.saveConfig();
	}
	
	public static void setLoc2(String id, Location loc) {
		arenaStorage.getConfig().set(id + ".loc2", Util.serializeLoc(loc));
		arenaStorage.saveConfig();
	}
	
	public static void setSpawnPoints(String id, List<Location> ll) {
		arenaStorage.getConfig().set(id + ".spawnpoints", Util.serializeLocList(ll));
		arenaStorage.saveConfig();
	}

	public static void configureAllArenas(ArenaManager am) {
		boolean waiting = false;
		for(String arenaID : arenaStorage.getConfig().getKeys(false)) {
			am.addArena(ArenaStorage.getArena(arenaID));
			if(!waiting) {
				am.getArenaByID(arenaID).setGameState(GameState.WAITING);
				waiting = true;
			}
		}
	}

	public static void setLobby(Location location) {
		arenaManagerStorage.getConfig().set("lobby", Util.serializeLoc(location));
		arenaManagerStorage.saveConfig();
	}

	
}
