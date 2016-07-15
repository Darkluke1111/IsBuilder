package me.darkluke1111.isBuilder;

import org.bukkit.Location;

/**
 * Instances represent a blockstructure which needs to be placed around a
 * workbench in order to use advanced recipes
 * 
 * @author Lukas
 *
 */
public class CraftingStructure {

	private Schematic schematic;

	public CraftingStructure(String name, Schematic schematic) {
		this.schematic = schematic;
	}

	/**
	 * Returns the blockstructure as Schematic
	 * 
	 * @return
	 */
	public Schematic getSchematic() {
		return schematic;
	}

	/**
	 * Looks for the structure at the given Location (The structure will be
	 * recognized if the structures crafting table is one block below pos)
	 * 
	 * @param pos
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean lookForStructure(Location pos) {
		Location origin = new Location(pos.getWorld(), pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
		Schematic struct = this.getSchematic();
		origin = origin.add(struct.getOffset());
		Location temp;
		int index;
		for (short x = 0; x < struct.getLenght(); x++) {
			for (short y = 0; y < struct.getHeight(); y++) {
				for (short z = 0; z < struct.getWidth(); z++) {
					temp = origin.clone();
					temp.add(x, y, z);
					index = y * struct.getWidth() * struct.getLenght() + z * struct.getWidth() + x;
					// System.out.println(x + " " + y + " " + z + " - Is: " +
					// temp.getBlock().getTypeId() + " should be: " +
					// struct.getBlocks()[index]);
					if (struct.getData()[index] == 0)
						continue;
					if (!(temp.getBlock().getTypeId() == (int) struct.getBlocks()[index])
							|| !(temp.getBlock().getData() == (int) struct.getData()[index])) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
