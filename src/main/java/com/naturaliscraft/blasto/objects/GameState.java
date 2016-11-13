package com.naturaliscraft.blasto.objects;

public enum GameState {
	WAITING,
	ACTIVE,
	RESETTING;
	
	public static GameState advance(GameState current) {
		if(current.equals(GameState.WAITING)) return GameState.ACTIVE;
		else if(current.equals(GameState.ACTIVE)) return GameState.RESETTING;
		else return GameState.WAITING;
	}
}
