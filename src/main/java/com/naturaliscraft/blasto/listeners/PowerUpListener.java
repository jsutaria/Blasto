package com.naturaliscraft.blasto.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.events.PowerUpEvent;
import com.naturaliscraft.blasto.objects.GameState;
import com.naturaliscraft.blasto.objects.PowerUps;
import com.naturaliscraft.blasto.objects.arena.Arena;
import com.naturaliscraft.blasto.util.ScoreboardEditor;

public class PowerUpListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Arena a = Blasto.getArenaManager().getPlayerArena(e.getPlayer());
		if(a==null) return;
		if(!a.getGameState().equals(GameState.ACTIVE)) return;
		PowerUps p = PowerUps.standingOnPowerUp(e.getPlayer());
		if(p == null) return;

		Bukkit.getPluginManager().callEvent(new PowerUpEvent(
				Blasto.getArenaManager().getBlastoPlayer(e.getPlayer()), p)); 
		e.getPlayer().getLocation().getBlock().setType(Material.AIR);
	}
	
	@EventHandler
	public void onPowerUp(PowerUpEvent e) {
		e.getPlayer().powerUp(e.getPowerUp());
		if(e.getPowerUp().equals(PowerUps.TNT)) {
			e.getPlayer().getPlayer().getInventory().addItem(new ItemStack(Material.TNT, 1));
		}
		ScoreboardEditor.updateScoreBoard(e.getPlayer().getPlayer(), false);
	}
}
