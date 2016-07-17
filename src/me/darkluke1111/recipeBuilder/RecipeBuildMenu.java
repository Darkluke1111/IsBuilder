package me.darkluke1111.recipeBuilder;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class RecipeBuildMenu implements Listener{
    
    public static final int INV_SIZE = 27;
   
    private Inventory inv = Bukkit.createInventory(null, INV_SIZE, "RecipeBuilder");    
    private View activeView;
    private Map<String,View> views = new HashMap<>();
    
    public RecipeBuildMenu(Plugin plugin) {
    	Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
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
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(!e.getInventory().equals(inv)) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        activeView.handleClick(e);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onButtonClick(ButtonPressedEvent e) {
    	if(e.getButton().actions.get(0) == ButtonAction.SAVE_CONFIG) {
    		//TODO
    	}
    }
}
