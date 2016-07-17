package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeBuildView extends View {
	
	ConfigItem[] ingredientItems = new ConfigItem[9];
	ConfigItem resultItem;
    
    public RecipeBuildView(RecipeBuildMenu containingMenu) {
    	super(containingMenu);
    	Material walls = Material.STAINED_GLASS_PANE;
        setItemColumn(3, new MenuItem(walls,this));
        setItemColumn(5, new MenuItem(walls,this));
        setItem(0, 4, new MenuItem(walls,this));
        setItem(2, 4, new MenuItem(walls,this));
        System.out.println(buildCraftingArea());
        
        setItem(2,8, new Button(Material.REDSTONE_BLOCK,(byte) 0, "Accept", "Accepts the selection", this, ButtonAction.SAVE_RECIPE));
    }
    
    public ShapedRecipe getRecipe() {
    	ShapedRecipe recipe = new ShapedRecipe(resultItem.getConfigItemStack());
    	for(ConfigItem item : ingredientItems) {
    		System.out.println(item.getConfigItemStack().getType());
    	}
    	return recipe;
    }
    
    public ConfigItem getResultItemStack() {
    	return resultItem;
    }
    
    public boolean buildCraftingArea() {
        for(int i = 0; i < ingredientItems.length; i++) {
        	ingredientItems[i] = new ConfigItem(this);
        }
        resultItem = new ConfigItem(this);
        
        return insertCraftingGrid(0, 0) && insertResult(1, 4);   
    }
    
    public boolean insertCraftingGrid(int row, int column) {
    	if(ingredientItems.length != 9) return false;
    	if(row + 3 > maxRows || column + 3 > maxColumns) return false;
    	if(row < 0 || column < 0) return false;
    	
    	ConfigItem item;
    	for(int i = 0; i < 3; i++) {
    		for(int j = 0; j < 3 ; j++) {
    			item = ingredientItems[3*i + j];
    			setItem(row + i, column + j, item);
    		}
    	}
    	return true;
    }
    
    public boolean insertResult(int row, int column) {
    	if(row < 0 || row >= maxRows) return false;
    	if(column < 0 || column >= maxColumns) return false;
    	setItem(row, column, resultItem);
    	return true;
    }

}
