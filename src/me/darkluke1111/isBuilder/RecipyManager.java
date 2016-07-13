package me.darkluke1111.isBuilder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class RecipyManager implements Listener{
	
	private List<AdvancedRecipe> recipies = new ArrayList<>();
	private Plugin plugin;
	
	
	
	public RecipyManager(Plugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	public void addRecipy(AdvancedRecipe recipe) {
		recipies.add(recipe);
		plugin.getServer().addRecipe(recipe.getRecipe());
	}
	
	public void destroy() {
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler
	public void onCrafting(PrepareItemCraftEvent event) {
		for(AdvancedRecipe recipe : recipies) {
			if(recipiesAreEqual(recipe.getRecipe(),event.getRecipe())) {
				if(event.getInventory().getHolder() instanceof Player) {
					Player crafter = (Player) event.getInventory().getHolder();
					Location pos = crafter.getLocation().add(0, -1, 0);
					Block groundblock = pos.getBlock();
					if(groundblock.getType().equals(Material.WORKBENCH)) {
						if(recipe.getStruct().lookForStructure(pos)) {
							//Alles ok, Item wird gecraftet
						} else {
							//Die nötige Crafting Struktur ist nicht vorhanden
							event.getInventory().setResult(new ItemStack(Material.AIR));
							crafter.sendMessage(ChatColor.RED + "Deine Werkbank besitzt nicht die nötige Ausstattung!");
						}
					} else {
						//Der Spieler steht nicht auf der Werkbank
						event.getInventory().setResult(new ItemStack(Material.AIR));
						crafter.sendMessage(ChatColor.RED + "Du musst auf einer Werkbank stehen!");
					}
				}
			}
		}
	}
	
	/**
	 * Vergleicht 2 Rezepte aufgrund Name und 1. Lore-String der Ergebnisprodukte
	 * @param r1
	 * @param r2
	 * @return
	 */
	public boolean recipiesAreEqual(Recipe r1, Recipe r2) {
		ItemStack is1 = r1.getResult();
		ItemStack is2 = r2.getResult();
		if(is1.getType() == is2.getType()) {
			String lore1 = is1.getItemMeta().getLore().get(0);
			String lore2 = is2.getItemMeta().getLore().get(0);
			if(lore1.equals(lore2)) {
				return true;
			}
		}
		return false;
	}

}
