package me.darkluke1111.recipeBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class Button extends MenuItem{
       
	ButtonAction action;
    public Button(Material pic, byte data, String displayName, String description, View containingView, ButtonAction action) {
        super(pic, data, displayName, description, containingView, false);
        this.action = action;
    }
    
    public void pressed() {
    	ButtonPressedEvent event = new ButtonPressedEvent(this);
    	Bukkit.getServer().getPluginManager().callEvent(event);
    }

}

enum ButtonAction {
	SWITCH_VIEW,
	SAVE_RECIPE,
	CLOSE_MENU;
}

