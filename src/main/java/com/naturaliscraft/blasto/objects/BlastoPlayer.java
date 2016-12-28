package com.naturaliscraft.blasto.objects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.naturaliscraft.blasto.Blasto;

public class BlastoPlayer {
	private Player player;
	private int powerLevel;
	private int speedLevel;
	private int tntLevel;
	
	public BlastoPlayer(Player pl) {
		this.player = pl;
		this.powerLevel = 1;
		this.speedLevel = 1;
		this.tntLevel = 1;
	}

	public int getPowerLevel() {
		return this.powerLevel;
	}

	public int getSpeedLevel() {
		return this.speedLevel;
	}
	
	public int getTNTLevel() {
		return this.tntLevel;
	}
	
	public void setPowerLevel(int i) {
		this.powerLevel = i;
	}

	public void setSpeedLevel(int i) {
		this.speedLevel = i;
		this.player.removePotionEffect(PotionEffectType.SPEED);
		this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 50000, i - 1));
	}
	
	public void setTNTLevel(int i) {
		this.tntLevel = i;
	}
	
	public void powerUp(PowerUps p) {
		if(p.equals(PowerUps.POWER)) this.setPowerLevel(this.getPowerLevel() + 1);
		else if(p.equals(PowerUps.SPEED)) this.setSpeedLevel(this.getSpeedLevel() + 1);
		else this.setTNTLevel(this.getTNTLevel() + 1);;
	}
	
	public int getPower(PowerUps p) {
		switch(p) {
			case POWER: return this.powerLevel;
			case SPEED: return this.speedLevel;
			case TNT: return this.tntLevel;
		}
		return -1;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void teleportToLobby() {
		this.player.teleport(Blasto.getArenaManager().getLobby());
	}

}
