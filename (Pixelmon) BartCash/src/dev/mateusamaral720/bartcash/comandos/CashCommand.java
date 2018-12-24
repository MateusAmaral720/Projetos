package dev.mateusamaral720.bartcash.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mateusamaral720.bartcash.Main;
import dev.mateusamaral720.bartcash.api.CashAPI;


public class CashCommand extends CashAPI implements CommandExecutor {

	public static String prefix = "§eBartMC §8» ";

	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (cmd.getName().equalsIgnoreCase("cash")) {
			if (!(sender instanceof Player)) {
				CommandSender p = sender;
				if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("give")) {
					if(args.length <= 2) {
						p.sendMessage(prefix + "§cUse: /cash give <jogador> <quantia>");
						return true;
					}
					String t = args[1];

					Double valor;
					try {
						valor = Double.parseDouble(args[2]);
					} catch(NumberFormatException e) {
						p.sendMessage(prefix + config.getString("Mensagens.FormatoIncorreto").replace("&", "§"));
						return true;
					}
					
					addCash(t, valor);
					p.sendMessage(prefix + "§aVocê deu §e" + valor + "§a Cash para o jogador §e" + t);
					if(Bukkit.getPlayer(t).isOnline()) {
					Bukkit.getPlayer(t).sendMessage(prefix + "§aVocê recebeu §e" + valor + " §aCash");
					return true;
					}
				}
				
				if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("take")) {
					if(args.length <= 2) {
						p.sendMessage(prefix + "§cUse: /cash take <jogador> <quantia>");
						return true;
					}
					String t = args[1];

					Double valor;
					try {
						valor = Double.parseDouble(args[2]);
					} catch(NumberFormatException e) {
						p.sendMessage(prefix + config.getString("Mensagens.FormatoIncorreto").replace("&", "§"));
						return true;
					}
					
					removeCash(t, valor);
					p.sendMessage(prefix + "§aVocê removeu §e" + valor + "§a Cash do jogador §e" + t);
				}
				
