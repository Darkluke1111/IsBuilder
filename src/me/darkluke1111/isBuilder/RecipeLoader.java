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

		for (String key : confSec.getKeys(false)) {
			recipe = new AdvancedRecipe(key, getResultMat(key), getresultAmount(key), getPattern(key),
					getIngredients(key), getCraftStructNames(key));
			recipes.add(recipe);
		}
		return recipes;
	}

	/**
	 * Returns the result of a recipe in form of a Material instance
	 * 
	 * @param name
	 * @return
	 */
	public Material getResultMat(String name) {
		String materialName = config.getString("recipes." + name + ".resultMat");
		Material mat = Material.getMaterial(materialName);
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
	public Map<Character, Material> getIngredients(String name) {
		Map<Character, Material> ingredients = new HashMap<>();
		Set<String> set = config.getConfigurationSection("recipes." + name + ".ingredients").getKeys(false);
		for (String letter : set) {
			String matName = config.getString("recipes." + name + ".ingredients." + letter);
			ingredients.put(letter.charAt(0), Material.getMaterial(matName));
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
		List<String> structs = new ArrayList<>();
		for (Object obj : list) {
			structs.add((String) obj);
		}
		return structs;
	}
}
