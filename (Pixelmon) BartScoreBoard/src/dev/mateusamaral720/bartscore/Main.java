package dev.mateusamaral720.bartscore;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import dev.mateusamaral720.bartscore.api.Money;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {

		new Register();
		Money.register();
		PlayerScore.load();
		saveDefaultConfig();
		MensagemHabilitou();

	}

	@Override
	public void onDisable() {
		MensagemDesabilitou();
	}
	
	@SuppressWarnings("rawtypes")
	void MensagemHabilitou () {
		ConsoleCommandSender b = Bukkit.getConsoleSender();
		Iterator var = this.getConfig().getStringList("Mensagens.PluginHabilitado").iterator();

		while(var.hasNext()) {
			String msg = (String)var.next();
			b.sendMessage(msg.replaceAll("&", "§"));
		}
	}

	@SuppressWarnings("rawtypes")
	void MensagemDesabilitou () {
		ConsoleCommandSender b = Bukkit.getConsoleSender();
		Iterator var = this.getConfig().getStringList("Mensagens.PluginDesabilitado").iterator();

		while(var.hasNext()) {
			String msg = (String)var.next();
			b.sendMessage(msg.replaceAll("&", "§"));
		}
	}

	public static Main getPlugin() {
		return (Main)getPlugin(Main.class);
	}

}
