package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItem {

    protected Material pic;
    protected byte data;
    protected String displayName;
    protected String description;
    protected View containingView;
    protected ItemStack icon;
    protected boolean removable;
    protected int slot;
 
    
    public MenuItem(Material pic, View containingView) {
        this.pic = pic;
        this.data = 0;
        this.displayName = "";
        this.description = "";
        this.containingView = containingView;
        this.removable = false;
        buildIcon();
    }
    
    public MenuItem(Material pic, byte data, View containingView) {
        this.pic = pic;
        this.data = data;
        this.displayName = "";
        this.description = "";
        this.containingView = containingView;
        this.removable = false;
        buildIcon();
    }
    
    public MenuItem(Material pic, byte data, String displayName, String description, View containingView, boolean removable) {
		this.pic = pic;
		this.data = data;
		this.displayName = displayName;
		this.containingView = containingView;
		this.description = description;
		this.removable = removable;
		buildIcon();

	}

	protected void buildIcon() {
		icon = new ItemStack(pic);
		if (icon.getType() != Material.AIR) {
			ItemMeta meta = icon.getItemMeta();
			meta.setDisplayName(displayName);
			icon.setItemMeta(meta);
		}
	}
    
    public Material getPic() {
        return pic;
    }
    
    public byte getData() {
		return data;
	}
    
    public String getDisplayName() {
        return displayName;
    }

    public View getContainingView() {
        return containingView;
    }

    public String getDescription() {
        return description;
    }
    
    public ItemStack getIcon() {
        return icon;
    }
    
    public void handleClick(InventoryClickEvent e) {
//    	if(!isRemovable()) {
    		e.setCancelled(true);
//    	}
    }
    
    public boolean isRemovable() {
    	return removable;
    }
    
    
    public MenuItem copy() {
        return new MenuItem(pic,data, displayName, description, containingView, removable);
    }
    
    public void setSlot(int slot) {
    	this.slot = slot;
    }
    
	public void setMat(Material mat, byte data) {
		this.pic = mat;
		this.data = data;
		this.icon = new ItemStack(mat,1,data);
		//containingView.setItem(slot, this);
		containingView.updateSlot(slot);
	}
}

