package me.darkluke1111.isBuilder;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class AdvancedRecipe {
	private ShapedRecipe recipe;
	private CraftingStructure struct;
	
	public AdvancedRecipe(ItemStack result, CraftingStructure struct) {
		result = addLoreMarker(result);
		recipe = new ShapedRecipe(result);
		this.struct = struct;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public CraftingStructure getStruct() {
		return struct;
	}
	
	private ItemStack addLoreMarker(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList("Advanced"));
		item.setItemMeta(meta);
		return item;
	}
	
	public void shape(String row1, String row2, String row3) {
		recipe.shape(row1,row2,row3);
	}
	
	public void setIngredient(char key, Material ingredient) {
		recipe.setIngredient(key, ingredient);
	}

}
