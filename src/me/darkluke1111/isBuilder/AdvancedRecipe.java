package me.darkluke1111.isBuilder;

import org.bukkit.inventory.Recipe;

public class AdvancedRecipe {
	private Recipe recipe;
	private CraftingStructure struct;
	
	public AdvancedRecipe(Recipe recipe, CraftingStructure struct) {
		this.recipe = recipe;
		this.struct = struct;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public CraftingStructure getStruct() {
		return struct;
	}

}
