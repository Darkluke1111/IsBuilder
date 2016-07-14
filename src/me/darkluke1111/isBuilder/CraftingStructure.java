package me.darkluke1111.isBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import schematicUtils.Schematic;
import schematicUtils.SchematicUtils;

public class CraftingStructure {

	private static Map<String, Schematic> structures = new HashMap<>();

	private CraftingStructure() {
	}

	public static void unpackSchematics(Plugin plugin) {
		try {
			JarFile jar = new JarFile(
					plugin.getDataFolder().getParentFile() + File.separator + plugin.getName() + ".jar");

			Enumeration<JarEntry> enumEntries = jar.entries();
			while (enumEntries.hasMoreElements()) {
				JarEntry file = enumEntries.nextElement();
				File f = new File(plugin.getDataFolder() + java.io.File.separator + file.getName());
				if (!file.getName().endsWith(".schematic"))
					continue;
				// if (file.isDirectory()) { // if its a directory, create it
				// f.mkdir();
				// continue;
				// }

				InputStream is = jar.getInputStream(file); // get the input
															// stream
				FileOutputStream fos = new FileOutputStream(f);

				while (is.available() > 0) { // write contents of 'is' to 'fos'
					fos.write(is.read());
				}

				fos.close();
				is.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadStructures(Plugin plugin) {
		unpackSchematics(plugin);
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
