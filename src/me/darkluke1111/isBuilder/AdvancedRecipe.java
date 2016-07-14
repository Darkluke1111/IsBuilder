package me.darkluke1111.isBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;


public class AdvancedRecipe {
	
	private ShapedRecipe recipe;
	private List<String> structNames;
		
	AdvancedRecipe(String name, Material resultMat,int amount, String[] pattern, Map<Character,Material> ingredients, List<String> list) {
		
		ItemStack result = new ItemStack(resultMat);
		result = addLoreMarker(result);
		recipe = new ShapedRecipe(result);
		
		recipe.shape(pattern[0],pattern[1],pattern[2]);
		
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
