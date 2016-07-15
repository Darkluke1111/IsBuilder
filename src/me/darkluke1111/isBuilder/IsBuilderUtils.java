package me.darkluke1111.isBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

/**
 * Provides utility methods for the IsBuilder plugin
 * 
 * @author Lukas
 *
 */
public class IsBuilderUtils {

	/**
	 * Unpacks schematic files from the plugin jar into the data-folder
	 * 
	 * @param plugin
	 *            The owning plugin
	 */
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

	/**
	 * compares the results of two Bukkit Recipes NOT the whole recipe! Takes
	 * into account the Lore attatched to the result ItemStack to distinguish
	 * advanced recipe results from normal ones
	 * 
	 * @param r1
	 *            The first recipe
	 * @param r2
	 *            The second recipe
	 * @return True if the recipes are "equal"
	 */
	public static boolean recipiesAreEqual(Recipe r1, Recipe r2) {
		ItemStack is1 = r1.getResult();
		ItemStack is2 = r2.getResult();
		if (is1.getType() == is2.getType()) {
			String lore1 = is1.getItemMeta().getLore().get(0);
			String lore2 = is2.getItemMeta().getLore().get(0);
			if (lore1.equals(lore2)) {
				return true;
			}
		}
		return false;
	}
}
