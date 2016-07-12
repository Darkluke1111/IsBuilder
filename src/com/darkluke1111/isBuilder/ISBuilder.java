package com.darkluke1111.isBuilder;

import org.bukkit.plugin.java.JavaPlugin;

public class ISBuilder extends JavaPlugin{
	
	RecipyManager rm;
	
	@Override
	public void onEnable() {
		rm = new RecipyManager(this);
	}
	
	@Override
	public void onDisable() {
		rm.destroy();
	}
	

}
