package com.naturaliscraft.blasto.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomConfig {
	private JavaPlugin plugin = null;
    private String fileName = null;
    private FileConfiguration customConfig = null;
    private File customConfigFile = null;

    public CustomConfig(JavaPlugin plugin, String fileName){
        if(plugin == null){
            throw new IllegalArgumentException("Plugin can't be null");
        }
        
        this.fileName = fileName;
        this.plugin = plugin;
    }
    
    public void saveDefaultConfig() {
    	
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), fileName);
        }
        
        if (!customConfigFile.exists()) {            
             plugin.saveResource(fileName, false);
         }
    }

    public void reloadConfig() {
        if(customConfigFile == null){
            customConfigFile = new File(plugin.getDataFolder(),fileName);
        }

        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
    }

    public FileConfiguration getConfig(){
        if(customConfig == null){
            reloadConfig();
        }
        return customConfig;
    }

    public void saveConfig() {
        if((customConfig == null) || (customConfigFile == null)){
            return;
        }
        try {
            getConfig().save(customConfigFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, e);
        }
    }
}
