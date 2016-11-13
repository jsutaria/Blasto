package com.naturaliscraft.blasto.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.events.BlastoDieEvent;
import com.naturaliscraft.blasto.objects.BlastoPlayer;
import com.naturaliscraft.blasto.objects.GameState;
import com.naturaliscraft.blasto.objects.arena.Arena;
import com.naturaliscraft.blasto.util.Util;

public class BlastoCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		if(args.length == 0) {
			p.sendMessage(Util.color(Blasto.tag + "&6Developer: &c" + Blasto.get().getDescription().getAuthors().get(0)));
			p.sendMessage(Util.color(Blasto.tag + "&6Version: &c" + Blasto.get().getDescription().getVersion()));
			p.sendMessage(Util.color(Blasto.tag + "&6/blasto join &cto join a game!"));
			return true;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("join")) {
				if(Blasto.getArenaManager().isPlaying(p)) {
					p.sendMessage(Util.color(Blasto.tag + "&cError, you are already in a game!"));
					return true;
				}
				try {
					Blasto.getArenaManager().getCurrentWaiting().addPlayer(new BlastoPlayer(p));
					p.sendMessage(Util.color(Blasto.tag + "&cJoined the game!"));
				} catch (Exception e) {
					e.printStackTrace();
					p.sendMessage(Util.color(Blasto.tag + "&cError while trying to join the arena! Please report:"));
				}
				return true;

			}
			if(args[0].equalsIgnoreCase("leave")) {
				if(!Blasto.getArenaManager().isPlaying(p)) {

					p.sendMessage(Util.color(Blasto.tag + "&cError, you are not playing a game!"));
					return true;
				}
				/*
				Blasto.getArenaManager().getPlayerArena(p).removePlayer(p);
				
				p.sendMessage("Left Arena!");
				*/
				
				Arena ba = Blasto.getArenaManager().getPlayerArena(p);
				ba.broadcast(p.getDisplayName() + " &chas left the arena!");

				if(ba.getGameState().equals(GameState.ACTIVE)) {
					BlastoDieEvent bde = new BlastoDieEvent(null, ba.getPlayer(p));
					Bukkit.getPluginManager().callEvent(bde);
				} else {
					ba.removePlayer(p);
				}
				p.teleport(Blasto.getArenaManager().getLobby());
				p.getInventory().clear();
				return true;
			}
		}
		return true;
	}
	

}
