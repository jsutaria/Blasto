package com.naturaliscraft.blasto.util;

import org.bukkit.Bukkit;

import com.naturaliscraft.blasto.objects.arena.ArenaStorage;

public class Config {

	public static void setup() {
		ArenaStorage.arenaManagerStorage.getConfig().addDefault("lobby", Util.serializeLoc(Bukkit.getWorlds().get(0).getSpawnLocation()));
		ArenaStorage.arenaManagerStorage.getConfig().options().copyDefaults(true);
		ArenaStorage.arenaManagerStorage.saveConfig();
	}

}
