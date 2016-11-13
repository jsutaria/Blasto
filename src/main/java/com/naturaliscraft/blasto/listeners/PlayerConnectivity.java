package com.naturaliscraft.blasto.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.events.BlastoDieEvent;
import com.naturaliscraft.blasto.objects.GameState;
import com.naturaliscraft.blasto.objects.arena.Arena;
import com.naturaliscraft.blasto.util.ScoreboardEditor;

public class PlayerConnectivity implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().teleport(Blasto.getArenaManager().getLobby());
		e.getPlayer().getInventory().clear();
		ScoreboardEditor.updateScoreBoard(e.getPlayer(), false);
		e.getPlayer().setAllowFlight(false);
		e.getPlayer().setFlying(false);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if(Blasto.getArenaManager().isPlaying(e.getPlayer())) {
			Arena ba = Blasto.getArenaManager().getPlayerArena(e.getPlayer());
			if(ba.getGameState().equals(GameState.ACTIVE)) {
				BlastoDieEvent ble = new BlastoDieEvent(null, ba.getPlayer(e.getPlayer()));
				Bukkit.getPluginManager().callEvent(ble);
			} else {
				ba.removePlayer(e.getPlayer());
			}
			ba.broadcast(e.getPlayer().getPlayer().getDisplayName() + " &chas left the arena!");
		}
	}
}
