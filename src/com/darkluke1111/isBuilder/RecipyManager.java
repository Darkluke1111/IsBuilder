package com.darkluke1111.isBuilder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

public class RecipyManager implements Listener{
	
	private List<Recipe> recipies = new ArrayList<>();
	private Plugin plugin;
	
	
	
	public RecipyManager(Plugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	public void addRecipy(Recipe recipe) {
		recipies.add(recipe);
		plugin.getServer().addRecipe(recipe);
	}
	
	public void destroy() {
		HandlerList.unregisterAll(this);
	}
	
	public void onCrafting(PrepareItemCraftEvent event) {
		for(Recipe recipe : recipies) {
			if(recipiesAreEqual(recipe,event.getRecipe())) {
				//TODO
			}
		}
	}
	
	public boolean recipiesAreEqual(Recipe r1, Recipe r2) {
		//TODO Compare
		return false;
	}

}
