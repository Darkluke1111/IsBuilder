package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItem {

    protected Material pic;
    protected String displayName;
    protected String description;
    protected View containingView;
    protected ItemStack icon;
    
    public MenuItem(View containingView) {
        this.pic = Material.AIR;
        this.displayName = "";
        this.description = "";
        this.containingView = containingView;
        buildIcon();
    }
    
    public MenuItem(Material pic, String displayName, String description, View containingView) {
        this.pic = pic;
        this.displayName = displayName;
        this.containingView = containingView;
        this.description = description;
        buildIcon();

        
    }
    
    protected void buildIcon() {
        icon = new ItemStack(pic);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(displayName);
        icon.setItemMeta(meta);
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
}

