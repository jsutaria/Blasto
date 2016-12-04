package com.naturaliscraft.blasto.listeners;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.events.BlastoDieEvent;
import com.naturaliscraft.blasto.events.TNTExplodeEvent;
import com.naturaliscraft.blasto.events.TNTPlaceEvent;
import com.naturaliscraft.blasto.objects.BlastoPlayer;
import com.naturaliscraft.blasto.objects.GameState;
import com.naturaliscraft.blasto.objects.arena.Arena;

public class TNTPlacing implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		e.setCancelled(false);
		Arena a = Blasto.getArenaManager().getPlayerArena(e.getPlayer());
		if(a == null) return;
		if(!a.getGameState().equals(GameState.ACTIVE)) return;
		if(!e.getBlock().getType().equals(Material.TNT)) return;
		BlastoPlayer bp = Blasto.getArenaManager().getBlastoPlayer(e.getPlayer());
		TNTPlaceEvent tpe = new TNTPlaceEvent(bp, new Location(
				e.getBlock().getWorld(), e.getBlock().getX(), a.getC1().getBlockY(), e.getBlock().getZ()));
		Bukkit.getPluginManager().callEvent(tpe);
	}
	
	@EventHandler
	public void onTNTPlace(final TNTPlaceEvent e) {
		int power = e.getPlayer().getPowerLevel();
		Location l = e.getTntSpot();
		World world = l.getWorld();
		final Block tnt = world.getBlockAt(l.clone().add(0, 1, 0));
		final Block tnt1 = world.getBlockAt(l.clone().add(0, 2, 0));
		final Block tnt2 = world.getBlockAt(l.clone().add(0, 3, 0));
		if(!tnt.getType().equals(Material.CARPET)) tnt.setType(Material.TNT);
		tnt1.setType(Material.TNT);
		tnt2.setType(Material.TNT);
		final Set<Location> aff = new HashSet<Location>(); //affected locations
		/*
		for(int x = -power; x <= power; x++) {
			aff.add(l.clone().add(x, 1, 0));
			aff.add(l.clone().add(0, 1, x));
		}
		
		
		
		for(int x = 0; x<= power; x++) {
			boolean dir1 = false;
			boolean dir2 = false;
			Location loc = l.clone().add(x , 1, 0);
			Material mat = loc.getBlock().getType();
			if(!(mat.equals(Material.TNT) || mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) dir1 = true;
			Location loc2 = l.clone().add(0 , 1, x);
			Material mat2 = loc2.getBlock().getType();
			if(!(mat2.equals(Material.TNT) || mat2.equals(Material.AIR) || mat2.equals(Material.CARPET) || mat2.equals(Material.LEAVES))) dir2 = true;
			if(!dir1) aff.add(loc);
			if(!dir2) aff.add(loc2);
		}
		*/
		
		for(int x = 0; x<= power; x++) {
			Location loc = l.clone().add(x , 1, 0);
			Material mat = loc.getBlock().getType();
			if(!(mat.equals(Material.TNT) || mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) {
				break;
			}
			aff.add(loc);
		}
		
		for(int x = 0; x<= power; x++) {
			Location loc = l.clone().add(0 , 1, x);
			Material mat = loc.getBlock().getType();
			if(!(mat.equals(Material.TNT) || mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) {
				break;
			}
			aff.add(loc);
		}
		
		for(int x = 0; x>= -power; x--) {
			Location loc = l.clone().add(x , 1, 0);
			Material mat = loc.getBlock().getType();
			if(!(mat.equals(Material.TNT) || mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) {
				break;
			}
			aff.add(loc);
		}
		
		for(int x = 0; x>= -power; x--) {
			Location loc = l.clone().add(0 , 1, x);
			Material mat = loc.getBlock().getType();
			if(!(mat.equals(Material.TNT) || mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) {
				break;
			}
			aff.add(loc);
		}
		/*
		for(int x = 0; x>= -power; x--) {
			boolean dir1 = false;
			boolean dir2 = false;
			Location loc = l.clone().add(x , 1, 0);
			Material mat = loc.getBlock().getType();
			if(!(mat.equals(Material.TNT) || mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) {
				dir1 = true;
			}
			Location loc2 = l.clone().add(0 , 1, x);
			Material mat2 = loc2.getBlock().getType();
			if(!(mat2.equals(Material.TNT) || mat2.equals(Material.AIR) || mat2.equals(Material.CARPET) || mat2.equals(Material.LEAVES))) dir2 = true;
			if(!dir1) aff.add(loc);
			if(!dir2) aff.add(loc2);
		}
		
		
		for(int x = 0; x<= power; x++) {
			Location loc = l.clone().add(x, 1, 0);
			Material mat = loc.getBlock().getType();
			Bukkit.broadcastMessage("Going once");
			if(!(mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) break;
			Bukkit.broadcastMessage("Going twice");
			aff.add(loc);
		}
		
		for(int x = 0; x<= power; x++) {
			Location loc = l.clone().add(-x, 1, 0);
			Material mat = loc.getBlock().getType();
			Bukkit.broadcastMessage("Going once1");
			if(!(mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) break;
			Bukkit.broadcastMessage("Going twice1");
			aff.add(loc);
		}
		
		for(int x = 0; x<= power; x++) {
			Location loc = l.clone().add(0, 1, x);
			Material mat = loc.getBlock().getType();
			Bukkit.broadcastMessage("Going once3");
			if(!(mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) break;
			Bukkit.broadcastMessage("Going twice3");
			aff.add(loc);
		}
		
		for(int x = 0; x<= power; x++) {
			Location loc = l.clone().add(0, 1, -x);
			Material mat = loc.getBlock().getType();
			Bukkit.broadcastMessage("Going once3");
			if(!(mat.equals(Material.AIR) || mat.equals(Material.CARPET) || mat.equals(Material.LEAVES))) break;
			Bukkit.broadcastMessage("Going twice3");
			aff.add(loc);
		}
		
		*/
		Bukkit.getScheduler().runTaskLater(Blasto.get(), new Runnable() {
			public void run() {
				e.getPlayer().getPlayer().getInventory().addItem(new ItemStack(Material.TNT, 1));
				TNTExplodeEvent tee = new TNTExplodeEvent(e.getPlayer(), aff);
				Bukkit.getPluginManager().callEvent(tee);
				Bukkit.getServer().getWorlds().get(0).playSound(tnt.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 30, 0);
				if(!tnt.getType().equals(Material.CARPET)) tnt.setType(Material.AIR);
				tnt1.setType(Material.AIR);
				tnt2.setType(Material.AIR);
			}
		}, 40L);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTNTExplode(TNTExplodeEvent e) {
		Player bp = e.getPlayer().getPlayer();
		Arena a = Blasto.getArenaManager().getPlayerArena(bp);
		Random rand = new Random();
		int random = 0;
		for(final Location l : e.getAffectedLocations()) {
			random = rand.nextInt(14);
			if(l.clone().add(0, 0, 0).getBlock().getType().equals(Material.LEAVES)) {
				l.clone().add(0, 0, 0).getBlock().setType(Material.AIR);
				if(random == 1) {
					l.clone().add(0, 0, 0).getBlock().setType(Material.CARPET);
					random = rand.nextInt(9);
					if(random < 3) {
						l.clone().add(0, 0, 0).getBlock().setData(DyeColor.LIME.getData());
					} else if (random > 6) {
						l.clone().add(0, 0, 0).getBlock().setData(DyeColor.YELLOW.getData());
					} else {
						l.clone().add(0, 0, 0).getBlock().setData(DyeColor.RED.getData());
					}
				}
			}
			if(l.clone().add(0, 1, 0).getBlock().getType().equals(Material.LEAVES)) {
				l.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
			}
			if(l.clone().add(0, 2, 0).getBlock().getType().equals(Material.LEAVES)) {
				l.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
			}
			
			if(l.clone().add(0, 0, 0).getBlock().getType().equals(Material.AIR))
				l.clone().add(0, 0, 0).getBlock().setType(Material.FIRE);
			if(l.clone().add(0, 1, 0).getBlock().getType().equals(Material.AIR))
				l.clone().add(0, 1, 0).getBlock().setType(Material.FIRE);
			if(l.clone().add(0, 2, 0).getBlock().getType().equals(Material.AIR))
				l.clone().add(0, 2, 0).getBlock().setType(Material.FIRE);
				
			Bukkit.getScheduler().runTaskLater(Blasto.get(), new Runnable() {
				public void run() {
					if(l.clone().add(0, 0, 0).getBlock().getType().equals(Material.FIRE))
						l.clone().add(0, 0, 0).getBlock().setType(Material.AIR);
					if(l.clone().add(0, 1, 0).getBlock().getType().equals(Material.FIRE))
						l.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
					if(l.clone().add(0, 2, 0).getBlock().getType().equals(Material.FIRE)) 
						l.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
				}
			}, 4L);
			
			l.getWorld().playEffect(l.clone().add(0,0,0), Effect.SMOKE, 0);
			l.getWorld().playEffect(l.clone().add(0,1,0), Effect.SMOKE, 0);
			l.getWorld().playEffect(l.clone().add(0,2,0), Effect.SMOKE, 0);
			
			try {
				for(BlastoPlayer p : a.getPlayers()) {
					if(a.getPlayers().size() <= 1) break;
					if(p.getPlayer().getLocation().getBlockX() == l.getBlockX() &&
							p.getPlayer().getLocation().getBlockZ() == l.getBlockZ()) {
						BlastoDieEvent bde = new BlastoDieEvent(e.getPlayer(), p);
						Bukkit.getPluginManager().callEvent(bde);
					}
				}
				if(a.getPlayers().size() <= 1) break;
			} catch (Exception ex) {
				
			}
			
		}
	}
	

}
