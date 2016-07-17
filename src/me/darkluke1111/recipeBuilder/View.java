package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class View {

	private MenuItem[] items = new MenuItem[RecipeBuildMenu.INV_SIZE];
	private int maxSlots = RecipeBuildMenu.INV_SIZE;
	private int maxRows = RecipeBuildMenu.INV_SIZE/9;
	private int maxColumns = 9;

	public View setItem(int slot, MenuItem item) {
		if (slot < RecipeBuildMenu.INV_SIZE)
			item.setSlot(slot);
			items[slot] = item;
		return this;
	}

	public View() {

	}

	public View setItem(int row, int column, MenuItem item) {
		int  slot = getSlot(row, column);
		setItem(slot, item);
		return this;
	}

	public View setItemColumn(int column, MenuItem item) {
		if (column < 9) {
			for (int i = column; i < RecipeBuildMenu.INV_SIZE; i += 9) {
				setItem(i, item.copy());
			}
		}
		return this;
	}

	public View setItemRow(int row, MenuItem item) {
		if (row < RecipeBuildMenu.INV_SIZE / 9) {
			for (int i = 9 * row; i < 9 * row + 9; i++) {
				setItem(i, item.copy());
			}
		}
		return this;
	}

	public void handleClick(InventoryClickEvent e) {
		if(items[e.getSlot()] != null) items[e.getSlot()].handleClick(e);	
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
	
	public void setMenuItemAtSlot(int slot, MenuItem item) {

	}
	
	public MenuItem getItem(int slot) {
		return items[slot];
	}
	
	public MenuItem getItem(int row, int column) {
		int slot = getSlot(row, column);
		return getItem(slot);
	}
	
	public int getSlot(int row, int column) {
		return row * 9 + column;
	}
}
