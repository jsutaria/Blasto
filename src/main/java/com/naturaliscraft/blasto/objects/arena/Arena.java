package com.naturaliscraft.blasto.objects.arena;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.events.BlastoWinEvent;
import com.naturaliscraft.blasto.events.GameStateChangeEvent;
import com.naturaliscraft.blasto.objects.BlastoPlayer;
import com.naturaliscraft.blasto.objects.GameState;
import com.naturaliscraft.blasto.util.ScoreboardEditor;
import com.naturaliscraft.blasto.util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Arena {
	private String id;
	private Location corner1;
	private Location corner2;
	//private Location lobby;
	private Set<BlastoPlayer> players = new HashSet<BlastoPlayer>();
	private Set<Player> spectate = new HashSet<Player>();
	private GameState gamestate;
	private int timeInWaiting;
	private List<Location> spawnPoints = new ArrayList<Location>();
	
	public Arena(String name, Location c1, Location c2, List<Location> sp)  {
		this.id = name;
		double xbig = c1.getBlockX() > c2.getBlockX() ? c1.getBlockX() : c2.getBlockX();
		double xsmall = c1.getBlockX() < c2.getBlockX() ? c1.getBlockX() : c2.getBlockX();
		
		double ybig = c1.getBlockY() > c2.getBlockY() ? c1.getBlockY() : c2.getBlockY();
		double ysmall = c1.getBlockY() < c2.getBlockY() ? c1.getBlockY() : c2.getBlockY();
		
		double zbig = c1.getBlockZ() > c2.getBlockZ() ? c1.getBlockZ() : c2.getBlockZ();
		double zsmall = c1.getBlockZ() < c2.getBlockZ() ? c1.getBlockZ() : c2.getBlockZ();
		
		//this.lobby = lby;
		this.corner1 = new Location(c1.getWorld(), xbig, ybig, zbig);
		this.corner2 = new Location(c1.getWorld(), xsmall, ysmall, zsmall);
		this.gamestate = GameState.RESETTING;
		this.spawnPoints = sp;
	}
	
	public int getTimeInWaiting() {
		return this.timeInWaiting;
	}
	
	
	public void broadcast(String message) {
		for(BlastoPlayer p : this.players) {
			p.getPlayer().sendMessage(Util.color(Blasto.tag + message));
		}
	}
	
	public String getID() {
		return this.id;
	}
	
	public Location getC1() {
		return this.corner1;
	}
	
	public Location getC2() {
		return this.corner2;
	}
	
	public Set<BlastoPlayer> getPlayers() {
		return this.players;
	}
	
	public BlastoPlayer getFirstPlayer() {
		for(BlastoPlayer p : this.players) {
			return p;
		}
		return null;
	}
	
	public void addPlayer(BlastoPlayer p) {
		if(this.players.size() > 20) {
			p.getPlayer().sendMessage("&cToo many people in the arena! Please join the next game");
			return;
		}
		this.players.add(p);
		try { 
			for(BlastoPlayer pl : this.players) {
				ScoreboardEditor.updateScoreBoard(pl.getPlayer(), false);
			}
			} catch (Exception e) {}
		this.broadcast(p.getPlayer().getDisplayName() + " &cjoined the arena!");
	}
	
	public void removePlayer(BlastoPlayer p) {
		this.players.remove(p);
		ScoreboardEditor.updateScoreBoard(p.getPlayer(), true);
		try { 
		for(BlastoPlayer pl : this.players) {
			ScoreboardEditor.updateScoreBoard(pl.getPlayer(), false);
		}
		} catch (Exception e) {}
//		p.getPlayer().teleport(Blasto.getArenaManager().getLobby());
		p.getPlayer().removePotionEffect(PotionEffectType.SPEED);
		p.getPlayer().getInventory().clear();
		if(!this.spectate.contains(p.getPlayer())) p.getPlayer().teleport(Blasto.getArenaManager().getLobby());
	}
	
	public void addPlayer(Player p) {
		this.addPlayer(new BlastoPlayer(p));
	}
	
	public BlastoPlayer getPlayer(Player p) {
		for (BlastoPlayer pl : this.getPlayers()) {
			if(pl.getPlayer().equals(p)) return pl;
		}
		return null;
	}
	
	public void removePlayer(Player p) {
		this.removePlayer(this.getPlayer(p));
	}
	
	public void removeAllPlayers() {
		this.players.removeAll(this.players);
		
	}
	
	public GameState getGameState() {
		return this.gamestate;
	}
	
	public void setGameState(GameState gs) {
		Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, this.getGameState(), gs));
		this.gamestate = gs;
	}

	public void fixYPlusSix() {
		//FIXME: again, another horrible fix for the Y+6 bug
		for(BlastoPlayer p : this.getPlayers()) {
			Location l = p.getPlayer().getLocation();
			if(l.getY() > 8.0) {
				l.setY(6.025);
				p.getPlayer().teleport(l);
			}
		}
	}
	
	public void advanceGameState() {
		setGameState(GameState.advance(this.gamestate));
	}
	
	@SuppressWarnings("deprecation")
	public void cleanup() {
		World w = this.corner1.getWorld();
		int y = this.corner1.getBlockY() + 1;
		for(int x = this.corner1.getBlockX(); x >= this.corner2.getBlockX(); x--) {
			for(int z = this.corner1.getBlockZ(); z >= this.corner2.getBlockZ(); z--) {
				Block b = w.getBlockAt(x, y, z);
				if(b.getType().equals(Material.AIR)) {
					b.setType(Material.LEAVES);
					b.getLocation().clone().add(0, 1, 0).getBlock().setType(Material.LEAVES);
					b.getLocation().clone().add(0, 2, 0).getBlock().setType(Material.LEAVES);
				} else if(b.getType().equals(Material.CARPET)) {
					if(b.getData() != DyeColor.WHITE.getWoolData()) {
						b.setType(Material.LEAVES);
						b.getLocation().clone().add(0, 1, 0).getBlock().setType(Material.LEAVES);
						b.getLocation().clone().add(0, 2, 0).getBlock().setType(Material.LEAVES);
					}
				}
			}
		}
	}
	public boolean canAdvance() {
		if(this.gamestate.equals(GameState.ACTIVE)) {
			if (this.players.size() == 1) {
				BlastoPlayer pl = this.getFirstPlayer();
				if(pl == null) {
				} else {
					BlastoWinEvent bwe = new BlastoWinEvent(pl);
					Bukkit.getPluginManager().callEvent(bwe);
				}
			}
			if (this.players.size() == 0) {
 				return true;
			}
		}
		else if (this.gamestate.equals(GameState.WAITING)) {
			if(this.players.size() >= 3 && this.timeInWaiting == 0) this.broadcast("&cGame starting in 30 seconds!");
			if (this.players.size() >= 3) {
				for(BlastoPlayer p : this.players) {
					p.getPlayer().setLevel((30-this.timeInWaiting));
					float exp = (30 - this.timeInWaiting)/ 30F;
					p.getPlayer().setExp(exp);
				}
				if(this.timeInWaiting >= 30) { 
					for(BlastoPlayer p : this.players) {
						p.getPlayer().getInventory().clear();
						p.getPlayer().getInventory().addItem(new ItemStack(Material.TNT, 1));
					}
					this.teleporAll();
					return true;
				}
				else this.timeInWaiting++;
			} else {
				this.timeInWaiting = 0;
			}
		} else if (this.gamestate.equals(GameState.RESETTING)) {
			for(Arena a : Blasto.getArenaManager().getArenas()) {
				if(a.getGameState().equals(GameState.WAITING)) return false;
			}
			this.cleanup();
			return true;
		}
		return false;
	}

	public void teleporAll() {
		// TODO Auto-generated method stub
		int x = 0;
		for(BlastoPlayer bp : this.players) {

            // FIXME: Really bad temporary fix
            //
            // This doesn't actually solve the problem
            // It literally just changes the integer back
            //
            if (this.spawnPoints.get(x).getY() == 12.0625) {
                this.spawnPoints.get(x).setY(6.0625);

                Bukkit.getServer().getConsoleSender().sendMessage(
                    String.format(
                        "Fixed improper spawn Y +6 bug for %s",
                        bp.getPlayer().getName()
                    )
                );
            }

			bp.getPlayer().teleport(this.spawnPoints.get(x));

			x++;
		}
	}

	public List<Location> getSpawnPoints() {
		return spawnPoints;
	}

	public void setSpawnPoints(List<Location> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}
	
	public void addSpawnPoints(Location sp) {
		this.spawnPoints.add(sp);
	}

	//***
	public Set<Player> getSpectators() {
		return this.spectate;
	}

	public void addSpectator(Player p) {
		this.spectate.add(p);

        Bukkit.getServer().getConsoleSender().sendMessage(
                String.format("%s is spectating", p.getName())
        );

		p.teleport(this.getSpawnPoints().get(0).add(0, 6, 0));
		p.setAllowFlight(true);
		p.setFlying(true);
		
	}
	
	public void removeSpectator(Player p) {
		this.spectate.remove(p);
		p.teleport(Blasto.getArenaManager().getLobby());
		p.setAllowFlight(false);
		p.setFlying(false);
	}
	
	public void removeSpectators() {
		for(Player p : this.spectate) {
			this.removeSpectator(p);
		}
	}
	//***

}
