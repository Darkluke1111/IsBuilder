# IsBuilder

IsBuilder is a Minecraftplugin, using the Bukkit API

What it does:
Adds the possibility to make advanced recipes, which need different multiblock structures around the workbench in order to be crafted. It is possible to build your own recipes, using the config.yml and also to use your own multiblock structures by placing them into the data-folder as schematics (read below for instructions).
The plugin is mainly designed for skyblock servers, to make it possible to craft blocks which would not be accessible in vanilla minecraft. The multiblock structures are meant to represent different tech-lvls, through which the player has to evolve in order to craft more valuable blocks.

Adding advanced recipes:
You can only make shaped recipes which need a 3x3 crafting grid (yet). There is already one recipe example in the config file to give you an idea how it works.

#################################################

recipes: 							#here begins the section in which you can add your recipes
  GoldBlock1: 						#name of the recipe (can be almost everything, is not shown ion the game)
    resultMat: GOLD_BLOCK			#The Material which is crafted (This needs to be a Material-Name from the Bukkit API!)
    resultAmount: 1					#Amount of the Crafting-Product, crafted in a single crafting-operation)
    craftPattern: "XXX-XXX-XXX"		#Specifies the 3x3 crafting grid, you can use common characters as placeholder 
    								#for different ingredients ("X" in this example). Separate the rows with "-".
    ingredients:					#Section in which you need to map the Placeholder-Characters to the Ingredients
      X: COBBLESTONE				#Add entries for ALL characters, used in the craftingPattern-section and specify the 
      								#corresponding Material (This needs to be a Material-Name from the Bukkit API!)
    craftStructNames:				#This is the Section, where you can list the names of the multiblock structures
      - Lvl1						#You can add as many structures as you want to one recipe. Keep in mind that in the
      								#game you will need only ONE of them! This way you can allow players to craft Items,
      								#which normally only need a lvl1 structure can also be crafted at a lvl2 structure.
      								#IMPORTANT: The structure names need to match the correspondig schematicfile-names 
      								#without the .schematic extension
      								
##################################################

Adding structures:
Structures can be added by placing schematic files into the plugin's datafolder. The file needs to have the extension ".schematic". The filename without the extension will be the structure name, which you need to use in the config.yml file to add the structure to a recipe.
IMPORTANT: Using an GZIPInputStream to load the schematics from the gzipped schematicfiles, which are provided by WorldEdit, did not work for me. This means the plugin only accepts unzipped schematic files! If you got zipped ones, unzip them before you add them into the datafolder!
