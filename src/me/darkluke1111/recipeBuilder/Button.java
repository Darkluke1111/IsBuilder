package me.darkluke1111.recipeBuilder;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class Button {
    
    private Material pic;
    private String displayName;
    private String internalName;
    private String description;
    private View containingView;
    
    public Button(Material pic, String displayName, String internalName, String description, View containingView) {
        this.pic = pic;
        this.displayName = displayName;
        this.internalName = internalName;
        this.description = description;
        this.containingView = containingView;
    }
    
    public void pressed() {

        ButtonPressedEvent event = new ButtonPressedEvent(this);

        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public Material getPic() {
        return pic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getDescription() {
        return description;
    }

    public View getContainingView() {
        return containingView;
    }

}