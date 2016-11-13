package com.naturaliscraft.blasto.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.naturaliscraft.blasto.objects.GameState;
import com.naturaliscraft.blasto.objects.arena.Arena;

public class GameStateChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Arena arena;
    private GameState prevGameState;
    private GameState newGameState;

    public GameStateChangeEvent(Arena ar, GameState prevGS, GameState newGS) {
        this.arena = ar;
        this.prevGameState = prevGS;
        this.newGameState = newGS;
    }
    
    public Arena getArena() {
    	return this.arena;
    }
    
    public GameState getPreviousGameState() {
    	return this.prevGameState;
    }
    
    public GameState getNewGameState() {
    	return this.newGameState;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    
}