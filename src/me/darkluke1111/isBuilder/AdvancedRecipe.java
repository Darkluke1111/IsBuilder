package me.darkluke1111.isBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;


public class AdvancedRecipe {
	
	public static Map<String,AdvancedRecipe> recipes;

	
	private ShapedRecipe recipe;
	private List<String> structNames;
	
	public static void initRecipes(Plugin plugin,RecipeLoader rl) {
		Configuration config = plugin.getConfig();		
		ConfigurationSection confSec = config.getConfigurationSection("recipes");
				
		for(String key : confSec.getKeys(false)) {
			
			recipes.put(key, new AdvancedRecipe(key, rl.getResultMat(key), rl.getresultAmount(key), rl.getPattern(key), rl.getIngredients(key), rl.getCraftStructNames(key)));
			
		}
	}
	
	AdvancedRecipe(String name, Material resultMat,int amount, String[] pattern, Map<Character,Material> ingredients, List<String> list) {
		
		ItemStack result = new ItemStack(resultMat);
		result = addLoreMarker(result);
		recipe = new ShapedRecipe(result);
		
		recipe.shape(pattern[0],pattern[1],pattern[3]);
		
		for(Entry<Character, Material> entry : ingredients.entrySet()) {
			recipe.setIngredient(entry.getKey(), entry.getValue());
		}
		structNames = list;
		
		
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public List<String> getStructNames() {
		return structNames;
	}
	
	private ItemStack addLoreMarker(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList("Advanced"));
		item.setItemMeta(meta);
		return item;
	}
}
