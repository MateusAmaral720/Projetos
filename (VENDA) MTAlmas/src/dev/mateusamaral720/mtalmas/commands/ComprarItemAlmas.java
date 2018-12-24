package dev.mateusamaral720.mtalmas.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mateusamaral720.mtalmas.api.AlmasAPI;


public class ComprarItemAlmas implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (cmd.getName().equalsIgnoreCase("compraritem")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cComando bloqueado via console.");
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("§e[Cash] Use: /compraritemalmas (Item CODE)");
				return true;
			}
			String ITEM = args[0];
			if(!AlmasAPI.config.contains("Itens." + ITEM)) {
				p.sendMessage("§e[MT_Almas] §aItem Inexistente.");
				return true;
			}
				double almas = AlmasAPI.config.getDouble("Itens." + ITEM + ".Custo");
				if (AlmasAPI.getAlmas(p.getName()) >= almas) {
					for (String s : AlmasAPI.config.getConfig().getStringList("Itens." + ITEM + ".Comandos")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("<player>", p.getName()));
					}
					AlmasAPI.removeAlmas(p.getName(), almas);
					p.sendMessage(AlmasAPI.config.getString("Itens." + ITEM + ".Mensagem").replace("&", "§").replace("<player>", p.getName()));
				} else {
					p.sendMessage("§cVoce nao possui almas suficientes!");
				}
		}
		return false;
	}
}
