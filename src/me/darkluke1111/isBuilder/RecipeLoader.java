package me.darkluke1111.isBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Class for loading advanced recipes from a .yml file, which must be provided
 * in the constructor and cannot be switched or reloaded during runtime.
 * 
 * @author Lukas
 *
 */
public class RecipeLoader {

	Configuration config;

	/**
	 * constructor takes the Configuration instance which represents the yml
	 * file that is used to load the recipes
	 * 
	 * @param config
	 */
	public RecipeLoader(Configuration config) {
		this.config = config;
	}

	/**
	 * Tries to load all recipes from the config
	 * 
	 * @return
	 */
	public Set<AdvancedRecipe> loadRecipes() {
		Set<AdvancedRecipe> recipes = new HashSet<>();
		ConfigurationSection confSec = config.getConfigurationSection("recipes");
		AdvancedRecipe recipe;
		if(confSec == null) {
			//TODO Errorhandling
			return recipes;
		}
		for (String key : confSec.getKeys(false)) {
			recipe = new AdvancedRecipe(key, getResultMat(key), getresultAmount(key), getPattern(key),
					getIngredients(key), getCraftStructNames(key));
			recipes.add(recipe);
		}
		return recipes;
	}
	
	class MaterialCompound {
		Material mat;
		byte data;
		public MaterialCompound(Material mat,byte data) {
			this.mat = mat;
			this.data = data;
		}
	}

	/**
	 * Returns the result of a recipe in form of a Material instance
	 * 
	 * @param name
	 * @return
	 */
	public MaterialCompound getResultMat(String name) {
		String entry = config.getString("recipes." + name + ".resultMat");
		String[] devided = entry.split(":");
		
		MaterialCompound mat = new MaterialCompound(Material.getMaterial(devided[0]), Byte.parseByte(devided[1]));
		
		// System.out.println(mat.toString());
		return mat;
	}

	/**
	 * Returns the amount of the resultmaterial from a single crafting process
	 * of a recipe
	 * 
	 * @param name
	 * @return
	 */
	public int getresultAmount(String name) {
		int amount = config.getInt("recipes." + name + ".resultAmount");
		// System.out.println(amount);
		return amount;
	}

	/**
	 * Returns the crafting pattern of the recipe as stringarray with 3 elements
	 * consisting of 3 letters (similar form is used in Recipe#shape(String...)
	 * form Bukkit API)
	 * 
	 * @param name
	 * @return
	 */
	public String[] getPattern(String name) {
		String[] craftPattern = config.getString("recipes." + name + ".craftPattern").split("-");
		// System.out.println(craftPattern.toString());
		return craftPattern;
	}

	/**
	 * Returns the map which maps the characters form the crafting pattern to
	 * the ingredients
	 * 
	 * @param name
	 * @return
	 */
	public Map<Character, MaterialCompound> getIngredients(String name) {
		Map<Character, MaterialCompound> ingredients = new HashMap<>();
		Set<String> set = config.getConfigurationSection("recipes." + name + ".ingredients").getKeys(false);
		for (String letter : set) {
			String entry = config.getString("recipes." + name + ".ingredients." + letter);
			
			String[] devided = entry.split(":");			
			MaterialCompound mat = new MaterialCompound(Material.getMaterial(devided[0]), Byte.parseByte(devided[1]));
			ingredients.put(letter.charAt(0), mat);
		}
		// System.out.println(ingredients.toString());
		return ingredients;
	}

	/**
	 * returns the list of possible CraftingStructures which can be used for
	 * this recipe
	 * 
	 * @param name
	 * @return
	 */
	public List<String> getCraftStructNames(String name) {
		List<?> list = config.getList("recipes." + name + ".craftStructNames");
		//TODO syso
		System.out.println(list);
		List<String> structs = new ArrayList<>();
		for (Object obj : list) {
		    //TODO syso
		    System.out.println((String)obj);
			structs.add((String) obj);
		}
		return structs;
	}
}
