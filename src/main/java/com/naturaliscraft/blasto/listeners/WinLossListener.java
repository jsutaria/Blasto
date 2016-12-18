package com.naturaliscraft.blasto.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.events.BlastoDieEvent;
import com.naturaliscraft.blasto.events.BlastoWinEvent;
import com.naturaliscraft.blasto.objects.arena.Arena;
import com.naturaliscraft.blasto.util.Util;

public class WinLossListener implements Listener {

	
	@EventHandler
	public void onWin(BlastoWinEvent e) {
		Bukkit.broadcastMessage(Util.color(Blasto.tag +  
				"&3" + e.getPlayer().getPlayer().getDisplayName() + "&c has won the game on " + e.getArena().getID()));
		e.getArena().removePlayer(e.getPlayer());
		e.getPlayer().teleportToLobby(); 
	}
	
	@EventHandler
	public void onDie(BlastoDieEvent e) {
		Arena a = e.getArena();
		if(e.getKiller() != null) {
			if(!e.getKiller().getPlayer().equals(e.getDyer().getPlayer())) {
				a.broadcast("&3" + e.getKiller().getPlayer().getDisplayName() + "&c has blasted " + 
						e.getDyer().getPlayer().getDisplayName());
			} else { 
				a.broadcast("&3" + e.getKiller().getPlayer().getDisplayName() + "&c has blasted themselves!");
			} 
		}
		//***
		if(a.getPlayers().size() > 2) a.addSpectator(e.getDyer().getPlayer());
		//***

        Bukkit.getServer().getConsoleSender().sendMessage(
                String.format("%s is deaded", e.getDyer().getPlayer().getName())
        );

		a.removePlayer(e.getDyer());
	}
	

}
