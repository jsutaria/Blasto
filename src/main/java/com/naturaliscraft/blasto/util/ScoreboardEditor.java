package com.naturaliscraft.blasto.util;
import java.text.DecimalFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import com.naturaliscraft.API;
import com.naturaliscraft.blasto.Blasto;
import com.naturaliscraft.blasto.objects.BlastoPlayer;
public class ScoreboardEditor {
	
	public static void updateScoreBoard(final Player p, final boolean forceReset) {
		Bukkit.getScheduler().runTaskLater(Blasto.get(), new Runnable() {
			public void run() {
				uSB(p, forceReset);
			}
		}, 10L);
	}
	
	public static void uSB(Player p, boolean forceReset) {
		ScoreboardManager sbm = Bukkit.getScoreboardManager();
		if(Blasto.getArenaManager().isPlaying(p) && !forceReset) {
			BlastoPlayer bp = Blasto.getArenaManager().getBlastoPlayer(p);
			Scoreboard sb = sbm.getNewScoreboard();
			Objective o = sb.registerNewObjective("Gameinfo", "dummy");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			o.setDisplayName(Util.color("&7&k&l!&4B&cl&6a&4s&ct&6o&7&k&l!"));
			o.getScore(Util.color("&f&m---------------------")).setScore(7);
			o.getScore(Util.color("&c")).setScore(6);
			o.getScore(Util.color("&c&l          TNT: &f&l" + bp.getTNTLevel())).setScore(5);
			o.getScore(Util.color("&e&l        Power: &f&l" + bp.getPowerLevel())).setScore(4);
			o.getScore(Util.color("&a&l        Speed: &f&l" + bp.getSpeedLevel())).setScore(3);
			o.getScore(Util.color("&b&l       Players: &f&l" + Blasto.getArenaManager().getPlayerArena(p).getPlayers().size())).setScore(2);
			o.getScore(Util.color("&f")).setScore(1);
			o.getScore(Util.color("&m---------------------")).setScore(0);
			p.setScoreboard(sb);
		} else {
			if(Blasto.api == null) {
				Scoreboard sb = sbm.getNewScoreboard();
				Objective o = sb.registerNewObjective("Gameinfo", "dummy");
				o.setDisplaySlot(DisplaySlot.SIDEBAR);
				o.setDisplayName(Util.color("&7&k&l!&4B&cl&6a&4s&ct&6o&7&k&l!"));
				o.getScore(Util.color("&f&m---------------------")).setScore(6);
				o.getScore(Util.color("&c")).setScore(5);
				o.getScore(Util.color("&c&l          TNT: &f&l" + 1)).setScore(4);
				o.getScore(Util.color("&e&l        Power: &f&l" + 1)).setScore(3);
				o.getScore(Util.color("&a&l        Speed: &f&l" + 1)).setScore(2);
				o.getScore(Util.color("&f")).setScore(1);
				o.getScore(Util.color("&m---------------------")).setScore(0);
				p.setScoreboard(sb);
			} else {
				Scoreboard sb = sbm.getNewScoreboard();
				Objective o = sb.registerNewObjective("Gameinfo", "dummy");
				o.setDisplaySlot(DisplaySlot.SIDEBAR);
				o.setDisplayName(Util.color("&7&k&l!&4B&cl&6a&4s&ct&6o&7&k&l!"));
				o.getScore(Util.color("&f&m---------------------")).setScore(7);
				o.getScore(Util.color("&c")).setScore(6);
				double kills = API.getKills(p.getUniqueId().toString(), "Blasto");
				double deaths = API.getDeaths(p.getUniqueId().toString(), "Blasto");
				DecimalFormat myFormat = new DecimalFormat("0.000");
				String kdrStr = myFormat.format(kills/deaths);
				int games = API.get("games", p.getUniqueId().toString(), "Blasto");
				int wins = API.getWins(p.getUniqueId().toString(), "Blasto");
				int coins = API.getCoins(p.getUniqueId().toString());
				o.getScore(Util.color("&c&l         KDR: &f&l" + kdrStr)).setScore(5);
				o.getScore(Util.color("&e&l    Games Played: &f&l" + games)).setScore(4);
				o.getScore(Util.color("&a&l         Wins: &f&l" + wins)).setScore(3);
				o.getScore(Util.color("&b&l        Coins: &f&l" + coins)).setScore(2);
				o.getScore(Util.color("&f")).setScore(1);
				o.getScore(Util.color("&m---------------------")).setScore(0);
				p.setScoreboard(sb);
			}
		}
	}
}