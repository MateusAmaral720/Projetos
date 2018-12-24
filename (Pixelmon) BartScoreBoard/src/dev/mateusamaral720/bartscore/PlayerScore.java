package dev.mateusamaral720.bartscore;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import dev.mateusamaral720.bartcash.api.CashAPI;
import dev.mateusamaral720.bartscore.api.Money;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerScore implements CommandExecutor, Listener {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<Player, Boolean> scoreboard = new HashMap();



	public static void load() {
		(new BukkitRunnable() {
			@SuppressWarnings("rawtypes")
			public void run() {
				Iterator var2 = Bukkit.getOnlinePlayers().iterator();

				while(var2.hasNext()) {
					Player players = (Player)var2.next();
					PlayerScore.update(players);
				}

			}
		}).runTaskTimer(Main.getPlugin(), 0L, 20L);
	}

	@EventHandler
	void evento(PlayerJoinEvent e) {
		if (!scoreboard.containsKey(e.getPlayer())) {
			scoreboard.put(e.getPlayer(), true);
		}

		build(e.getPlayer());
	}

	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (cmd.getName().equalsIgnoreCase("score") && sender instanceof Player) {
			Player p = (Player)sender;
			if (scoreboard.containsKey(p)) {
				if ((Boolean)scoreboard.get(p)) {
					p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					p.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.ComandoScoreDesabilitada").replaceAll("&", "§"));
					scoreboard.remove(p);
					scoreboard.put(p, false);
				} else {
					scoreboard.remove(p);
					scoreboard.put(p, true);
					p.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.ComandoScoreHabilitada").replaceAll("&", "§"));
					build(p);
				}
			} else {
				p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				p.sendMessage(Main.getPlugin().getConfig().getString("Mensagens.ComandoScoreDesabilitada").replaceAll("&", "§"));
				scoreboard.put(p, false);
			}
		}
		return false;
	}

	public static void build(Player p) {
		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective obj = sb.registerNewObjective("score", "dummy");
		obj.setDisplayName("§6BART MC");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		LineAdder line = new LineAdder(sb, obj);
		line.addLine("", "§a", "", 13);
		line.addLine(" §e", "Cargo: ", "§bLoading...", 11);
		line.addLine("", "§b", "", 10);
		line.addLine(" §e", "Saldo: §b", "§bLoading...", 9);
		line.addLine(" §e", "Cash: §b", "§e0", 8);
		line.addLine("", "§6", "", 7);
		if (SimpleClans.getInstance().getClanManager().getClanPlayer(p) != null) {
			line.addLine("   §e", "Clã: §b", "§bLoading...", 6);
			line.addLine("   §e", "  Online: §b", "§bLoading...", 5);
			line.addLine("", "§d", "", 4);
		}
		line.addLine(" §e", "Jogadores: §b", "§bLoading...", 3);
		line.addLine(" ", "§2", "", 2);
		line.addLine(" §6","   www.bart","mc.tk", 1);
		p.setScoreboard(sb);
	}

	@SuppressWarnings("deprecation")
	public static void update(Player p) {
		if (scoreboard.containsKey(p) && (Boolean)scoreboard.get(p)) {
			Scoreboard sb = p.getScoreboard();
			if (sb.getObjective("score") != null) {
				double valor = Money.get(p);
				NumberFormat formato = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
				String moneyf = formato.format(valor);

				if (SimpleClans.getInstance().getClanManager().getClanPlayer(p) != null) {
					LineAdder line = new LineAdder(sb, sb.getObjective("score"));
					if (sb.getTeam("line6") == null) {
						line.addLine("   §e", "Clã: §b", "§bLoading...", 6);
					}

					if (sb.getTeam("line5") == null) {
						line.addLine("   §e", "  Online: §b", "§bLoading...", 5);
					}


					if (sb.getTeam("line4") == null) {
						line.addLine("", "§d", "", 4);
					}

					Team tag = sb.getTeam("line6");
					tag.setSuffix("§8(" + SimpleClans.getInstance().getClanManager().getClanPlayer(p).getClan().getColorTag() + "§8)");

					Team member = sb.getTeam("line5");
					member.setSuffix(SimpleClans.getInstance().getClanManager().getClanPlayer(p).getClan().getOnlineMembers().size() + "");

				} else {									
					if (sb.getTeam("line6") != null) {
						sb.getTeam("line6").unregister();
					}

					if (sb.getTeam("line5") != null) {
						sb.getTeam("line5").unregister();
					}
					
					if (sb.getTeam("line4") != null) {
						sb.getTeam("line4").unregister();
					}


					sb.resetScores("Clã: §b");
					sb.resetScores("  Online: §b");
					sb.resetScores("§d");

				}

				if (moneyf.length() == 17 || moneyf.length() == 18) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Bilhões";
				}

				if (moneyf.length() == 19 || moneyf.length() == 20 || moneyf.length() == 21 || moneyf.length() == 22) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Trilhões";
				}

				if (moneyf.length() == 23 || moneyf.length() == 24 || moneyf.length() == 25 || moneyf.length() == 26) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Quadrilhões";
				}

				if (moneyf.length() == 27 || moneyf.length() == 28 || moneyf.length() == 29 || moneyf.length() == 30) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Quintilhões";
				}

				if (moneyf.length() == 31 || moneyf.length() == 32 || moneyf.length() == 33 || moneyf.length() == 34) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Sextilhões";
				}

				if (moneyf.length() == 35 || moneyf.length() == 36 || moneyf.length() == 37 || moneyf.length() == 38) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Septilhões";
				}

				if (moneyf.length() == 39 || moneyf.length() == 40 || moneyf.length() == 41 || moneyf.length() == 42) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Octilhões";
				}

				if (moneyf.length() == 43 || moneyf.length() == 44 || moneyf.length() == 45 || moneyf.length() == 46) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Nonilhões";
				}

				if (moneyf.length() == 47 || moneyf.length() == 48 || moneyf.length() == 49 || moneyf.length() == 50) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Decilhões";
				}

				if (moneyf.length() == 51 || moneyf.length() == 52 || moneyf.length() == 53 || moneyf.length() == 54) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Undecilhões";
				}

				if (moneyf.length() == 55 || moneyf.length() == 56 || moneyf.length() == 57 || moneyf.length() == 58) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Duodecilhões";
				}

				if (moneyf.length() == 59 || moneyf.length() == 60 || moneyf.length() == 61 || moneyf.length() == 62) {
					moneyf = moneyf.substring(0, 2) + moneyf.substring(2, 3) + " Tredecilhões";
				}

				if (moneyf.length() > 62) {
					moneyf = "§cIndisponível";
				}

				Team money = sb.getTeam("line9");
				money.setSuffix(moneyf);
				
				Team cash = sb.getTeam("line8");
				cash.setSuffix(String.valueOf(CashAPI.getCash(p.getName())));
				
				Team grupo = sb.getTeam("line11");
				grupo.setSuffix(PermissionsEx.getUser(p).getGroups()[0].getPrefix().replace("&", "§"));

				Team jogadores = sb.getTeam("line3");
				jogadores.setSuffix(String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
			}

		}
	}
}