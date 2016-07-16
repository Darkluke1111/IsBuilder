package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;

public abstract class Button extends MenuItem{
       
    public Button(Material pic,byte data, String displayName, String description, View containingView) {
        super(pic, data, displayName, description, containingView, false);
    }
    
    public abstract void pressed();

}