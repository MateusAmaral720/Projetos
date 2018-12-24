package dev.mateusamaral720.fanaticminas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import dev.mateusamaral720.fanaticminas.api.ActionBarAPI;
import dev.mateusamaral720.fanaticminas.api.Money;
import dev.mateusamaral720.fanaticminas.api.Utils;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Principal implements Listener {

	public static List<Player> jogadores = new ArrayList<>();
	public static String prefix = "§eFanaticMC §8» ";



	public static HashMap<Location, Material> blocks = new HashMap<>();

	
	@SuppressWarnings("deprecation")
	@EventHandler
	void evento(BlockBreakEvent e) {
			Player p = e.getPlayer();
			if(p.getWorld().getName().equalsIgnoreCase("minas")) {
				
			if (p.isOp()) {
				if (p.getGameMode() == GameMode.CREATIVE) {
					return;
				}
			}
			Block bloco = e.getBlock();
			Location loc = bloco.getLocation();
				if (Utils.check(bloco.getTypeId())) {
					e.setCancelled(true);
					Double valor = Utils.getPrecoBloco(bloco.getTypeId()).doubleValue();
					PermissionGroup grupo = PermissionsEx.getUser(p.getName()).getGroups()[0];
					if (grupo.getName().equals("Youtuber+") || grupo.getName().equals("Youtuber")) {
						Money.add(p, Utils.converterValorClasse(p, valor, valor * 1.4));
						ActionBarAPI.sendActionBar(p, "§2($) §aVocê recebeu +§2$§a"
								+ Utils.getFormat(Utils.converterValorClasse(p, valor, valor * 1.4)) + " §b(Bônus YT) §apor quebrar este bloco.", 3*20);
					} else if (grupo.getName().equals("Warrior")) {
						Money.add(p, Utils.converterValorClasse(p, valor, valor * 1.3));
						ActionBarAPI.sendActionBar(p, "§2($) §aVocê recebeu +§2$§a"
								+ Utils.getFormat(Utils.converterValorClasse(p, valor, valor * 1.3)) + " §b(Bônus VIP) §apor quebrar este bloco.", 3*20);
					} else if (grupo.getName().equals("Runer")) {
						Money.add(p, Utils.converterValorClasse(p, valor, valor * 1.2));
						ActionBarAPI.sendActionBar(p, "§2($) §aVocê recebeu +§2$§a"
								+ Utils.getFormat(Utils.converterValorClasse(p, valor, valor * 1.2)) + " §b(Bônus VIP) §apor quebrar este bloco.", 3*20);
					} else if (grupo.getName().equals("Mage") || grupo.getName().equals("MiniYT")) {
						Money.add(p, Utils.converterValorClasse(p, valor, valor * 1.1));
						ActionBarAPI.sendActionBar(p, "§2($) §aVocê recebeu +§2$§a"
								+ Utils.getFormat(Utils.converterValorClasse(p, valor, valor * 1.1)) + " §b(Bônus VIP) §apor quebrar este bloco.", 3*20);
					} else {
						Money.add(p, Utils.converterValorClasse(p, valor, valor * 1.4));
						ActionBarAPI.sendActionBar(p, "§2($) §aVocê recebeu +§2$§a"
								+ Utils.getFormat(Utils.converterValorClasse(p, valor, 0.0)) + " §apor quebrar este bloco.", 3*20);
					}
					blocks.put(loc, bloco.getType());
					bloco.setType(Material.COBBLESTONE);
					e.getBlock().getDrops().clear();
					new BukkitRunnable() {
						@Override
						public void run() {
							bloco.setType(blocks.get(loc));
							blocks.remove(loc);
							loc.getWorld().playEffect(loc.add(0, 1, 0), Effect.SMOKE, 1);
							for (Entity players : loc.getWorld().getNearbyEntities(loc, 5.0d, 5.0D, 5.0D)) {
								if (players instanceof Player) {
									Player pl = (Player) players;
									pl.playSound(pl.getLocation(), Sound.CHICKEN_EGG_POP, 0.5F, 1F);
								}
							}
						}
					}.runTaskLater(Main.getPlugin(), 15 * 20);
				} else {
					e.setCancelled(true);
					p.sendMessage(prefix + "§cVocê não pode quebrar este bloco na mina.");
				}
			} else {
				e.setCancelled(false);
			}
	}



	@EventHandler
	void evento(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(p.getWorld().getName().equalsIgnoreCase("minas")) {
			if(!jogadores.contains(p)) {
				jogadores.add(p);
				}
		} else {
			
		}
	}
	@EventHandler
	void evento(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (jogadores.contains(p)) {
			jogadores.remove(p);
		}
	}

	@EventHandler
	void evento(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (jogadores.contains(p)) {
				e.setCancelled(true);
			}
		}
	}


	@EventHandler
	void evento(PlayerChangedWorldEvent e) {
		if(e.getFrom().getName().equalsIgnoreCase("minas")) {
			jogadores.remove(e.getPlayer());
		} else if(e.getPlayer().getWorld().getName().equalsIgnoreCase("")) {
			jogadores.add(e.getPlayer());
		}
	}

	@EventHandler
	void evento(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (jogadores.contains(p)) {
			String msg = e.getMessage();
			if (msg.contains(" ")) {
				msg = msg.split(" ")[0];
			}
			if (msg.equalsIgnoreCase("/spawn") || msg.equalsIgnoreCase("/g") || msg.equalsIgnoreCase("/tell")
					|| msg.equalsIgnoreCase("/money") || msg.equalsIgnoreCase("/enderchest")) {
			} else {
				p.sendMessage("");
				p.sendMessage(" §f• §cO Comando que você digitou está bloqueado na mina!");
				p.sendMessage(" §f• §cPara sair da mina, digite /spawn.");
				p.sendMessage("");
				e.setCancelled(true);
				return;
			}
			if (msg.equalsIgnoreCase("/spawn")) {
				jogadores.remove(p);
			}
		}
	}

}
