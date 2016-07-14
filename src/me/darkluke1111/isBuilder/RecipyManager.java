package me.darkluke1111.isBuilder;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
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
	
	public Map<String,AdvancedRecipe> recipes = new HashMap<>();
	
	
	
	public RecipyManager(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void initRecipes(Plugin plugin,RecipeLoader rl) {
		Configuration config = plugin.getConfig();		
		ConfigurationSection confSec = config.getConfigurationSection("recipes");
		AdvancedRecipe recipe;
				
		for(String key : confSec.getKeys(false)) {
			recipe = new AdvancedRecipe(key, rl.getResultMat(key), rl.getresultAmount(key), rl.getPattern(key), rl.getIngredients(key), rl.getCraftStructNames(key));
			plugin.getServer().addRecipe(recipe.getRecipe());
			recipes.put(key, recipe);
		}
	}
	
	public void destroy() {
		HandlerList.unregisterAll(this);
	}

	@EventHandler
	public void onCrafting(PrepareItemCraftEvent event) {
		for (AdvancedRecipe recipe : recipes.values()) {
			if (recipiesAreEqual(recipe.getRecipe(), event.getRecipe())) {
				if (event.getInventory().getHolder() instanceof Player) {
					Player crafter = (Player) event.getInventory().getHolder();
					Location pos = crafter.getLocation();

					if (matchStructures(recipe, pos)) {
						// Alles ok, Item wird gecraftet
					} else {
						// Die nötige Crafting Struktur ist nicht vorhanden
						event.getInventory().setResult(new ItemStack(Material.AIR));
						crafter.sendMessage(ChatColor.RED + "Deine Werkbank besitzt nicht die nötige Ausstattung!");
					}

				}
			}
		}
	}
	
	public boolean matchStructures(AdvancedRecipe recipe, Location pos) {
		for(String structName : recipe.getStructNames()) {
			if(CraftingStructure.lookForStructure(pos, CraftingStructure.getStructureForName(structName))) {
				return true;
			}
		}
		return false;
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
