package me.darkluke1111.isBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class RecipeLoader {
	Plugin plugin;
	
	public RecipeLoader(Plugin plugin) {
		this.plugin = plugin;
	}

	public Material getResultMat(String name) {
		String materialName = plugin.getConfig().getString("recipes."+ name + ".resultMat");
		Material mat = Material.getMaterial(materialName);
//		System.out.println(mat.toString());
		return mat;
	}
	
	public int getresultAmount(String name) {
		int amount = plugin.getConfig().getInt("recipes."+ name + ".resultAmount");
//		System.out.println(amount);
		return amount;
	}
	
	public String[] getPattern(String name) {
		String[] craftPattern = plugin.getConfig().getString("recipes."+ name + ".craftPattern").split("-");
//		System.out.println(craftPattern.toString());
		return craftPattern;
	}
	
	public Map<Character,Material> getIngredients(String name) {
		Map<Character,Material> ingredients = new HashMap<>();
		Set<String> set = plugin.getConfig().getConfigurationSection("recipes."+ name + ".ingredients").getKeys(false);
		for(String letter : set) {
			String matName = plugin.getConfig().getString("recipes."+ name + ".ingredients." + letter);
			ingredients.put(letter.charAt(0), Material.getMaterial(matName));
		}
//		System.out.println(ingredients.toString());
		return ingredients;
	}
	
	public List<String> getCraftStructNames(String name) {
		List<?> list = plugin.getConfig().getList("recipes."+ name + ".craftStructNames");
		List<String> structs = new ArrayList<>();
		for(Object obj : list) {
			structs.add((String)obj);
		}
		return structs;
	}
}
