package dev.mateusamaral720.mtalmas.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import dev.mateusamaral720.mtalmas.Main;

public class MySQL {

	protected static Connection con = null;
	public static ConsoleCommandSender sc = Bukkit.getConsoleSender();

	public static void open() {
		String type = "jdbc:mysql://";
		String host = AlmasAPI.config.getString("MySQL.host");
		String database = AlmasAPI.config.getString("MySQL.database");
		String url = type + host + ":3306/" + database;
		String user =  AlmasAPI.config.getString("MySQL.user");
		String password =  AlmasAPI.config.getString("MySQL.password");
		try {
			con = DriverManager.getConnection(url, user, password);
			sc.sendMessage(AlmasAPI.prefix + "§aConexão com MySQL aberta!");
		} catch (SQLException e) {
			sc.sendMessage(AlmasAPI.prefix + "§cConexão com MySQL não foi possivel!");
			Main.getPlugin().getPluginLoader().disablePlugin(Main.getPlugin());
		}
	}

	public static void close() {
		if (con != null) {
			try {
				con.close();
				sc.sendMessage(AlmasAPI.prefix + "§aConexão fechada com sucesso!");
			} catch (SQLException e) {
				sc.sendMessage(AlmasAPI.prefix + "§cNão foi possivel fechar a conexão!");
			}
		}
	}

	public static void createTable() {
		if (con != null) {
			PreparedStatement stm = null;
			try {
				stm = con.prepareStatement(
						"CREATE TABLE IF NOT EXISTS `mtalmas` (`id` INT NOT NULL AUTO_INCREMENT,`player` VARCHAR(24) NULL,`quantia` DOUBLE NULL,PRIMARY KEY (`id`));");
				stm.executeUpdate();
				sc.sendMessage(AlmasAPI.prefix + "§aTabela carregada");
			} catch (SQLException e) {
				sc.sendMessage(AlmasAPI.prefix + "§cNão foi possivel carregar a tabela");
			}
		}
	}

}