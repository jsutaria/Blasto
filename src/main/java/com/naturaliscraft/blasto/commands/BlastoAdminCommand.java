package com.naturaliscraft.blasto.commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.objects.arena.Arena;
import com.naturaliscraft.blasto.objects.arena.ArenaStorage;
import com.naturaliscraft.blasto.util.Util;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class BlastoAdminCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		if(!player.hasPermission("blasto.admin")) return true;
		if(args.length == 0) {
			player.sendMessage(Util.color("&c/ba create <ArenaName>"));
			player.sendMessage(Util.color("&4&lMust have arena region selected with worldedit"));
			player.sendMessage(Util.color("&c/ba setlobby"));
			return true;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("setlobby")) {
				Blasto.getArenaManager().setLobby(player.getLocation());
				ArenaStorage.setLobby(player.getLocation());
				player.sendMessage(Util.color("&4&lLobby set!"));
				return true;
			}
		}
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("create")) {
				Selection sel = Blasto.getWorldEdit().getSelection(player);
				Arena a = new Arena(args[1], sel.getMaximumPoint(), sel.getMinimumPoint(), new ArrayList<Location>());
				ArenaStorage.saveArena(a);
				Blasto.getArenaManager().addArena(a);
				player.sendMessage(args[1] + " created!");
			}
			
			if(args[0].equalsIgnoreCase("addspawn")) {
				Arena a = Blasto.getArenaManager().getArenaByID(args[1]);
				a.addSpawnPoints(player.getLocation());
				ArenaStorage.saveArena(a);
				player.sendMessage("Spawn Added!");
			}
		}	
		return true;
	}
}
