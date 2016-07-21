package me.darkluke1111.recipeBuilder;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.darkluke1111.isBuilder.AdvancedRecipe;
import me.darkluke1111.isBuilder.IsBuilder;

public class RecipeBuildMenu implements Listener{
    
    public static final int INV_SIZE = 27;
   
    private Inventory inv = Bukkit.createInventory(null, INV_SIZE, "RecipeBuilder");    
    private View activeView;
    private Map<String,View> views = new HashMap<>();
    private IsBuilder plugin;
    
    public RecipeBuildMenu(IsBuilder builder) {
    	Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		views.put("Build", new RecipeBuildView(this));
		this.plugin = builder;
	}
    
    public void open(Player p) {
        p.openInventory(inv);
        showView(views.get("Build"));
    }
    
    public void showView(View view) {
        inv.setContents(view.getIcons());
        setActiveView(view);
    }
    
    public void showViewItem(int slot) {
    	inv.setItem(slot, activeView.getItem(slot).getIcon());
    }
    
    private void setActiveView(View view) {
        activeView = view;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
    	System.out.println(e.getRawSlot());
        if(!e.getInventory().equals(inv)) return;
        if(!(e.getClick()== ClickType.LEFT) && !(e.getClick() == ClickType.RIGHT)) {
        	e.setCancelled(true);
        	return;
        }
		if(e.getRawSlot() >= INV_SIZE) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        activeView.handleClick(e);
    }
    
    @EventHandler
    public void onButtonClick(ButtonPressedEvent e) {
    	if(e.getButton().action == ButtonAction.SAVE_RECIPE) {
    		if(!(activeView instanceof RecipeBuildView)) return;
    		RecipeBuildView view = (RecipeBuildView) activeView;
    		AdvancedRecipe recipe= view.getRecipe();
    		//plugin.getRl().writeRecipe(recipe);
    		//TODO
    	}
    }
}
