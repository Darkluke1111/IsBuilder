package me.darkluke1111.isBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
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

    /**Compares the recipes due to their crafting pattern and ingredients. Always returns false for Furnace Recipes/unshaped recipes (yet).
     * 
     * @param r1
     *            The first recipe
     * @param r2
     *            The second recipe
     * @return True if the recipes are "equal"
     */
    public static boolean recipiesAreEqual(Recipe r1, Recipe r2) {

        if ((r1 instanceof ShapedRecipe) && (r1 instanceof ShapedRecipe)) {
            ShapedRecipe sr1 = (ShapedRecipe) r1;
            ShapedRecipe sr2 = (ShapedRecipe) r2;
            
            return shapedRecipesAreEqual(sr1, sr2);
            
        } else {
            //TODO Other Recipe Types
        }

		return false;
	}
    
    public static boolean shapedRecipesAreEqual(ShapedRecipe r1, ShapedRecipe r2) {
        Map<Character,ItemStack> im1 = r1.getIngredientMap();
        Map<Character,ItemStack> im2 = r2.getIngredientMap();
        
        String[] p1 = r1.getShape();
        String[] p2 = r2.getShape();
        
        if(p1.length != p2.length) {
            System.out.println("Different Arr-Length");
            return false;
        }
        
        for(int i = 0; i < p1.length ; i++) {
            if(p1[i].length() != p2[i].length()) {
                System.out.println("Different Str-length");
                return false;
            }
            
            for(int j = 0; j < p1[i].length(); j++) {
                ItemStack is1 = im1.get(p1[i].charAt(j));
                ItemStack is2 = im2.get(p2[i].charAt(j));
                if(is1.getType() != is2.getType()) {
                    System.out.println("Different type");
                    return false;
                }
                if(is1.getData().getData() != is2.getData().getData()) {
                    System.out.println("Diefferent data");
                    return false;
                }
            }
        }
        return true;
    }
}
