package com.naturaliscraft.blasto.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.objects.BlastoPlayer;
import com.naturaliscraft.blasto.objects.arena.Arena;

public class BlastoDieEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private BlastoPlayer dyingPl;
    private BlastoPlayer killer;
    private Arena arena;

    public BlastoDieEvent(BlastoPlayer kill, BlastoPlayer dyer) {
        this.dyingPl = dyer;
        this.killer = kill;
        this.arena = Blasto.getArenaManager().getPlayerArena(this.dyingPl.getPlayer());
    }
    
    public Arena getArena() {
    	return this.arena;
    }
    
    public BlastoPlayer getDyer() {
    	return this.dyingPl;
    }
    
    public BlastoPlayer getKiller() {
    	return this.killer;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    
}