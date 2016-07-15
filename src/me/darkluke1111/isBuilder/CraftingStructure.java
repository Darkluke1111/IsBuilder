package me.darkluke1111.isBuilder;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Instances represent a blockstructure which needs to be placed around a
 * workbench in order to use advanced recipes
 * 
 * @author Lukas
 *
 */
public class CraftingStructure {

	String name;
	private short[] blocks;
	private byte[] data;
	private short width;
	private short lenght;
	private short height;
	private Vector offset;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Name of the CraftingStructure
	 * @param blocks2
	 *            The Array which represents the structure blocks
	 * @param data
	 *            The Array which represents the structures blockdata
	 * @param width
	 *            The width of the structure
	 * @param lenght
	 *            The length of the structure
	 * @param height
	 *            The height of the structure
	 * @param offset
	 *            Vector from The Structures corner to the block above the
	 *            workbench
	 */
	public CraftingStructure(String name, short[] blocks2, byte[] data, short width, short lenght, short height,
			Vector offset) {

		this.blocks = blocks2;
		this.data = data;
		this.width = width;
		this.lenght = lenght;
		this.height = height;
		this.offset = offset;
	}

	/**
	 * Looks for the structure at the given Location (The structure will be
	 * recognized if the structures crafting table is one block below pos)
	 * 
	 * @param pos
	 *            The Location which should be above the workbench
	 * @return True if the structure was found
	 */
	@SuppressWarnings("deprecation")
	public boolean lookForStructure(Location pos) {
		Location origin = new Location(pos.getWorld(), pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
		origin = origin.add(getOffset());
		Location temp;
		int index;
		for (short x = 0; x < getLenght(); x++) {
			for (short y = 0; y < getHeight(); y++) {
				for (short z = 0; z < getWidth(); z++) {
					temp = origin.clone();
					temp.add(x, y, z);
					index = y * getWidth() * getLenght() + z * getWidth() + x;
					// System.out.println(x + " " + y + " " + z + " - Is: " +
					// temp.getBlock().getTypeId() + " should be: " +
					// struct.getBlocks()[index]);
					if (getData()[index] == 0)
						continue;
					if (!(temp.getBlock().getTypeId() == (int) getBlocks()[index])
							|| !(temp.getBlock().getData() == (int) getData()[index])) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * @return the blocks
	 */
	public short[] getBlocks() {
		return blocks;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @return the width
	 */
	public short getWidth() {
		return width;
	}

	/**
	 * @return the lenght
	 */
	public short getLenght() {
		return lenght;
	}

	/**
	 * @return the height
	 */
	public short getHeight() {
		return height;
	}

	public Vector getOffset() {
		return offset;
	}

}
