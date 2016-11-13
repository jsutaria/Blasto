# Blasto 
[![Build Status](https://travis-ci.org/jsutaria/Blasto.svg?branch=master)](https://travis-ci.org/jsutaria/Blasto)   [![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/jsutaria/Blasto/issues)  [![Github Releases](https://img.shields.io/github/downloads/jsutaria/Blasto/latest/total.svg)](https://github.com/jsutaria/Blasto/archive/master.zip)

Blasto is a spigot plugin where you can blow up your friends! It is open sourced for those interested in expanding it, and based off of the "BomberMan" game.

## Dependencies
Blasto requires only a few dependencies.
* [ ] The obvious spigot/bukkit/craftbukkit
* [ ] WorldEdit from https://dev.bukkit.org/bukkit-plugins/worldedit/

And that's all!

##Setup Instructions
This minigame requires its own server. If you do not currently use [BungeeCord](https://www.spigotmc.org/wiki/bungeecord/) or [LilyPad](http://www.lilypadmc.org), I would advise switching to either of them.

Download the plugin from the `/target/` folder, or build it if you have made changes.

You will need a lobby location, and several arenas. In order to set your lobby location, use `/ba setlobby`. This is defaulted to the world spawn point. All players will log into that location forth. In order to set your arenas, teleport to the arena, use `//wand` and left click on the floor of one corner of the arena, and right click on the floor of the other corner. Be sure to have white carpets as spawnpads for players. Now, do `/ba create <Arena Name>`. *It is advised you have multiple arenas for players to join, or else players wont be able to join while a game is in progress*. Finally, set the spawn locations for the players, by going to each spawnpad you created and do `/ba addspawn <Arena name>`.

##How to play.

The goal of the game is to be the last one standing.

You will start the game with a speed of one, one TNT, which has a power of one. To power up, step on the colored carpets on the arena. A green carpet will increase your speed. A red carpet will increase the amount of ammunition you have (more TNT). A yellow carpet will increase the blast of your TNT explosions.

In order to blast other players, they need to be exploded by your TNT. Place your TNT, and watch it explode!

>May the best blaster win!

## Credits
Many thanks to [TeamRisk](http://github.com/teamrisk) for his help in the project!