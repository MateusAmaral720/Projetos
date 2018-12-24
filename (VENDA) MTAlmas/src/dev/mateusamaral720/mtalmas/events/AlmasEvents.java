package dev.mateusamaral720.mtalmas.events;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import dev.mateusamaral720.mtalmas.api.AlmasAPI;

public class AlmasEvents extends AlmasAPI implements Listener{
	
	@EventHandler
	void evento(PlayerJoinEvent e) {
		if(!contains(e.getPlayer().getName())) {
			createPlayer(e.getPlayer().getName());
			setAlmas(e.getPlayer().getName(), 0.0);
		}
	}
	
	@EventHandler
	void evento(PlayerMoveEvent e) {
		if(getAlmas(e.getPlayer().getName()) < 0) {
			setAlmas(e.getPlayer().getName(), 0.0);
		}
	}
	
	
	
	public static Boolean percentChance(int chance) {
	    return Math.random() <= chance;
	}
	
	@EventHandler
	void evento(PlayerDeathEvent e) {
		if(percentChance(AlmasAPI.config.getInt("Porcentagem"))) {
			Player p = e.getEntity();
			String player = p.getKiller().getName();
			addAlmas(player, 1.0);
			p.getKiller().sendMessage(prefix + config.getString("VoceRecebeuAlmas").replace("&", "§").replace("<player>", p.getName()));
		} else {
			Player p = e.getEntity();
			String player = p.getKiller().getName();
			addAlmas(player, 0.0);
		}
	}

}
