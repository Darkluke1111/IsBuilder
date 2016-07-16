package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class View {

	private MenuItem[] items = new MenuItem[RecipeBuildMenu.INV_SIZE];

	public View setItem(int slot, MenuItem item) {
		if (slot < RecipeBuildMenu.INV_SIZE)
			items[slot] = item;
		return this;
	}

	public View() {

	}

	public View setItem(int row, int column, MenuItem item) {
		int slot = row * 9 + column;
		setItem(slot, item);
		return this;
	}

	public View setItemColumn(int column, MenuItem item) {
		if (column < 9) {
			for (int i = column; i < RecipeBuildMenu.INV_SIZE; i += 9) {
				items[i] = item.copy();
			}
		}
		return this;
	}

	public View setItemRow(int row, MenuItem item) {
		if (row < RecipeBuildMenu.INV_SIZE / 9) {
			for (int i = 9 * row; i < 9 * row + 9; i++) {
				items[i] = item.copy();
			}
		}
		return this;
	}

	public void handleClick(int slot, Player p) {

	}

	public ItemStack[] getIcons() {
		ItemStack[] icons = new ItemStack[items.length];
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				icons[i] = new ItemStack(Material.AIR);
			} else {
				icons[i] = items[i].getIcon();
			}
		}
		return icons;
	}
}
