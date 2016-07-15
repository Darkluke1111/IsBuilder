package me.darkluke1111.isBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.darkluke1111.isBuilder.RecipeLoader.MaterialCompound;

/**
 * Instances represent advanced recipes, while wrapping a Bukkit ShapedRecipe
 * instance
 * 
 * @author Lukas
 *
 */
public class AdvancedRecipe {

	private ShapedRecipe recipe;
	private List<String> structNames;

	/**
	 * Constructor builds Shapedrecipe and wraps it together with a list of
	 * crafting structures
	 * 
	 * @param name
	 * @param resultMat
	 * @param amount
	 * @param pattern
	 * @param ingredients
	 * @param list
	 */
	AdvancedRecipe(String name, MaterialCompound resultMat, int amount, String[] pattern, Map<Character, MaterialCompound> ingredients,
			List<String> list) {

		ItemStack result = new ItemStack(resultMat.mat,1,resultMat.data);
		result = addLoreMarker(result);
		recipe = new ShapedRecipe(result);
		System.out.println(result.toString());

		recipe.shape(pattern[0], pattern[1], pattern[2]);

		for (Entry<Character, MaterialCompound> entry : ingredients.entrySet()) {
			recipe.setIngredient(entry.getKey(),new MaterialData(entry.getValue().mat,entry.getValue().data));
		}
		structNames = list;

	}

	/**
	 * Returns the wraped ShapedRecipe
	 * 
	 * @return
	 */
	public ShapedRecipe getRecipe() {
		return recipe;
	}

	/**
	 * Returns crafting structures
	 * 
	 * @return
	 */
	public List<String> getStructNames() {
		return structNames;
	}

	/**
	 * Adds "Advanced" to the item lore to mark it as a result of an advanced
	 * recipe
	 * 
	 * @param item
	 * @return
	 */
	private ItemStack addLoreMarker(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList("Advanced"));
		item.setItemMeta(meta);
		return item;
	}
}
