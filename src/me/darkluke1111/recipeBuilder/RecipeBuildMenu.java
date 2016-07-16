package me.darkluke1111.recipeBuilder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class RecipeBuildMenu {

    
    private Inventory inv = Bukkit.createInventory(null, 27, "RecipeBuilder");
    
    private View startView;
    private View buildView;
    private View listView;
}
