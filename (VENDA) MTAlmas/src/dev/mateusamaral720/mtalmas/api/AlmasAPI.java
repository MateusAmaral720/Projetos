package dev.mateusamaral720.mtalmas.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import dev.mateusamaral720.mtalmas.Main;

public class AlmasAPI extends MySQL {

	public static List<Entry<String, Integer>> valores;
	public static HashMap<String, Integer> contas = new HashMap<>();
	public static MTConfig almas = new MTConfig(Main.getPlugin(), "Almas.yml");
	public static MTConfig config = new MTConfig(Main.getPlugin(), "Configuracao.yml");

	public static String getPrefix() {
		return config.getString("Prefix").replace("&", "§");
	}

	public static String prefix = getPrefix();

	public static boolean MySqlEstaAtivado() {
		if (config.getBoolean("MySQL.ativado") == true) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean contains(String player) {
		if (MySqlEstaAtivado()) {
			PreparedStatement stm = null;
			try {
				stm = con.prepareStatement("SELECT * FROM `mtalmas` WHERE `player` = ?");
				stm.setString(1, player);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					return true;
				}
				return false;
			} catch (SQLException e) {
				return false;
			}
		} else {
			return config.contains("Almas." + player);
		}
	}

	public static void createPlayer(String player) {
		if (MySqlEstaAtivado()) {
			PreparedStatement stm = null;
			try {
				stm = con.prepareStatement("INSERT INTO `mtalmas`(`player`, `quantia`) VALUES (?,?)");
				stm.setString(1, player);
				stm.setDouble(2, 0);
				stm.executeUpdate();
				sc.sendMessage(prefix + "§aPlayer §f" + player + "§a criado com sucesso!");
			} catch (SQLException e) {
				sc.sendMessage(prefix + "§cNão foi possivel inserir o player: §f" + player + "§a no banco de dados!");
			}
		} else {
			setAlmas(player, 0.0);
		}
	}

	public static void setAlmas(String player, Double quantia) {
		if (MySqlEstaAtivado()) {
			PreparedStatement stm = null;
			try {
				stm = con.prepareStatement("UPDATE `mtalmas` SET `quantia` = ? WHERE `player` = ?");
				stm.setDouble(1, quantia);
				stm.setString(2, player);
				stm.executeUpdate();
			} catch (SQLException e) {
				sc.sendMessage(prefix + "§cNão foi possivel setar as almas do jogador");
			}
		} else {
			almas.set("Almas." + player, quantia);
			almas.saveConfig();
		}
	}

	public static Double getAlmas(String player) {
		if (MySqlEstaAtivado()) {
			PreparedStatement stm = null;
			try {
				stm = con.prepareStatement("SELECT * FROM `mtalmas` WHERE `player` = ?");
				stm.setString(1, player);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					return rs.getDouble("quantia");
				}
				return 0.0;
			} catch (SQLException e) {
				return 0.0;
			}
		} else {
			if (almas.contains("Almas." + player)) {
				return almas.getDouble("Almas." + player);
			} else {
				return 0.0;
			}
		}

	}

	public static void addAlmas(String player, Double quantia) {
		if (MySqlEstaAtivado()) {
			setAlmas(player, getAlmas(player) + quantia);
		} else {
			setAlmas(player, getAlmas(player) + quantia);
		}
	}

	public static void removeAlmas(String player, Double quantia) {
		if (MySqlEstaAtivado()) {
			setAlmas(player, getAlmas(player) - quantia);
		} else {
			setAlmas(player, getAlmas(player) - quantia);
		}
	}

	public static boolean containsAlmas(String player, Double quantia) {
		return getAlmas(player) >= quantia;
	}

	public static List<String> getTops(Player p) {
		if (MySqlEstaAtivado()) {
			PreparedStatement stm = null;
			List<String> tops = new ArrayList<String>();
			try {
				stm = con.prepareStatement("SELECT * FROM `mtalmas` ORDER BY `quantia` DESC");
				ResultSet rs = stm.executeQuery();
				int i = 0;
				while (rs.next()) {
					if (i <= 5) {
						i++;
						tops.add("     §3" + i + "º §a" + rs.getString("player") + ":§3 " + rs.getDouble("quantia"));
					}
				}
			} catch (SQLException e) {
				p.sendMessage(prefix + "§cNão foi possivel carregar o top almas");
			}
			return tops;
		} else {
			return null;
		}
	}

	public static void atualizarTop() {

		for (OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
			double cash = getAlmas(offline.getName());
			contas.put(offline.getName(), (int) cash);
			Stream<Entry<String, Integer>> streamOrdenada = contas.entrySet().stream()
					.sorted((x, y) -> y.getValue().compareTo(x.getValue()));
			valores = streamOrdenada.collect(Collectors.toList());
		}
	}

	public static void mostrarTop(Player p) {
		int id = 0;
		for (Entry<String, Integer> entrada : valores) {
			Integer valor = entrada.getValue();
			String player = entrada.getKey();
			p.sendMessage(config.getString("TopAlmas.Formato").replace("&", "§").replace("<posicao>", "" + id).replace("<nome>", player).replace("<valor>", String.valueOf(valor)));
			id++;
			if (id == config.getInt("TopAlmas.Tamanho")) {
				break;
			}
		}
	}
}
