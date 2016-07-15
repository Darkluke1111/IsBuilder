package me.darkluke1111.isBuilder;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class
 * 
 * @author Lukas
 *
 */
public class IsBuilder extends JavaPlugin {

	RecipeManager rm;
	RecipeLoader rl;
	CraftingStructureLoader sl;

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		getResource("recipes.yml");
		FileConfiguration recipeConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "recipes.yml"));
		
		IsBuilderUtils.unpackSchematics(this);
		
		rl = new RecipeLoader(recipeConfig);
		sl = new CraftingStructureLoader(this);
		rm = new RecipeManager(this);
		
		rm.addStructures(sl.loadStructures());
		rm.addRecipes(rl.loadRecipes());

	}
	
	@Override
	public void onDisable() {
		rm.destroy();
	}
	
	

}
