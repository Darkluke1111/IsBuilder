package me.darkluke1111.isBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 * Instances represent advanced recipes, while wrapping a Bukkit ShapedRecipe
 * instance
 * 
 * @author Lukas
 *
 */
@SerializableAs("AdvancedRecipe")
public class AdvancedRecipe implements ConfigurationSerializable {

	private ShapedRecipe recipe;
	private Set<String> structNames;
	private String name;
	
	public final static String NAME_TAG = "name";
	public final static String RESULT_TAG = "resultMat";
	public final static String AMOUNT_TAG = "resultAmount";	
	public final static String PATTERN_TAG = "craftPattern";
	public final static String INGREDIENT_TAG = "ingredients";
	public final static String STRUCT_TAG = "craftStructNames";

	/**
	 * Constructor builds Shapedrecipe and wraps it together with a list of
	 * crafting structures
	 * 
	 * @param name
	 * @param resultMat
	 * @param amount
	 * @param pattern
	 * @param ingredients
	 * @param list
	 */
	public AdvancedRecipe(String name, MaterialData resultMat, int amount, String[] pattern, Map<Character, MaterialData> ingredients,
			List<String> list) {
		

		ItemStack result = new ItemStack(resultMat.getItemType(), amount, resultMat.getData());
		result = addLoreMarker(result);
		recipe = new ShapedRecipe(result);
		System.out.println(result.toString());

		recipe.shape(pattern[0], pattern[1], pattern[2]);

		for (Entry<Character, MaterialData> entry : ingredients.entrySet()) {
			recipe.setIngredient(entry.getKey(), entry.getValue());
		}
		structNames = new HashSet<>(list);
		this.name = name;

	}
	
	public AdvancedRecipe(String name, ItemStack result, String[] pattern, Map<Character, ItemStack> ingredients,
			List<String> list) {

		result = addLoreMarker(result);
		recipe = new ShapedRecipe(result);
		System.out.println(result.toString());

		recipe.shape(pattern[0], pattern[1], pattern[2]);

		for (Entry<Character, ItemStack> entry : ingredients.entrySet()) {
			recipe.setIngredient(entry.getKey(), entry.getValue().getData());
		}
		structNames = new HashSet<>(list);
		this.name = name;		
	}
	
	public static AdvancedRecipe deserialize(Map<String, Object> map) {
		String name = "deserialized";
		
//		if(map.get(RESULT_TAG) == null || !(map.get(RESULT_TAG) instanceof String)) {
//			System.out.println("Error at resultMat");
//			return null;
//		}
//		String entry = (String) map.get(RESULT_TAG);
//		String[] devided = entry.split(":");
//		MaterialData resultMat = new MaterialData(Material.getMaterial(devided[0]), Byte.parseByte(devided[1]));
//		
//		if(map.get(AMOUNT_TAG) == null || !(map.get(AMOUNT_TAG) instanceof Integer)) {
//			System.out.println("Error in resultAmount");
//			return null;
//		}
//		int resultAmount = (int) map.get(AMOUNT_TAG);
		
		if(map.get(RESULT_TAG) == null || !(map.get(RESULT_TAG) instanceof ItemStack)) {
			System.out.println("Error at resultMat");
			return null;
		}
		ItemStack result = (ItemStack) map.get(RESULT_TAG);
		
		
		if(map.get(PATTERN_TAG) == null || !(map.get(PATTERN_TAG) instanceof String)) {
			System.out.println("Error in craftingPattern");
			return null;
		}		
		String[] craftPattern = ((String)map.get(PATTERN_TAG)).split("-");
		if(craftPattern.length != 3) {
			System.out.println("Error in craftingPattern format"); 
			return null;
		}
		
		if(map.get(INGREDIENT_TAG) == null || !(map.get(INGREDIENT_TAG) instanceof Map<?,?>)) {
			System.out.println("Error in ingredients");
			return null;
		}
//		Map<Character, MaterialData> ingredients = new HashMap<>();
//		MaterialData ingredientData;
//		String[] devided1;
//		try {
//			Map<String, String> stringMap = (Map<String, String>) map.get(INGREDIENT_TAG);
//			for (Entry<String, String> entry1 : stringMap.entrySet()) {
//				devided1 = entry1.getValue().split(":");
//				ingredientData = new MaterialData(Material.getMaterial(devided1[0]), Byte.parseByte(devided1[1]));
//				ingredients.put(entry1.getKey().charAt(0), ingredientData);
//			}
//		} catch (ClassCastException ex) {
//			System.out.println("Error in ingriedient format");
//			return null;
//		}
		
		Map<String, ItemStack> stringMap = (Map<String, ItemStack>) map.get(INGREDIENT_TAG);
		Map<Character, ItemStack> ingredients = new HashMap<>();
		for(Entry<String, ItemStack> entry : stringMap.entrySet()) {
			ingredients.put(entry.getKey().charAt(0), entry.getValue());
		}
		
		if(map.get(STRUCT_TAG) == null || !(map.get(STRUCT_TAG) instanceof List)) {
			System.out.println("Error in craftStructNames");
			return null;
		}
		List<String> craftStructNames = (List<String>) map.get(STRUCT_TAG);
		
		return new AdvancedRecipe(name, result, craftPattern, ingredients, craftStructNames);
	}
	

