package me.darkluke1111.isBuilder;

import org.bukkit.Location;
import org.bukkit.Material;

public class CraftingStructure {

	private Material struct[][][];
	private int xScale;
	private int yScale;
	private int zScale;
 
	public CraftingStructure(int lvl) {
		if(lvl == 1) {
			struct = new Material[1][2][1];
			struct[0][0][0] = Material.GOLD_BLOCK;
			struct [0][1][0] = Material.WORKBENCH;
		}
		
		xScale = struct.length;
		yScale = struct[0].length;
		zScale = struct[0][0].length;
	}
	
	public boolean lookForStructure(Location pos) {
		
		for( int x = 0; x < struct.length; x++) {
			for( int y = 0; y < struct[x].length; y++) {
				for( int z = 0; z < struct[x][y].length; z++) {
					Location temp = pos.clone();
					temp.add(-(int)(xScale/2)+x,-yScale+y,-(int)(zScale/2)+z);
					System.out.println(struct[x][y][z] + " " + temp.getBlock().getType());
					if(struct[x][y][z].equals(temp.getBlock().getType())) {
						continue;
					} else {
						return false;
					}
				}
			}
		}
		return true;
		
	}
}
