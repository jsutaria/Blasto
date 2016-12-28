package com.naturaliscraft.blasto.objects.arena;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.objects.BlastoPlayer;
import com.naturaliscraft.blasto.objects.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ArenaManager {
	private Set<Arena> arenas = new HashSet<Arena>();
	private Location lobby;
	
	public ArenaManager(Location lob) {
		this.lobby = lob;
	}
	
	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public Arena getArenaByID(String id) {
		try {
			for(Arena a : this.arenas) {
				if(a.getID().equals(id)) return a;
			}
		} catch (Exception e) {}
		return null;
	}
	
	public boolean isPlaying(Player p) {
		return Blasto.getArenaManager().getPlayerArena(p) != null;
	}
	
	public Arena getPlayerArena(Player p) {
		for(Arena a : arenas) {
			if(a.getPlayer(p) != null) return a;
		}
		return null;
	}
	
	public Set<Arena> getArenas() {
		return this.arenas;
	}
	
	public void addArena(Arena a) {
		this.arenas.add(a);
		a.cleanup();
	}
	
	public BlastoPlayer getBlastoPlayer(Player p) {
		return this.getPlayerArena(p).getPlayer(p);
	}
	public void removeArena(Arena a) {
		this.arenas.remove(a);
	}
	
	public void removeAllArenas() {
		this.arenas = new HashSet<Arena>();
	}
	
	public void startGames() {
		Bukkit.getScheduler().runTaskTimer(Blasto.get(), new Runnable() {
			public void run() {
				for(Arena a : Blasto.getArenaManager().arenas) {
					if(a.canAdvance()) {
						a.advanceGameState();
					}
					if(a.getGameState().equals(GameState.ACTIVE)) a.fixYPlusSix();
				}
			}
		}, 0L, 20L);
	}

	public Arena getCurrentWaiting() {
		for(Arena a : this.getArenas()) {
			if(a.getGameState().equals(GameState.WAITING)) return a;
		}
		return null;
	}

}
