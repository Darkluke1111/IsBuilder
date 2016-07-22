package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ConfigItem extends MenuItem {
	
	public ConfigItem(View containingView) {
		super(Material.AIR, containingView);
		removable = false;
	}
	
	@Override
	public void handleClick(InventoryClickEvent e) {
		super.handleClick(e);
		System.out.println("ConfigItem Click!");
		Material mat = e.getCursor().getType();
		
		byte data = e.getCursor().getData().getData();
		System.out.println(mat + " " + data);
		setMat(mat, data);
	}

}
