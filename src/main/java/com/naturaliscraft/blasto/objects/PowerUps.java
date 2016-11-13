package com.naturaliscraft.blasto.objects;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public enum PowerUps {
	SPEED(DyeColor.LIME),
	POWER(DyeColor.YELLOW),
	TNT(DyeColor.RED);
	
	private DyeColor dye;
	
	PowerUps(DyeColor fm) {
		this.dye = fm;
	}
	
	public DyeColor getDyeColor() {
		return this.dye;
	}
	
	public byte getData() {
		return this.dye.getWoolData();
	}
	
	public static PowerUps standingOnPowerUp(Player player) {
		Location loc = player.getLocation();
		if (!loc.getBlock().getType().equals(Material.CARPET)) return null;
		Block carpet = loc.getBlock();
		for(PowerUps p : PowerUps.values()) {
			if(carpet.getData() == p.getData()) return p;
		}
		return null;
		
	}
}
