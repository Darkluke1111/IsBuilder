package me.darkluke1111.recipeBuilder;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ButtonPressedEvent extends Event implements Cancellable{
    
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();
    
    public ButtonPressedEvent(Button button) {
        
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
        
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
