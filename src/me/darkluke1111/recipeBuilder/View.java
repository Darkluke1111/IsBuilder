package me.darkluke1111.recipeBuilder;

public class View {
    
    private MenuItem[] items = new MenuItem[RecipeBuildMenu.INV_SIZE];

    public View setItem(int slot, MenuItem item) {
        items[slot] = item;
        return this;
    }
    
    public View setItemColumn(int column, MenuItem item) {
        for(int i = column; i < RecipeBuildMenu.INV_SIZE ; i+=9) {
            items[i] = item;
        }
        return this;
    }
    
    public View setItemRow(int row, MenuItem item) {
        for(int i = 9*row; i < 9*row + 9; i++) {
            items[i] = item;
        }
        return this;
    }
}
