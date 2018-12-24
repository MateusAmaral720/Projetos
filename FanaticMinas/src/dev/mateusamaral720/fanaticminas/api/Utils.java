package dev.mateusamaral720.fanaticminas.api;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import dev.mateusamaral720.fanaticminas.Principal;
import dev.mateusamaral720.fanaticrankup.utils.RankUpType;

public class Utils {

	public static HashMap<Player, Location> loc1 = new HashMap<>();
	public static HashMap<Player, Location> loc2 = new HashMap<>();
	public static String prefix = "§eFanaticMC §8» ";


	public static boolean check(Integer id){
		for (String s : Config.cnf.getStringList("IDs")){
			int ids = Integer.parseInt(s.split(":")[0]);
			if (ids == id){
				return true;
			}
		}
		return false;
	}

	public static Double getPrecoBloco(Integer id){
		for (String s : Config.cnf.getStringList("IDs")){
			int ids = Integer.parseInt(s.split(":")[0]);
			if (ids == id){
				return Double.parseDouble(s.split(":")[1]);
			}
		}
		return 0.0;
	}

	public static void regenerar(){
		Bukkit.getConsoleSender().sendMessage(prefix + "§cRegenerando Blocos...");
		for (Entry<Location, Material> p : Principal.blocks.entrySet()){
			p.getKey().getBlock().setType(p.getValue());
		}
	}
	

	
	public static String getFormat(double m) {
		BigDecimal valor = new BigDecimal(m);
		NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
		return nf.format(valor);
	}
	
	public static Double converterValorClasse(Player p, Double valorBruto, Double bonusVIP) {
		RankUpType classe = RankUpType.rank.get(p.getName());
		
		switch (classe) {
		case MisticoI:
			return ((valorBruto * 1.1) + bonusVIP);
		case MisticoII:
			return ((valorBruto * 1.2) + bonusVIP);
		case MisticoIII:
			return ((valorBruto * 1.3) + bonusVIP);
		case EliteI:
			return ((valorBruto * 1.4) + bonusVIP);
		case EliteII:
			return ((valorBruto * 1.5) + bonusVIP);
		case EliteIII:
			return ((valorBruto * 1.6) + bonusVIP);
		case CavaleiroI:
			return ((valorBruto * 1.7) + bonusVIP);
		case CavaleiroII:
			return ((valorBruto * 1.8) + bonusVIP);
		case CavaleiroIII:
			return ((valorBruto * 1.9) + bonusVIP);
		case DeusI:
			return ((valorBruto * 2.0) + bonusVIP);
		case DeusII:
			return ((valorBruto * 2.1) + bonusVIP);
		case DeusIII:
			return ((valorBruto * 2.2) + bonusVIP);
		case VeteranoI:
			return ((valorBruto * 2.3) + bonusVIP);
		case VeteranoII:
			return ((valorBruto * 2.4) + bonusVIP);
		case VeteranoIII:
			return ((valorBruto * 2.5) + bonusVIP);
		case PlatinumI:
			return ((valorBruto * 2.6) + bonusVIP);
		case PlatinumII:
			return ((valorBruto * 2.7) + bonusVIP);
		case PlatinumIII:
			return ((valorBruto * 2.8) + bonusVIP);
		case ChampionI:
			return ((valorBruto * 2.9) + bonusVIP);
		case ChampionII:
			return ((valorBruto * 3.0) + bonusVIP);
		case ChampionIII:
			return ((valorBruto * 3.1) + bonusVIP);
		case LendarioI:
			return ((valorBruto * 3.2) + bonusVIP);
		case LendarioII:
			return ((valorBruto * 3.3) + bonusVIP);
		case LendarioIII:
			return ((valorBruto * 3.4) + bonusVIP);
		case Armageddon:
			return ((valorBruto * 3.5) + bonusVIP);
	    default:
	    	return valorBruto;
		}
	}

}
