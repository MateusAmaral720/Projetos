package dev.mateusamaral720.bartcash.comandos;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mateusamaral720.bartcash.api.CashAPI;

public class KeyCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (cmd.getName().equalsIgnoreCase("novakey")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cComando bloqueado via console.");
				return true;
			}
			Player p = (Player) sender;
			if (!p.isOp()) {
				p.sendMessage("§cVoce nao tem permissao.");
				return true;
			}
			if (args.length == 0) {
				p.sendMessage("§e[BartMC] Use: /gerarkey (Quantidade De Cash)");
				return true;
			}
			String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
			String randomString = "";
			int tamanho = 15;
			Random rand = new Random();
			char[] texto = new char[tamanho];
			for (int i = 0; i < tamanho; i++) {
				texto[i] = letras.charAt(rand.nextInt(letras.length()));
			}
			for (int i = 0; i < texto.length; i++) {
				randomString = randomString + texto[i];
			}
			int keyvalue = Integer.parseInt(args[0]);
			CashAPI.config.set("Keys." + randomString, keyvalue);
			CashAPI.config.saveConfig();
			p.sendMessage("§e[BartMC] Key " + randomString + " Criada, com valor de " + Integer.parseInt(args[0])
					+ " De Cash");
		}
		if (cmd.getName().equalsIgnoreCase("ativarkey")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cComando bloqueado via console.");
				return true;
			}	
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("§e[BartMC] §cUse: /ativakey (Key)");
				return true;
			}
			String key = args[0];
			if (CashAPI.config.contains("Keys." + key)) {
				CashAPI.addCash(p.getName(), CashAPI.config.getInt("Keys." + key));
				CashAPI.config.set("Keys." + key, null);
			} else {
				p.sendMessage("§bKey Inexistente!");
			}
		}
		return false;
	}
}
