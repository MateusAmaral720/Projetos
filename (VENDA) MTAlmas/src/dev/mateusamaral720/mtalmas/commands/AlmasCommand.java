package dev.mateusamaral720.mtalmas.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mateusamaral720.mtalmas.api.AlmasAPI;

public class AlmasCommand extends AlmasAPI implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (cmd.getName().equals("almas")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(getPrefix() + "§cComando bloqueado via console.");
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 0) {
				if (!p.hasPermission("mtalmas.ver")) {
					p.sendMessage(prefix + "§cVocê não tem permissao para isso!");
					return true;
				}
				String almas = String.valueOf(getAlmas(p.getName()));
				p.sendMessage(getPrefix() + config.getString("Mensagens.SuasAlmas").replace("&", "§").replace("<almas>", almas));
				return true;
			}
			if (args[0].equalsIgnoreCase("ver")) {
				if (!p.hasPermission("mtalmas.ver")) {
					p.sendMessage(prefix + config.getString("Mensagens.SemPermissao").replace("&", "§"));
					return true;
				}
				if (Bukkit.getPlayer(args[1]) == null) {
					return true;
				}
				String player = Bukkit.getPlayer(args[1]).getName();
				p.sendMessage(getPrefix() + config.getString("Mensagens.VerAlmas").replace("&", "§").replace("<player>", player).replace("<almas>", String.valueOf(getAlmas(player))));

			}
			if (args[0].equalsIgnoreCase("top")) {
				p.sendMessage("§eTop Jogadores Com Almas");
				if (MySqlEstaAtivado()) {
					p.sendMessage("§a» Top Almas «");
					for (String tops : getTops(p)) {
						p.sendMessage(tops);
					}
				} else {
					p.sendMessage("§a» Top Almas «");
					mostrarTop(p);
				}
			}
			if (!p.hasPermission("mtalmas.admin")) {
				p.sendMessage(prefix + config.getString("Mensagens.SemPermissao").replace("&", "§"));
				return true;
			}
			if(args.length != 2) {
				p.sendMessage("§aArgumentos invalidos.");
				return true;
			}
			if (args[0].equalsIgnoreCase("set")) {
				if (Bukkit.getPlayer(args[1]) == null) {
					return true;
				}
				String player = Bukkit.getPlayer(args[1]).getName();
				Double quantia;
				try {
					quantia = Double.parseDouble(args[2]);
				} catch (NumberFormatException e) {
					p.sendMessage("§cFormato incorreto! use apenas numeros.");
					return true;
				}

				setAlmas(player, quantia);
				Bukkit.getPlayer(player).sendMessage(prefix + "§aVocê recebeu §e" + quantia + "§a Almas.");
				p.sendMessage(prefix + "§aVocê setou as almas do jogador §e" + player + "§a para: §e" + quantia);
			}

			if (args[0].equalsIgnoreCase("add")) {
				if (Bukkit.getPlayer(args[1]) == null) {
					return true;
				}
				String player = Bukkit.getPlayer(args[1]).getName();
				Double quantia;
				try {
					quantia = Double.parseDouble(args[2]);
				} catch (NumberFormatException e) {
					p.sendMessage(prefix + config.getString("Mensagens.FormatoIncorreto"));
					return true;
				}

				addAlmas(player, quantia);
				Bukkit.getPlayer(player).sendMessage(prefix + "§aVocê recebeu §e" + quantia + "§a Almas.");
				p.sendMessage(prefix + "§aVocê adicionou §e" + quantia + "§a Almas para o jogador §e" + player);
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (Bukkit.getPlayer(args[1]) == null) {
					return true;
				}
				String player = Bukkit.getPlayer(args[1]).getName();
				Double quantia;
				try {
					quantia = Double.parseDouble(args[2]);
				} catch (NumberFormatException e) {
					p.sendMessage("§cFormato incorreto! use apenas numeros.");
					return true;
				}

				removeAlmas(player, quantia);
				Bukkit.getPlayer(player).sendMessage(prefix + "§aVocê perdeu §e" + quantia + "§a Almas.");
				p.sendMessage(prefix + "§aVocê removeu §e" + quantia + "§a Almas do jogador §e" + player);
			}

		}
		return false;
	}

}
