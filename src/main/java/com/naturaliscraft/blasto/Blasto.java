package com.naturaliscraft.blasto;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.naturaliscraft.blasto.commands.BlastoAdminCommand;
import com.naturaliscraft.blasto.commands.BlastoCommand;
import com.naturaliscraft.blasto.listeners.GameChange;
import com.naturaliscraft.blasto.listeners.PlayerConnectivity;
import com.naturaliscraft.blasto.listeners.PowerUpListener;
import com.naturaliscraft.blasto.listeners.TNTPlacing;
import com.naturaliscraft.blasto.listeners.WinLossListener;
import com.naturaliscraft.blasto.objects.arena.ArenaManager;
import com.naturaliscraft.blasto.objects.arena.ArenaStorage;
import com.naturaliscraft.blasto.util.Config;
import com.naturaliscraft.blasto.util.CustomConfig;
import com.naturaliscraft.blasto.util.ScoreboardEditor;
import com.naturaliscraft.blasto.util.Util;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class Blasto extends JavaPlugin {

	private static ArenaManager am;
	private static WorldEditPlugin we;
	public static String tag = "&8[&7&k&l!&4&lB&c&ll&6&la&4&ls&c&lt&6&lo&7&k&l!&8] ";
	private static Blasto instance;
	
	@Override
	public void onEnable() {
		instance = this;

		try {
			ArenaStorage.arenaStorage = new CustomConfig(this, "arenas.yml");
			ArenaStorage.arenaManagerStorage = new CustomConfig(this, "arenamanager.yml");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Config.setup();

		PluginManager pm = Bukkit.getPluginManager();
	
		getCommand("blasto").setExecutor(new BlastoCommand());
		getCommand("ba").setExecutor(new BlastoAdminCommand());
		
		pm.registerEvents(new GameChange(), this);
		pm.registerEvents(new PlayerConnectivity(),  this);
		pm.registerEvents(new WinLossListener(), this);
		pm.registerEvents(new PowerUpListener(), this);
		pm.registerEvents(new TNTPlacing(), this);
		
		we = (WorldEditPlugin) pm.getPlugin("WorldEdit");
		
		if(we == null) {
			System.out.println("Error, WorldEdit not found... disabling");
			pm.disablePlugin(this);
			return;
		}
		
		
		am = new ArenaManager(Util.deserializeLoc(ArenaStorage.arenaManagerStorage.getConfig().getString("lobby")));
		ArenaStorage.configureAllArenas(am);
		

		for(Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(am.getLobby());
			ScoreboardEditor.updateScoreBoard(p, true);
		}
		
		am.startGames();

	}
	
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}
	
	public static Blasto get() {
		return instance;
	}

	public static ArenaManager getArenaManager() {
		return am;
	}
	
	public static WorldEditPlugin getWorldEdit() {
		return we;
	}
}
