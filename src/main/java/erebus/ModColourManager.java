package erebus;

import java.awt.Color;

import erebus.blocks.BlockSmallPlant;
import erebus.blocks.BlockSmallPlant.EnumSmallPlantType;
import erebus.blocks.BlockSwampVent;
import erebus.blocks.BlockWitherWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;

/*
 * @author Choonster - who is awesome btw!
 */

public class ModColourManager {
	private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

	/**
	 * Register the colour handlers.
	 */
	public static void registerColourHandlers() {
		final BlockColors blockColors = MINECRAFT.getBlockColors();
		final ItemColors itemColors = MINECRAFT.getItemColors();

		registerBlockColourHandlers(blockColors);
		registerItemColourHandlers(blockColors, itemColors);
	}

	/**
	 * Register the {@link IBlockColor} handlers.
	 *
	 * @param blockColors The BlockColors instance
	 */
	private static void registerBlockColourHandlers(final BlockColors blockColors) {
		// Use the grass colour of the biome or the default grass colour
		final IBlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> {
			if (blockAccess != null && pos != null) {
				return BiomeColorHelper.getGrassColorAtPos(blockAccess, pos);
			}

			return ColorizerGrass.getGrassColor(0.5D, 1.0D);
		};

		final IBlockColor witheWebColourHandler = (state, blockAccess, pos, tintIndex) -> {
			if (blockAccess != null && pos != null) {
				return new Color(0, 0, 0).getRGB() & 0x00ffffff;
			}

			return new Color(0, 0, 0).getRGB() & 0x00ffffff;
		};

		blockColors.registerBlockColorHandler(grassColourHandler, ModBlocks.SMALL_PLANT);
		//blockColors.registerBlockColorHandler(grassColourHandler, ModBlocks.ALGAE);
		blockColors.registerBlockColorHandler(grassColourHandler, ModBlocks.SWAMP_VENT);
		blockColors.registerBlockColorHandler(witheWebColourHandler, ModBlocks.WITHER_WEB);
	}

	/**
	 * Register the {@link IItemColor} handlers
	 *
	 * @param blockColors The BlockColors instance
	 * @param itemColors  The ItemColors instance
	 */
	private static void registerItemColourHandlers(final BlockColors blockColors, final ItemColors itemColors) {
		// Use the Block's colour handler for an ItemBlock
		final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
			@SuppressWarnings("deprecation")
			final IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
			if(state.getBlock() instanceof BlockSmallPlant && (state.getValue(BlockSmallPlant.PLANT_TYPE) == EnumSmallPlantType.FIDDLE_HEAD ||state.getValue(BlockSmallPlant.PLANT_TYPE) == EnumSmallPlantType.FERN))
				return blockColors.colorMultiplier(state, null, null, tintIndex);
			
			if(state.getBlock() instanceof BlockSwampVent)
				return blockColors.colorMultiplier(state, null, null, tintIndex);
			
			if(state.getBlock() instanceof BlockWitherWeb)
				return blockColors.colorMultiplier(state, null, null, tintIndex);
			
			return -1;
		};

		itemColors.registerItemColorHandler(itemBlockColourHandler, ModBlocks.SMALL_PLANT);
		//itemColors.registerItemColorHandler(itemBlockColourHandler, ModBlocks.ALGAE);
		itemColors.registerItemColorHandler(itemBlockColourHandler, ModBlocks.SWAMP_VENT);
		itemColors.registerItemColorHandler(itemBlockColourHandler, ModBlocks.WITHER_WEB);
	}
}

