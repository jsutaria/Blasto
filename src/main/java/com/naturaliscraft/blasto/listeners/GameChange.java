package com.naturaliscraft.blasto.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.naturaliscraft.blasto.events.GameStateChangeEvent;

public class GameChange implements Listener {

	@EventHandler
	public void onGameChange(GameStateChangeEvent e) {
		//Bukkit.broadcastMessage(e.getArena().getID());
		//Bukkit.broadcastMessage(e.getArena().getC1().toString());
		//Bukkit.broadcastMessage(e.getArena().getC2().toString());
		//Bukkit.broadcastMessage(e.getPreviousGameState().name());
		//Bukkit.broadcastMessage(e.getNewGameState().name());
		System.out.println(e.getArena().getID() + " is advancing from " + e.getPreviousGameState() + " to "
				+ e.getNewGameState());
		try {
			e.getArena().removeSpectators();
		} catch (Exception ex) {
			
		}
	}
}
