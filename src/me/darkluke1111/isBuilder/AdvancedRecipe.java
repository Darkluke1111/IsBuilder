package me.darkluke1111.isBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
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
	private Set<String> structNames;
	private String name;

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
		structNames = new HashSet<>(list);
		this.name = name;

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
	public Set<String> getStructNames() {
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
	
	@Override
	public boolean equals(Object rSide) {
	    if(!(rSide instanceof AdvancedRecipe)) return false;
	    
	    AdvancedRecipe rRecipe = (AdvancedRecipe) rSide;
	    if(!IsBuilderUtils.recipiesAreEqual(this.getRecipe(), rRecipe.getRecipe())) return false;
	    
	    //if(!getStructNames().equals(rRecipe.getStructNames())) return false;
	    
        return true;    
	}
	
	@Override
	public int hashCode() {
	    return new HashCodeBuilder(5,11).append(recipe.getIngredientMap()).append(recipe.getShape()).toHashCode();
	}
	
	public String getName() {
		return name;
	}
}
