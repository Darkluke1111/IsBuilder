package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeBuildView extends View {
	
	ConfigItem[] ingredientItems = new ConfigItem[9];
	ConfigItem resultItem;
    
    public RecipeBuildView() {
    	Material walls = Material.STAINED_GLASS_PANE;
        setItemColumn(3, new MenuItem(walls,this));
        setItemColumn(5, new MenuItem(walls,this));
        setItem(0, 4, new MenuItem(walls,this));
        setItem(2, 4, new MenuItem(walls,this));
        
        for(MenuItem item : ingredientItems) {
        	item = new ConfigItem(this);
        }
        resultItem = new ConfigItem(this);
        
        
        setItem(1,4, new ConfigItem(this));
        
        setItem(3,9, new Button(Material.REDSTONE_BLOCK,(byte) 0, "Accept", "Accepts the selection", this, ButtonAction.SAVE_RECIPE));
    }
    
    public ShapedRecipe getRecipe() {
    	ShapedRecipe recipe = new ShapedRecipe(result);
    }
    
    public ItemStack getResultItemStack() {
    	return items[]
    }
    
    public boolean insertCraftingGrid(int row, int column) {
    	if(ingredientItems.length != 9) return false;
    	if()
    }

}
