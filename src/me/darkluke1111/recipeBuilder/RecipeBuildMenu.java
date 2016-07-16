package me.darkluke1111.recipeBuilder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class RecipeBuildMenu {
    
    public static final int INV_SIZE = 45;

    
    private Inventory inv = Bukkit.createInventory(null, INV_SIZE, "RecipeBuilder");
    
    private View startView;
    private View buildView;
    private View listView;
    
    public void open(Player p) {
        p.openInventory(inv);
    }
}
