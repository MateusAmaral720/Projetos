package dev.mateusamaral720.bartcash.api;

import org.bukkit.OfflinePlayer;

import dev.mateusamaral720.bartcash.Main;

public class CashAPI {
	public static String prefix = "§eBartMC §8» ";
	  public static MTConfig cash = new MTConfig(Main.getPlugin(), "Cash.yml");
	  public static MTConfig config = new MTConfig(Main.getPlugin(), "Configuracao.yml");

	public static void setCash(String p, double valor) {
		cash.set("Cash." + p, Double.valueOf(valor));
		cash.saveConfig();
	}

	public static int getCash(OfflinePlayer p) {
		return cash.getInt("Cash." + p.getName());
	}

	public static int getCash(String p) {
		return cash.getInt("Cash." + p);
	}

	public static void addCash(String p, double valor) {
		setCash(p, getCash(p) + valor);
	}

	public static void removeCash(String p, double valor) {
		setCash(p, getCash(p) - valor);
	}
	
	

	public static boolean containsCash(String p, double valor) {
		if (getCash(p) >= valor) {
			return true;
		}
		return false;
	}

	public static void createPlayer(String p) {
		setCash(p, 0);
	}

	public static void pay(String enviador, String recebedor, double valor) {
		if (containsCash(enviador, valor)) {
			addCash(recebedor, valor);
			removeCash(enviador, valor);
		} else {
			removeCash(enviador, 0);
			addCash(recebedor, 0);
		}
	}
}
