package me.darkluke1111.isBuilder;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import schematicUtils.Schematic;
import schematicUtils.schematicUtils;

public class CraftingStructure {

	private Schematic struct;

	public CraftingStructure(int lvl) {

		try{

			struct = schematicUtils.loadSchematic("Lvl" + lvl + ".schematic");
			
		} catch (IOException e) {
			Bukkit.getServer().getLogger().warning("Was not able to load Craftingstructures!");
			e.printStackTrace();
		}

	}

	public boolean lookForStructure(Location pos) {

		return true;

	}
}
