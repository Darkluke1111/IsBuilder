package me.darkluke1111.isBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import schematicUtils.Schematic;
import schematicUtils.SchematicUtils;

public class CraftingStructure {
	
	private static Map<String,Schematic> structures = new HashMap<>();
	
	private CraftingStructure() {}
	
	public static void loadStructures(Plugin plugin) {
		File folder = plugin.getDataFolder();
		String[] filenames = folder.list();
		File schematicFile;
		Schematic schematic;
		for(String name : filenames) {
			if(name.endsWith(".schematic")) {
				schematicFile = new File(plugin.getDataFolder() + File.separator + name);
				try {
					schematic = SchematicUtils.loadSchematic(schematicFile);
					structures.put(name.substring(0, name.lastIndexOf('.')), schematic);
				} catch (IOException e) {
					plugin.getServer().getLogger().warning("Fehler beim laden von " + schematicFile.getAbsolutePath());
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	public static Schematic getStructureForName(String name) {
		return structures.get(name);
	}



	@SuppressWarnings("deprecation")
	static public boolean lookForStructure(Location pos, Schematic struct) {
		Location origin = new Location(pos.getWorld(), pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
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
