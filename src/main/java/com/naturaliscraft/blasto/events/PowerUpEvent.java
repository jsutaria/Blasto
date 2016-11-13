package com.naturaliscraft.blasto.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.naturaliscraft.blasto.objects.BlastoPlayer;
import com.naturaliscraft.blasto.objects.PowerUps;

public class PowerUpEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private BlastoPlayer player;
    private PowerUps power;

    public PowerUpEvent(BlastoPlayer pl, PowerUps pow) {
    	this.player = pl;
    	this.power = pow;
    }
    
    public BlastoPlayer getPlayer() {
    	return this.player;
    }
    
    public PowerUps getPowerUp() {
    	return this.power;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    
}