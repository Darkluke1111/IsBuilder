package me.darkluke1111.isBuilder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.IntTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

/**
 * Loads CraftingStructures from plugin data folder
 * 
 * @author Lukas
 *
 */
public class StructureLoader {
	Plugin plugin;

	public StructureLoader(Plugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Loads all structures from the schematic files in the plugin data folder
	 * 
	 * @return
	 */
	public Map<String, CraftingStructure> loadStructures() {
		Map<String, CraftingStructure> structures = new HashMap<>();
		File folder = plugin.getDataFolder();
		String[] filenames = folder.list();
		CraftingStructure struct;
		for (String name : filenames) {
			if (name.endsWith(".schematic")) {
				try {
					struct = loadCraftingStructure(name);
					structures.put(name.substring(0, name.lastIndexOf('.')), struct);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return structures;
	}
	
	public CraftingStructure loadCraftingStructure(String name) throws IOException {
		File file = new File(plugin.getDataFolder() + File.separator + name);
		FileInputStream stream = new FileInputStream(file);
		NBTInputStream nbtStream = new NBTInputStream(new BufferedInputStream(stream));

		CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
		if (!schematicTag.getName().equals("Schematic")) {
			nbtStream.close();
			throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");

		}

		Map<String, Tag> schematic = schematicTag.getValue();
		if (!schematic.containsKey("Blocks")) {
			nbtStream.close();
			throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");

		}

		short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
		short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
		short height = getChildTag(schematic, "Height", ShortTag.class).getValue();

		int offsetX = getChildTag(schematic, "WEOffsetX", IntTag.class).getValue();
		int offsetY = getChildTag(schematic, "WEOffsetY", IntTag.class).getValue();
		int offsetZ = getChildTag(schematic, "WEOffsetZ", IntTag.class).getValue();
		Vector offset = new Vector(offsetX, offsetY, offsetZ);

		// Get blocks
		byte[] blockId = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
		byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
		byte[] addId = new byte[0];
		short[] blocks = new short[blockId.length]; // Have to later combine IDs

		// We support 4096 block IDs using the same method as vanilla Minecraft,
		// where
		// the highest 4 bits are stored in a separate byte array.
		if (schematic.containsKey("AddBlocks")) {
			addId = getChildTag(schematic, "AddBlocks", ByteArrayTag.class).getValue();
		}

		// Combine the AddBlocks data with the first 8-bit block ID
		for (int index = 0; index < blockId.length; index++) {
			if ((index >> 1) >= addId.length) { // No corresponding AddBlocks
												// index
				blocks[index] = (short) (blockId[index] & 0xFF);
			} else {
				if ((index & 1) == 0) {
					blocks[index] = (short) (((addId[index >> 1] & 0x0F) << 8) + (blockId[index] & 0xFF));
				} else {
					blocks[index] = (short) (((addId[index >> 1] & 0xF0) << 4) + (blockId[index] & 0xFF));
				}
			}
		}
		nbtStream.close();
		return new CraftingStructure(name, blocks, blockData, width, length, height, offset);
	}

	/**
	 * Get child tag of a NBT structure.
	 *
	 * @param items
	 *            The parent tag map
	 * @param key
	 *            The name of the tag to get
	 * @param expected
	 *            The expected type of the tag
	 * @return child tag casted to the expected type
	 * @throws DataException
	 *             if the tag does not exist or the tag is not of the expected
	 *             type
	 */
	private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected)
			throws IllegalArgumentException {
		if (!items.containsKey(key)) {
			throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
		}
		Tag tag = items.get(key);
		if (!expected.isInstance(tag)) {
			throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
		}
		return expected.cast(tag);
	}

}
