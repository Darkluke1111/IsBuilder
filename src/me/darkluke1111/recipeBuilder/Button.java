package me.darkluke1111.recipeBuilder;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class Button extends MenuItem{
       
	List<ButtonAction> actions;
    public Button(Material pic, byte data, String displayName, String description, View containingView, List<ButtonAction> actions) {
        super(pic, data, displayName, description, containingView, false);
    }
    
    public void pressed() {
    	ButtonPressedEvent event = new ButtonPressedEvent(this);
    	Bukkit.getServer().getPluginManager().callEvent(event);
    }

}

enum ButtonAction {
	SWITCH_VIEW,
	SAVE_CONFIG,
	CLOSE_MENU;
}

