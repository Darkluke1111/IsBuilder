package me.darkluke1111.isBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public class RecipeLoader {
	Configuration config;
	
	public RecipeLoader(Configuration config) {
		this.config = config;
	}
	
	public Set<AdvancedRecipe> loadRecipes() {
		Set<AdvancedRecipe> recipes = new HashSet<>();		
		ConfigurationSection confSec = config.getConfigurationSection("recipes");
		AdvancedRecipe recipe;
				
		for(String key : confSec.getKeys(false)) {
			recipe = new AdvancedRecipe(key, getResultMat(key), getresultAmount(key), getPattern(key), getIngredients(key), getCraftStructNames(key));
			recipes.add(recipe);
		}
		return recipes;
	}

	public Material getResultMat(String name) {
		String materialName = config.getString("recipes."+ name + ".resultMat");
		Material mat = Material.getMaterial(materialName);
//		System.out.println(mat.toString());
		return mat;
	}
	
	public int getresultAmount(String name) {
		int amount = config.getInt("recipes."+ name + ".resultAmount");
//		System.out.println(amount);
		return amount;
	}
	
	public String[] getPattern(String name) {
		String[] craftPattern = config.getString("recipes."+ name + ".craftPattern").split("-");
//		System.out.println(craftPattern.toString());
		return craftPattern;
	}
	
	public Map<Character,Material> getIngredients(String name) {
		Map<Character,Material> ingredients = new HashMap<>();
		Set<String> set = config.getConfigurationSection("recipes."+ name + ".ingredients").getKeys(false);
		for(String letter : set) {
			String matName = config.getString("recipes."+ name + ".ingredients." + letter);
			ingredients.put(letter.charAt(0), Material.getMaterial(matName));
		}
//		System.out.println(ingredients.toString());
		return ingredients;
	}
	
	public List<String> getCraftStructNames(String name) {
		List<?> list = config.getList("recipes."+ name + ".craftStructNames");
		List<String> structs = new ArrayList<>();
		for(Object obj : list) {
			structs.add((String)obj);
		}
		return structs;
	}
}
