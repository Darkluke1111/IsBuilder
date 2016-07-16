package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
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
 
    
    public MenuItem(Material pic, View containingView) {
        this.pic = pic;
        this.data = 0;
        this.displayName = "";
        this.description = "";
        this.containingView = containingView;
        this.removable = true;
        buildIcon();
    }
    
    public MenuItem(Material pic, byte data, View containingView) {
        this.pic = pic;
        this.data = data;
        this.displayName = "";
        this.description = "";
        this.containingView = containingView;
        this.removable = true;
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
    
    
    public MenuItem copy() {
        return new MenuItem(pic,data, displayName, description, containingView, removable);
    }
}

