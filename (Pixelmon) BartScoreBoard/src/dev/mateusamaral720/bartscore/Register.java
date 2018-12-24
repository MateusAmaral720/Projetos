package dev.mateusamaral720.bartscore;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class Register {
	
	public Register() {
		this.eventos(new PlayerScore());
		this.comandos("score", new PlayerScore());
	}
	
	void comandos(String comando, CommandExecutor classe) {
	      Main pl = Main.getPlugin();
	      pl.getCommand(comando).setExecutor(classe);
	      
	   }

	   void eventos(Listener classe) {
	      Main pl = Main.getPlugin();
	      Bukkit.getPluginManager().registerEvents(classe, pl);
	   }

}
