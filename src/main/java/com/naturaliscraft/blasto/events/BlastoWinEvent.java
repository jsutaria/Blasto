package com.naturaliscraft.blasto.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.objects.BlastoPlayer;
import com.naturaliscraft.blasto.objects.arena.Arena;

public class BlastoWinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private BlastoPlayer player;


    public BlastoWinEvent(BlastoPlayer pl) {
        this.player = pl;
    }
    
    public Arena getArena() {
    	return Blasto.getArenaManager().getPlayerArena(this.player.getPlayer());
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
    
    
}