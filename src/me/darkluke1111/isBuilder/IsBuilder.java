package me.darkluke1111.isBuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class IsBuilder extends JavaPlugin{
	
	RecipyManager rm;
	
	@Override
	public void onEnable() {
		rm = new RecipyManager(this);
		AdvancedRecipe r1 = new AdvancedRecipe(new ItemStack(Material.GOLD_BLOCK), new CraftingStructure(1));
		r1.shape("SSS","SSS","SSS");
		r1.setIngredient('S', Material.COBBLESTONE);
		rm.addRecipy(r1);
	}
	
	@Override
	public void onDisable() {
		rm.destroy();
	}
	

}
