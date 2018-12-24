package dev.mateusamaral720.bartcash;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import dev.mateusamaral720.bartcash.api.CashAPI;
import dev.mateusamaral720.bartcash.comandos.CashCommand;
import dev.mateusamaral720.bartcash.comandos.ComprarItem;
import dev.mateusamaral720.bartcash.comandos.KeyCommand;

public class Main
  extends JavaPlugin
  implements Listener
{
	
	public static List<Entry<String, Integer>> valores;
	public HashMap<String, Integer> contas = new HashMap<>();

  
  public static Main getPlugin()
  {
    return (Main)getPlugin(Main.class);
  }
  
  public void onEnable()
  {
    CashAPI.cash.saveDefaultConfig();
    CashAPI.config.saveDefaultConfig();
    Bukkit.getPluginManager().registerEvents(this, this);

    new BukkitRunnable() {
		
		@Override
		public void run() {
			atualizarTop();
		}
	}.runTaskTimer(this, 0L, 120*20);
    atualizarTop();
    getCommand("novakey").setExecutor(new KeyCommand());
    getCommand("ativarkey").setExecutor(new KeyCommand());
    getCommand("cash").setExecutor(new CashCommand());
    getCommand("compraritem").setExecutor(new ComprarItem());
  }
  
  public void atualizarTop() {
		
			  for(OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
					int cash = CashAPI.getCash(offline);
					  contas.put(offline.getName(), cash);
					  Stream<Entry<String, Integer>> streamOrdenada = contas.entrySet().stream().sorted((x,y) -> y.getValue().compareTo(x.getValue()));
					  valores = streamOrdenada.collect(Collectors.toList());
				  }
  }
  
  public static void mostrarTop(Player p) {
	  int id = 1;
	  for(Entry<String, Integer> entrada : valores) {
		  Integer valor = entrada.getValue();
		  String player = entrada.getKey();
		  p.sendMessage("   §f" + id + "° §7" + player + " §8- §7" + valor);
		  id++;
		  if(id == 6) {
			  break;
		  }
	  }
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
  
    if (!CashAPI.cash.contains("Cash." + e.getPlayer().getName())) {
      CashAPI.createPlayer(e.getPlayer().getName());
    }
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent e)
  {
    Player p = e.getPlayer();
    if (CashAPI.getCash(p.getName()) < 0) {
      CashAPI.setCash(p.getName(), 0);
    }
  }
}
