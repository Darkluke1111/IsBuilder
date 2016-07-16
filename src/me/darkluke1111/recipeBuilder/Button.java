package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;

public abstract class Button extends MenuItem{
    

    
    public Button(Material pic, String displayName, String description, View containingView) {
        super(pic, displayName, description, containingView);
    }
    
    public abstract void pressed();

}