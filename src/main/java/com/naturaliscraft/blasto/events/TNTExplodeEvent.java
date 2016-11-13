package com.naturaliscraft.blasto.events;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.naturaliscraft.blasto.objects.BlastoPlayer;

public class TNTExplodeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private BlastoPlayer player;
    private Set<Location> affectedLocations = new HashSet<Location>();

    public TNTExplodeEvent(BlastoPlayer pl, Set<Location> tntspot) {
    	this.player = pl;
    	this.affectedLocations = tntspot;
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

	public Set<Location> getAffectedLocations() {
		return this.affectedLocations;
	}
    
}