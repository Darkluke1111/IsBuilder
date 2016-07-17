package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ConfigItem extends MenuItem {
	
	public ConfigItem(View containingView) {
		super(Material.AIR, containingView);
		removable = true;
	}
	
	public ItemStack getConfigItemStack() {
		return containingView.getIcons()[slot];
	}

}
