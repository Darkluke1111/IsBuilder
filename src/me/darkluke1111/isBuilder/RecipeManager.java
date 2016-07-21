package me.darkluke1111.isBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

/**
 * Handles Crafting with advanced recipes AdvancedRecipe Instances must be added
 * to be managed.
 * 
 * @author Lukas
 *
 */
public class RecipeManager implements Listener {

	/**
	 * Stores all added advanced recipes
	 * 
	 */
	private Set<AdvancedRecipe> recipes = new HashSet<>();

	private Map<String, CraftingStructure> structures = new HashMap<>();

	/**
	 * Constructor
	 * 
	 * @param plugin
	 */
	public RecipeManager(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public Set<AdvancedRecipe> getRecipes() {
		return recipes;
	}

	/**
	 * Unregisters all EventHandlers in the instance. Should be called when the
	 * RecipeManager Instance is not used anymore. (e.g. in onDisable() of main
	 * plugin class)
	 * 
	 */
	public void destroy() {
		HandlerList.unregisterAll(this);
	}

	/**
	 * Adds a set of advanced recipes to the manager
	 * 
	 * @param set
	 */
	public void addRecipes(Set<AdvancedRecipe> set) {
	    for(AdvancedRecipe aRecipe : set) {
	        aRecipe.getRecipe().getResult().getType().toString();
	        recipes.add(aRecipe);
	        
			Bukkit.getServer().addRecipe(aRecipe.getRecipe());
		}
		
	}

	/**
	 * Adds a map for crafting structures to the manager
	 * 
	 * @param map
	 */
	public void addStructures(Map<String, CraftingStructure> map) {
		structures.putAll(map);
	}

	/**
	 * Adds one advanced recipe to the manager
	 * 
	 * @param recipe
	 */
	public void addRecipe(AdvancedRecipe recipe) {
		Bukkit.getServer().addRecipe(recipe.getRecipe());
		recipes.add(recipe);
	}

	/**
	 * Is called from the PrepareItemCraftEvent to handle advanced crafting
	 * 
	 * @param event
	 */
	@EventHandler
	public void onCrafting(PrepareItemCraftEvent event) {
		for (AdvancedRecipe recipe : recipes) {
			if (IsBuilderUtils.recipiesAreEqual(recipe.getRecipe(), event.getRecipe())) {
				if (event.getInventory().getHolder() instanceof Player) {
					Player crafter = (Player) event.getInventory().getHolder();
					Location pos = crafter.getLocation();

					if (matchCraftingStructures(recipe, pos)) {
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

	/**
	 * Returns true if a required crafting structure is present at the Location
	 * 
	 * @param pos
	 * @return
	 */
	public boolean matchCraftingStructures(AdvancedRecipe aRecipe, Location pos) {
		for (String structName : aRecipe.getStructNames()) {
		    //System.out.println(getStructureForName(structName));
			if (getStructureForName(structName).lookForStructure(pos)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the Crafting Structure for its name
	 * 
	 * @param name
	 * @return
	 */
	public CraftingStructure getStructureForName(String name) {
		return structures.get(name);
	}

}
