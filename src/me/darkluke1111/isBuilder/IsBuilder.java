package me.darkluke1111.isBuilder;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.darkluke1111.recipeBuilder.RecipeBuildMenu;

/**
 * Main plugin class
 * 
 * @author Lukas
 *
 */
public class IsBuilder extends JavaPlugin {
    Material mat = Material.COBBLESTONE;
	RecipeManager rm;
	RecipeLoader rl;
	CraftingStructureLoader sl;

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		saveResource("recipes.yml",false);
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if(cmd.getName().equalsIgnoreCase("openInv")) {
	        if(!(sender instanceof Player)) return true;
	        Player p = (Player) sender;
	        RecipeBuildMenu rbm = new RecipeBuildMenu();
	        rbm.open(p);

	    }
        return true;
	}

}
