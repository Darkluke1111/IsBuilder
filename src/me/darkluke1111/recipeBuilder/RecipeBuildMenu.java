package me.darkluke1111.recipeBuilder;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class RecipeBuildMenu implements Listener{
    
    public static final int INV_SIZE = 27;
   
    private Inventory inv = Bukkit.createInventory(null, INV_SIZE, "RecipeBuilder");    
    private View activeView;
    private Map<String,View> views = new HashMap<>();
    
    public RecipeBuildMenu() {
		views.put("Build", new RecipeBuildView());
	}
    
    public void open(Player p) {
        p.openInventory(inv);
        showView(views.get("Build"));
    }
    
    public void showView(View view) {
        inv.setContents(view.getIcons());
        setActiveView(view);
    }
    
    private void setActiveView(View view) {
        activeView = view;
    }
    
    public void onInventoryClick(InventoryClickEvent e) {
        if(!e.getInventory().equals(inv)) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        activeView.handleClick(e.getSlot(), p);
    }
}
