package me.darkluke1111.isBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.plugin.Plugin;

public class IsBuilderUtils {

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
}
