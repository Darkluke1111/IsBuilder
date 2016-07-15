package me.darkluke1111.isBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import schematicUtils.Schematic;
import schematicUtils.SchematicUtils;

/**Instances represent a blockstructure which needs to be placed around a workbench in order to use advanced recipes
 * @author Lukas
 *
 */
public class CraftingStructure {


	
	private Schematic schematic;

	public CraftingStructure(String name, Plugin plugin) throws RecipeLoadException {
		File schematicFile = new File(plugin.getDataFolder() + File.separator + name);
		try {
			schematic = SchematicUtils.loadSchematic(schematicFile);

		} catch (IOException e) {
			RecipeLoadException ex = new RecipeLoadException("Error while loading Recipe " + name + "from config.yml");
			throw ex;
		}
	}
	
	class RecipeLoadException extends Exception{
		private static final long serialVersionUID = 6304953078558085195L;

		public RecipeLoadException(String message) {
			super(message);
		}
	}
	
	public Schematic getSchematic() {
		return schematic;
	}
	
	@SuppressWarnings("deprecation")
	public boolean lookForStructure(Location pos) {
		Location origin = new Location(pos.getWorld(), pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
		Schematic struct = this.getSchematic();
		origin = origin.add(struct.getOffset());
		Location temp;
		int index;
		for(short x = 0; x < struct.getLenght(); x++) {
			for(short y = 0; y < struct.getHeight(); y++) {
				for(short z = 0; z < struct.getWidth(); z++) {
					temp = origin.clone();
					temp.add(x, y, z);
					index = y * struct.getWidth() * struct.getLenght() + z * struct.getWidth() + x;
//					System.out.println(x + " " + y + " " + z + " - Is: " + temp.getBlock().getTypeId() + " should be: " + struct.getBlocks()[index]);
					if(struct.getData()[index]==0) continue;
					if(!(temp.getBlock().getTypeId() == (int)struct.getBlocks()[index])
							|| !(temp.getBlock().getData() == (int)struct.getData()[index])) {
						return false;					
					}
				}
			}
		}	
		return true;
	}



	
	
	
}
