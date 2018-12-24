package dev.mateusamaral720.fanaticminas.api;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import dev.mateusamaral720.fanaticminas.Main;
import net.milkbowl.vault.economy.Economy;

public class Money {
	   public static Economy economia = null;

	   public static void register() {
	      ConsoleCommandSender sc = ((Main)Main.getPlugin(Main.class)).getServer().getConsoleSender();
	      if (!setupEconomy()) {
	         sc.sendMessage("§cFalta o Vault ou Iconomy");
	         ((Main)Main.getPlugin(Main.class)).getServer().getPluginManager().disablePlugin(Main.getPlugin(Main.class));
	      }
	   }

	   public static boolean setupEconomy() {
	      if (((Main)Main.getPlugin(Main.class)).getServer().getPluginManager().getPlugin("Vault") == null) {
	         return false;
	      } else {
	         RegisteredServiceProvider<Economy> rsp = ((Main)Main.getPlugin(Main.class)).getServer().getServicesManager().getRegistration(Economy.class);
	         if (rsp == null) {
	            return false;
	         } else {
	            economia = (Economy)rsp.getProvider();
	            return economia != null;
	         }
	      }
	   }

	   public static Double get(Player p) {
	      return economia.getBalance(p);
	   }

	   public static void add(Player p, double valor) {
	      economia.depositPlayer(p, valor);
	   }

	   public static void retirar(Player p, Double valor) {
	      economia.withdrawPlayer(p, valor);
	   }

	   public static boolean contains(Player p, Double valor) {
	      return economia.getBalance(p) >= valor;
	   }
	}
