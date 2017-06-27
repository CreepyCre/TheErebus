package erebus;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import erebus.blocks.BlockAmber;
import erebus.blocks.BlockBerryBush;
import erebus.blocks.BlockCabbage;
import erebus.blocks.BlockDarkFruitVine;
import erebus.blocks.BlockDoorErebus;
import erebus.blocks.BlockDoubleHeightPlant;
import erebus.blocks.BlockErebusMushroomHuge;
import erebus.blocks.BlockGiantFlower;
import erebus.blocks.BlockGlowshroom;
import erebus.blocks.BlockGlowshroomStalkMain;
import erebus.blocks.BlockLeavesErebus;
import erebus.blocks.BlockMud;
import erebus.blocks.BlockMushroomSmall;
import erebus.blocks.BlockOreErebus;
import erebus.blocks.BlockOreErebus2;
import erebus.blocks.BlockPlanksErebus;
import erebus.blocks.BlockPricklyPear;
import erebus.blocks.BlockQuickSand;
import erebus.blocks.BlockRedGem;
import erebus.blocks.BlockSaplingErebus;
import erebus.blocks.BlockSmallPlant;
import erebus.blocks.BlockStigma;
import erebus.blocks.BlockThorns;
import erebus.blocks.BlockTurnip;
import erebus.blocks.BlockUmberstone;
import erebus.blocks.EnumWood;
import erebus.blocks.ErebusPortal;
import erebus.items.ItemMaterials.EnumErebusMaterialsType;
import erebus.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

	private static final List<Block> BLOCKS = new LinkedList<Block>();

	public static final Block UMBERSTONE = new BlockUmberstone();
	public static final Block PLANKS = new BlockPlanksErebus();
	public static final Block PORTAL = new ErebusPortal();

	public static final Block ORE_IRON = new BlockOreErebus(1);
	public static final Block ORE_GOLD = new BlockOreErebus(2);
	public static final Block ORE_COAL = new BlockOreErebus2(0, Items.COAL, 0, 1, 1, 0, 2);
	public static final Block ORE_DIAMOND = new BlockOreErebus2(2, Items.DIAMOND, 0, 1, 1, 3, 7);
	public static final Block ORE_EMERALD = new BlockOreErebus2(2, Items.EMERALD, 0, 1, 1, 3, 7);
	public static final Block ORE_LAPIS = new BlockOreErebus2(1, Items.DYE, 4, 4, 9, 2, 5);
	public static final Block ORE_QUARTZ = new BlockOreErebus2(0, Items.QUARTZ, 0, 1, 1, 2, 5);
	public static final Block ORE_COPPER = new BlockOreErebus(1);
	public static final Block ORE_SILVER = new BlockOreErebus(2);
	public static final Block ORE_TIN = new BlockOreErebus(1);
	public static final Block ORE_LEAD = new BlockOreErebus(1);
	public static final Block ORE_ALUMINIUM = new BlockOreErebus(1);
	public static final Block ORE_JADE = new BlockOreErebus2(2, ModItems.MATERIALS, EnumErebusMaterialsType.JADE.ordinal(), 1, 1, 3, 7);
	public static final Block ORE_ENCRUSTED_DIAMOND = new BlockOreErebus2(2, Items.DIAMOND, 0, 1, 1, 3, 7);
	public static final Block ORE_FOSSIL = new BlockOreErebus2(0, ModItems.MATERIALS, EnumErebusMaterialsType.SHARD_BONE.ordinal(), 1, 1, 0, 2);
	public static final Block ORE_GNEISS = new BlockOreErebus2(0, ModItems.MATERIALS, EnumErebusMaterialsType.GNEISS_ROCK.ordinal(), 1, 1, 0, 2);
	public static final Block ORE_PETRIFIED_WOOD = new BlockOreErebus2(0, ModItems.MATERIALS, EnumErebusMaterialsType.PETRIFIED_WOOD.ordinal(), 1, 1, 0, 2);
	public static final Block ORE_TEMPLE = new BlockOreErebus(0); // what does this drop?
	public static final Block AMBER = new BlockAmber();
	public static final Block MUD = new BlockMud();
	public static final Block QUICK_SAND = new BlockQuickSand();
	public static final Block RED_GEM = new BlockRedGem();

	public static final Block CROP_TURNIP = new BlockTurnip().setCreativeTab(ModTabs.PLANTS);
	public static final Block CROP_CABBAGE = new BlockCabbage().setCreativeTab(ModTabs.PLANTS);
	public static final Block JADE_BERRY_BUSH = new BlockBerryBush("JADE");
	public static final Block HEART_BERRY_BUSH = new BlockBerryBush("HEART");
	public static final Block SWAMP_BERRY_BUSH = new BlockBerryBush("SWAMP");
	public static final Block DARK_FRUIT_VINE = new BlockDarkFruitVine();
	public static final Block PRICKLY_PEAR = new BlockPricklyPear();
	public static final Block GIANT_FLOWER = new BlockGiantFlower();
	public static final Block GIANT_FLOWER_STIGMA = new BlockStigma();
	public static final Block SMALL_PLANT = new BlockSmallPlant();
	public static final Block THORNS = new BlockThorns();
	public static final BlockDoubleHeightPlant DOUBLE_PLANT = new BlockDoubleHeightPlant();

	//MUSHROOMS
	public static final Block DARK_CAPPED_MUSHROOM = new BlockMushroomSmall(false);
	public static final Block SARCASTIC_CZECH_MUSHROOM = new BlockMushroomSmall(true);
	public static final Block GRANDMAS_SHOES_MUSHROOM = new BlockMushroomSmall(true);
	public static final Block DUTCH_CAP_MUSHROOM = new BlockMushroomSmall(true);
	public static final Block KAIZERS_FINGERS_MUSHROOM = new BlockMushroomSmall(false);

	public static final Block DARK_CAPPED_MUSHROOM_BLOCK = new BlockErebusMushroomHuge(MapColor.OBSIDIAN, DARK_CAPPED_MUSHROOM);
	public static final Block SARCASTIC_CZECH_MUSHROOM_BLOCK = new BlockErebusMushroomHuge(MapColor.RED, SARCASTIC_CZECH_MUSHROOM);
	public static final Block GRANDMAS_SHOES_MUSHROOM_BLOCK = new BlockErebusMushroomHuge(MapColor.GREEN, GRANDMAS_SHOES_MUSHROOM );
	public static final Block DUTCH_CAP_MUSHROOM_BLOCK = new BlockErebusMushroomHuge(MapColor.YELLOW, DUTCH_CAP_MUSHROOM);
	public static final Block KAIZERS_FINGERS_MUSHROOM_BLOCK = new BlockErebusMushroomHuge(MapColor.BROWN, KAIZERS_FINGERS_MUSHROOM);

	//GLOWSHROOMS
	public static final Block GLOWSHROOM = new BlockGlowshroom();
	public static final Block GLOWSHROOM_STALK_MAIN = new BlockGlowshroomStalkMain();
	
	public static void init() {
		try {
			for (Field field : ModBlocks.class.getDeclaredFields()) {
				Object obj = field.get(null);
				if (obj instanceof Block) {
					Block block = (Block) obj;
					String name = field.getName().toLowerCase(Locale.ENGLISH);
					registerBlock(name, block);
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		EnumWood.init();
	}

	public static void registerBlock(String name, Block block) {
		BLOCKS.add(block);

		GameRegistry.register(block.setRegistryName(Reference.MOD_ID, name).setUnlocalizedName(Reference.MOD_ID + "." + name));

		ItemBlock item;
		if (block instanceof IHasCustomItem)
			item = ((IHasCustomItem) block).getItemBlock();
		else
			item = new ItemBlock(block);

		GameRegistry.register((ItemBlock) item.setRegistryName(Reference.MOD_ID, name).setUnlocalizedName(Reference.MOD_ID + "." + name));
	}

	public static void registerRenderers() {
		for (Block block : BLOCKS)
			if (block instanceof ISubBlocksBlock) {
				List<String> models = ((ISubBlocksBlock) block).getModels();
				for (int i = 0; i < models.size(); i++)
					ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(Reference.MOD_ID + ":blocks/" + models.get(i), "inventory"));
			} else {
				ResourceLocation name = block.getRegistryName();
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":blocks/" + name.getResourcePath(), "inventory"));
				if (block instanceof BlockLeavesErebus)
					ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(new IProperty[] { BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE }).build());
				if (block instanceof BlockSaplingErebus)
					ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(new IProperty[] { BlockSapling.TYPE }).build());
				if (block instanceof BlockDoorErebus)
					ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(new IProperty[] { BlockDoor.POWERED }).build());
			}
	}

	public static interface IHasCustomItem {
		ItemBlock getItemBlock();
	}

	public static interface ISubBlocksBlock {
		List<String> getModels();
	}
}