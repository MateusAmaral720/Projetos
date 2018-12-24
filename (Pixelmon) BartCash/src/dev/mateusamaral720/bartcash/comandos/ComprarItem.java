package dev.mateusamaral720.bartcash.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mateusamaral720.bartcash.api.CashAPI;



public class ComprarItem implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (cmd.getName().equalsIgnoreCase("compraritem")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cComando bloqueado via console.");
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage(CashAPI.prefix + " Use: /compraritem (Item CODE)");
				return true;
			}
			String ITEM = args[0];
			if(!CashAPI.config.contains("Itens." + ITEM)) {
				p.sendMessage(CashAPI.prefix + "§aItem Inexistente.");
				return true;
			}
				int cash = CashAPI.config.getInt("Itens." + ITEM + ".Custo");
				if (CashAPI.getCash(p) >= cash) {
					for (String s : CashAPI.config.getConfig().getStringList("Itens." + ITEM + ".Comandos")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("<player>", p.getName()));
					}
					CashAPI.removeCash(p.getName(), cash);
					p.sendMessage(CashAPI.config.getString("Itens." + ITEM + ".Mensagem").replace("&", "§"));
				} else {
					p.sendMessage(CashAPI.prefix + "§cVoce não possui cash suficiente!");
				}
		}
		return false;
	}
}
