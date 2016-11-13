package com.naturaliscraft.blasto.events;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.naturaliscraft.blasto.objects.BlastoPlayer;

public class TNTPlaceEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private BlastoPlayer player;
    private Location tntSpot;

    public TNTPlaceEvent(BlastoPlayer pl, Location tntspot) {
    	this.player = pl;
    	this.tntSpot = tntspot;
    }
    
    public BlastoPlayer getPlayer() {
    	return this.player;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

	public Location getTntSpot() {
		return tntSpot;
	}
    
}