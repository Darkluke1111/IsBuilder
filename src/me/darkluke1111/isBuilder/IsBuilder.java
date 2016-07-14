package me.darkluke1111.isBuilder;

import org.bukkit.plugin.java.JavaPlugin;

public class IsBuilder extends JavaPlugin{
	
	RecipyManager rm;
	
	@Override
	public void onEnable() {
		saveResource("Lvl1.schematic", true);
		rm = new RecipyManager(this);

	}
	
	@Override
	public void onDisable() {
		rm.destroy();
	}
	
	

}
