package me.darkluke1111.isBuilder;

import org.bukkit.plugin.java.JavaPlugin;

public class IsBuilder extends JavaPlugin{
	
	RecipyManager rm;
	RecipeLoader rl;
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveResource("Lvl1.schematic", true);
		CraftingStructure.loadStructures(this);
		rm = new RecipyManager(this);
		rl = new RecipeLoader(this);
		rm.initRecipes(this, rl);

	}
	
	@Override
	public void onDisable() {
		rm.destroy();
	}
	
	

}
