package com.naturaliscraft.blasto.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;


public class Util {
    public static String serializeLoc(Location l){
        return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
    }

    public static Location deserializeLoc(String s){
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
    }
    
    public static String color(String s) {
    	return ChatColor.translateAlternateColorCodes('&', s);
    }
    
    public static List<String> serializeLocList(List<Location> loc){
    	List<String> sl = new ArrayList<String>();
    	for(Location l : loc) {
    		sl.add(l.getWorld().getName() + "," + l.getX() + "," + l.getY() + "," + l.getZ());
    	}
    	return sl;
    }

    public static List<Location> deserializeLocList(List<String> s){
    	List<Location> ll = new ArrayList<Location>();
    	for(String str : s) {
    		String[] st = str.split(",");
    		ll.add(new Location(Bukkit.getWorld(st[0]), Double.parseDouble(st[1]), Double.parseDouble(st[2]), Double.parseDouble(st[3])));
    	}
    	return ll;
    }

}
