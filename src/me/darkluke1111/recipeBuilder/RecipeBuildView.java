package me.darkluke1111.recipeBuilder;

import org.bukkit.Material;

public class RecipeBuildView extends View {
    
    public RecipeBuildView() {
    	Material walls = Material.STAINED_GLASS_PANE;
        setItemColumn(3, new MenuItem(walls,this));
        setItemColumn(5, new MenuItem(walls,this));
        setItem(0, 4, new MenuItem(walls,this));
        setItem(2, 4, new MenuItem(walls,this));
    }

}
