package me.darkluke1111.isBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.material.MaterialData;

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
	public MaterialData getResultMat(String name) {
		String entry = config.getString("recipes." + name + ".resultMat");
		String[] devided = entry.split(":");
		
		MaterialData mat = new MaterialData(Material.getMaterial(devided[0]), Byte.parseByte(devided[1]));
		
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
	public Map<Character, MaterialData> getIngredients(String name) {
		Map<Character, MaterialData> ingredients = new HashMap<>();
		Set<String> set = config.getConfigurationSection("recipes." + name + ".ingredients").getKeys(false);
		for (String letter : set) {
			String entry = config.getString("recipes." + name + ".ingredients." + letter);
			
			String[] devided = entry.split(":");	
			MaterialData md = new MaterialData(Material.getMaterial(devided[0]), Byte.parseByte(devided[1]));
			ingredients.put(letter.charAt(0), md);
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
	
	public void writeRecipe(AdvancedRecipe recipe) {
		prepareConfig();
		if(!writeName(recipe.getName())) return;
		writeResult(recipe);
		writeAmount(recipe);
		writePattern(recipe);
		writeIngridients(recipe);
		writeStructNames(recipe);
		
	}
	
	public void prepareConfig() {
		if(!config.isConfigurationSection("recipes")) {
			config.createSection("recipes");
		}
	}
	
	public boolean writeName(String name) {
		if(config.isString("recipes." + name)) return false;
		config.createSection("recipes." + name);
		return true;
	}
	
	public void writeResult(AdvancedRecipe recipe) {
		config.set("recipes." + recipe.getName() + ".resultMat", recipe.getRecipe().getResult().getType().toString());
	}
	
	public void writeAmount(AdvancedRecipe recipe) {
		config.set("recipes." + recipe.getName() + ".resultAmount", recipe.getRecipe().getResult().getAmount());
	}
	
	public void writePattern(AdvancedRecipe recipe) {
		String pattern;
		pattern = StringUtils.join(recipe.getRecipe().getShape(),"-");
		config.set("recipes." + recipe.getName() + ".resultPattern", pattern);
	}
	
	public void writeIngridients(AdvancedRecipe recipe) {
		config.createSection("recipes." + recipe.getName(), recipe.getRecipe().getIngredientMap());
	}
	
	public void writeStructNames(AdvancedRecipe recipe) {
		List<String> list = new ArrayList<>(recipe.getStructNames());
		config.set("recipes" + recipe.getName() + "craftStructNames", list);
	}
}
