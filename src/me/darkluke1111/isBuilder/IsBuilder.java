package me.darkluke1111.isBuilder;

import java.io.File;
import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
		ConfigurationSerialization.registerClass(AdvancedRecipe.class,"AdvancedRecipe");
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
		
		Iterator<AdvancedRecipe> it = rm.getRecipes().iterator();
		getConfig().set("recipes", it.next());
		saveConfig();
		reloadConfig();
		System.out.println(getConfig().getConfigurationSection("recipes"));
		System.out.println(getConfig().getValues(false));
		AdvancedRecipe recipe = (AdvancedRecipe) getConfig().get("recipes");
		System.out.println(recipe);
		getConfig().set("new",recipe);
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		rm.destroy();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if(cmd.getName().equalsIgnoreCase("openInv")) {
	        if(!(sender instanceof Player)) return true;
//	        Player p = (Player) sender;
//	        RecipeBuildMenu rbm = new RecipeBuildMenu(this);
//	        rbm.open(p);

	    }
        return true;
	}
	
	public RecipeManager getRm() {
		return rm;
	}
	
	public RecipeLoader getRl() {
		return rl;
	}

}
