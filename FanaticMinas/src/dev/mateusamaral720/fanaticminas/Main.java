package dev.mateusamaral720.fanaticminas;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.mateusamaral720.fanaticminas.api.Config;
import dev.mateusamaral720.fanaticminas.api.Money;

public class Main extends JavaPlugin{
	
	public static Main getPlugin() {
		return getPlugin(Main.class);
	}
	
	public void onEnable() {
		String[] msg = 
			{"§bPlugin §eBartMinas §bIniciado!",
				"§bAutor: §eMateus Amaral",
				"§bVersao: §e1.0",
				"§bServidor: §eRankUP"};
		Config.cnf.saveDefaultConfig();
		Bukkit.getConsoleSender().sendMessage(msg);
		Bukkit.getPluginManager().registerEvents(new Principal(), this);
		Money.register();
	}

}
