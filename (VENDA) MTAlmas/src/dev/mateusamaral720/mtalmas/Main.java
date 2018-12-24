package dev.mateusamaral720.mtalmas;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.mateusamaral720.mtalmas.api.AlmasAPI;
import dev.mateusamaral720.mtalmas.api.MySQL;
import dev.mateusamaral720.mtalmas.commands.AlmasCommand;
import dev.mateusamaral720.mtalmas.commands.ComprarItemAlmas;
import dev.mateusamaral720.mtalmas.events.AlmasEvents;

public class Main extends JavaPlugin {

	public static Main getPlugin() {
		  return getPlugin(Main.class);
		}

	public void onEnable() {
		AlmasAPI.config.saveDefaultConfig();
		if(!verifyLicense(AlmasAPI.config.getString("Licenca"), Bukkit.getIp(), Bukkit.getPort() +"")) {

				Bukkit.getConsoleSender().sendMessage("§cPlugin MT_Almas nao esta com a licenca ativada!");
				Bukkit.getPluginManager().disablePlugin(getPlugin());
			} else {
				AlmasAPI.atualizarTop();
				Bukkit.getPluginManager().registerEvents(new AlmasEvents(), this);
				getCommand("compraritemalmas").setExecutor(new ComprarItemAlmas());
				getCommand("almas").setExecutor(new AlmasCommand());
				Bukkit.getConsoleSender().sendMessage("§c-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				Bukkit.getConsoleSender().sendMessage("§aPlugin: MT_Almas (Premium)");
				Bukkit.getConsoleSender().sendMessage("§aVersao: 1.0");
				Bukkit.getConsoleSender().sendMessage("§aAutor: MateusAmaral720");
				Bukkit.getConsoleSender().sendMessage("§c-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				if (AlmasAPI.MySqlEstaAtivado()) {
					MySQL.open();
					MySQL.createTable();
				} else {
					AlmasAPI.almas.saveDefaultConfig();
				}
			}

	}

	public boolean verifyLicense(String key, String ip, String porta) {
        String urlloc = "http://mtplugins.000webhostapp.com/licencas/licencas.php?key=" + key+ "&ip=" + ip
                + "&porta=" + porta;
        try {
            URL url = new URL(urlloc);
            URLConnection openConnection = url.openConnection();
            openConnection.addRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            Scanner r = new Scanner(openConnection.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (r.hasNext()) {
                sb.append(r.next());
            }
            r.close();
            return sb.toString().equalsIgnoreCase("Permitido");
        } catch (IOException localIOException) {
            Bukkit.getConsoleSender().sendMessage("§aNao foi possivel ativar o plugin MT_Almas, licenca invalida!");
            return false;
        }
    }

	public void onDisable() {
		MySQL.close();
	}

}
