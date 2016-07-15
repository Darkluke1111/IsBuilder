package me.darkluke1111.isBuilder;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class
 * 
 * @author Lukas
 *
 */
public class IsBuilder extends JavaPlugin {

	RecipyManager rm;
	RecipeLoader rl;

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		IsBuilderUtils.unpackSchematics(this);
		

		rl = new RecipeLoader(getConfig());
		rm = new RecipyManager(this);
		rm.loadStructures();
		rm.addRecipes(rl.loadRecipes());

	}
	
	@Override
	public void onDisable() {
		rm.destroy();
	}
	
	

}
