package me.darkluke1111.recipeBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;

public class RecipeBuildView extends View {
    
    public RecipeBuildView() {
    	Material walls = Material.STAINED_GLASS_PANE;
        setItemColumn(3, new MenuItem(walls,this));
        setItemColumn(5, new MenuItem(walls,this));
        setItem(0, 4, new MenuItem(walls,this));
        setItem(2, 4, new MenuItem(walls,this));
        
        setItemColumn(0, new ConfigItem(this));
        setItemColumn(1, new ConfigItem(this));
        setItemColumn(2, new ConfigItem(this));
        
        setItem(1,4, new ConfigItem(this));
        
        setItem(3,9, new Button(Material.REDSTONE_BLOCK,(byte) 0, "Accept", "Accepts the selection", this, new ArrayList<ButtonAction>(Arrays.asList(ButtonAction.SAVE_CONFIG, ButtonAction.CLOSE_MENU))));
    }

}