				if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("setar")) {
					if(args.length <= 2) {
						p.sendMessage(prefix + "§cUse: /cash set <jogador> <quantia>");
						return true;
					}
					String t = args[1];

					Double valor;
					try {
						valor = Double.parseDouble(args[2]);
					} catch(NumberFormatException e) {
						p.sendMessage(prefix + config.getString("Mensagens.FormatoIncorreto").replace("&", "§"));
						return true;
					}
					
					setCash(t, valor);
					p.sendMessage(prefix + "§aVocê setou o cash do jogador §e" + t + " §apara §e" + valor);
				}
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage(prefix + config.getString("Mensagens.SeuCash").replace("&", "§").replace("<cash>", String.valueOf(getCash(p.getName()))));
				return true;
			}
			
				if (args[0].equalsIgnoreCase("ajuda")) {
					if (!p.hasPermission("mtcash.admin")) {
						p.sendMessage("§b        MENU HELP        ");
						p.sendMessage("§e/cash ver (Jogador)");
						p.sendMessage("§e/cash ajuda");
						p.sendMessage("§e/cash enviar (Jogador) (Quantia)");
						p.sendMessage("§e/cash");
					} else {
						p.sendMessage("§b        MENU ADMIN HELP        ");
						p.sendMessage("§e/cash ver (Jogador)");
						p.sendMessage("§e/cash ajuda");
						p.sendMessage("§e/cash enviar (Jogador) (Quantia)");
						p.sendMessage("§e/cash add (Jogador) (Quantia)");
						p.sendMessage("§e/cash remove (Jogador) (Quantia)");
						p.sendMessage("§e/cash set (Jogador) (Quantia)");
						p.sendMessage("§e/cash");
					}
				}
				if(args[0].equalsIgnoreCase("top")) {
					  p.sendMessage("       §6» Cash Top «");
					Main.mostrarTop(p);
				}
				
				if (args[0].equalsIgnoreCase("ver")) {
					Player t = Bukkit.getPlayer(args[1]);
					if (args.length == 1) {
						p.sendMessage("§cUse: /cash ver (Jogador)");
						return true;
					}
					if (t == null) {
						p.sendMessage(prefix + "§cJogador offline.");
						return true;
					}
					if (t == p) {
						p.sendMessage("§cPara olhar seu proprio saldo, utilize /cash");
						return true;
					}
					p.sendMessage(prefix + config.getString("Mensagens.VerCash").replace("&", "§").replace("<player>", t.getName()).replace("<cash>", Double.toString(getCash(t.getName()))));
				}
			
			if(args[0].equalsIgnoreCase("enviar") || args[0].equalsIgnoreCase("pay")) {
				Player t = Bukkit.getPlayer(args[1]);
				if (args.length == 1) {
					p.sendMessage(prefix + "§cUse: /cash enviar (jogador) (quantia)");
					return true;
				}
				if (args.length == 2) {
					p.sendMessage(prefix + "§cUse: /cash enviar (jogador) (quantia)");
					return true;
				}
				if (t == null) {
					p.sendMessage(prefix + "§cJogador offline");
					return true;
				}
				Double valor;
				try {
					valor = Double.parseDouble(args[2]);
				} catch (NumberFormatException e) {
					p.sendMessage("§cFormato invalido");
					return true;
				}
				removeCash(p.getName(), valor);
				p.sendMessage(prefix + "§eVocê enviou " + valor + "de cash ao jogador " + t.getName());
				addCash(t.getName(), valor);
				t.sendMessage(prefix + "§aVocê recebeu" + valor + " de cash do jogador " + p.getName());
			}

			if (p.isOp()) {
				if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("give")) {
					if(args.length <= 2) {
						p.sendMessage(prefix + "§cUse: /cash give <jogador> <quantia>");
						return true;
					}
					String t = args[1];

					Double valor;
					try {
						valor = Double.parseDouble(args[2]);
					} catch(NumberFormatException e) {
						p.sendMessage(prefix + config.getString("Mensagens.FormatoIncorreto").replace("&", "§"));
						return true;
					}
					
					addCash(t, valor);
					p.sendMessage(prefix + "§aVocê deu §e" + valor + "§a Cash para o jogador §e" + t);
					if(Bukkit.getPlayer(t).isOnline()) {
					Bukkit.getPlayer(t).sendMessage(prefix + "§aVocê recebeu §e" + valor + " §aCash");
					return true;
					}
				}
				
				if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("take")) {
					if(args.length <= 2) {
						p.sendMessage(prefix + "§cUse: /cash take <jogador> <quantia>");
						return true;
					}
					String t = args[1];

					Double valor;
					try {
						valor = Double.parseDouble(args[2]);
					} catch(NumberFormatException e) {
						p.sendMessage(prefix + config.getString("Mensagens.FormatoIncorreto").replace("&", "§"));
						return true;
					}
					
					removeCash(t, valor);
					p.sendMessage(prefix + "§aVocê removeu §e" + valor + "§a Cash do jogador §e" + t);
				}
				
				if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("setar")) {
					if(args.length <= 2) {
						p.sendMessage(prefix + "§cUse: /cash set <jogador> <quantia>");
						return true;
					}
					String t = args[1];

					Double valor;
					try {
						valor = Double.parseDouble(args[2]);
					} catch(NumberFormatException e) {
						p.sendMessage(prefix + config.getString("Mensagens.FormatoIncorreto").replace("&", "§"));
						return true;
					}
					
					setCash(t, valor);
					p.sendMessage(prefix + "§aVocê setou o cash do jogador §e" + t + " §apara §e" + valor);
				}
			} else { 
				p.sendMessage(prefix + config.getString("Mensagens.SemPermissao").replace("&", "§"));
			}
		}
		return false;
	}

}