	/**
	 * Returns the wraped ShapedRecipe
	 * 
	 * @return
	 */
	public ShapedRecipe getRecipe() {
		return recipe;
	}

	/**
	 * Returns crafting structures
	 * 
	 * @return
	 */
	public Set<String> getStructNames() {
		return structNames;
	}

	/**
	 * Adds "Advanced" to the item lore to mark it as a result of an advanced
	 * recipe
	 * 
	 * @param item
	 * @return
	 */
	private ItemStack addLoreMarker(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList("Advanced"));
		item.setItemMeta(meta);
		return item;
	}
	
	@Override
	public boolean equals(Object rSide) {
	    if(!(rSide instanceof AdvancedRecipe)) return false;
	    
	    AdvancedRecipe rRecipe = (AdvancedRecipe) rSide;
	    if(!IsBuilderUtils.recipiesAreEqual(this.getRecipe(), rRecipe.getRecipe())) return false;
	    
	    //if(!getStructNames().equals(rRecipe.getStructNames())) return false;
	    
        return true;    
	}
	
	@Override
	public int hashCode() {
	    return new HashCodeBuilder(5,11).append(recipe.getIngredientMap()).append(recipe.getShape()).toHashCode();
	}
	
	public String getName() {
		return name;
	}


	@Override
	public Map<String, Object> serialize() {
		Map<String,Object> configMap = new HashMap<>();
		
//		String resultMat = recipe.getResult().getType().toString() + ":" + Byte.toString(recipe.getResult().getData().getData());
//		configMap.put(RESULT_TAG, resultMat);
//		
//		int resultAmount = recipe.getResult().getAmount();
//		configMap.put(AMOUNT_TAG, resultAmount);
		
		ItemStack resultMat = recipe.getResult();
		configMap.put(RESULT_TAG, resultMat);
		
		String craftPattern = recipe.getShape()[0] + "-" + recipe.getShape()[1] + "-" + recipe.getShape()[2];  
		configMap.put(PATTERN_TAG, craftPattern);
		
		Map<String,ItemStack> ingredients = new HashMap<>();

		for(Entry<Character,ItemStack> entry : recipe.getIngredientMap().entrySet()) {
			ingredients.put(entry.getKey().toString(), entry.getValue());
		}
		configMap.put(INGREDIENT_TAG, ingredients);
		
		List<String> craftStructNames = new ArrayList<>(structNames);
		configMap.put(STRUCT_TAG, craftStructNames);
		
		
		return configMap;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Result: " + recipe.getResult().getType().toString());
		sb.append(":" + Byte.toString(recipe.getResult().getData().getData()));
		sb.append(" Pattern: " + recipe.getShape()[0] + "-" + recipe.getShape()[1] + "-" + recipe.getShape()[2]);
		return sb.toString();
	}
}
