package erebus.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import erebus.block.silo.TileEntitySiloTank;
import erebus.blocks.BlockPetrifiedChest;
import erebus.entity.EntityAnimatedBambooCrate;
import erebus.entity.EntityBlackAnt;
import erebus.inventory.ContainerAnimatedBambooCrate;
import erebus.inventory.ContainerAntInventory;
import erebus.inventory.ContainerBambooCrate;
import erebus.inventory.ContainerColossalCrate;
import erebus.inventory.ContainerComposter;
import erebus.inventory.ContainerExtenderThingy;
import erebus.inventory.ContainerHoneyComb;
import erebus.inventory.ContainerLiquifier;
import erebus.inventory.ContainerPetrifiedCraftingTable;
import erebus.inventory.ContainerPetrifiedWoodChest;
import erebus.inventory.ContainerSilo;
import erebus.inventory.ContainerSmoothieMaker;
import erebus.inventory.ContainerUmberFurnace;
import erebus.lib.Reference;
import erebus.tileentity.TileEntityBambooBridge;
import erebus.tileentity.TileEntityBambooCrate;
import erebus.tileentity.TileEntityBambooPipe;
import erebus.tileentity.TileEntityBambooPipeExtract;
import erebus.tileentity.TileEntityBones;
import erebus.tileentity.TileEntityComposter;
import erebus.tileentity.TileEntityErebusAltar;
import erebus.tileentity.TileEntityErebusAltarEmpty;
import erebus.tileentity.TileEntityErebusAltarHealing;
import erebus.tileentity.TileEntityErebusAltarLightning;
import erebus.tileentity.TileEntityErebusAltarRepair;
import erebus.tileentity.TileEntityErebusAltarXP;
import erebus.tileentity.TileEntityExtenderThingy;
import erebus.tileentity.TileEntityFluidJar;
import erebus.tileentity.TileEntityGaeanKeystone;
import erebus.tileentity.TileEntityGlowingJar;
import erebus.tileentity.TileEntityHoneyComb;
import erebus.tileentity.TileEntityLiquifier;
import erebus.tileentity.TileEntityOfferingAltar;
import erebus.tileentity.TileEntityPetrifiedWoodChest;
import erebus.tileentity.TileEntityPreservedBlock;
import erebus.tileentity.TileEntitySmoothieMaker;
import erebus.tileentity.TileEntityTempleTeleporter;
import erebus.tileentity.TileEntityUmberFurnace;
import erebus.tileentity.TileEntityUmberGolemStatue;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler {

	public static enum GuiID {
		BAMBOO_CRATE,
		COLOSSAL_CRATE,
		PETRIFIED_CRAFT,
		UMBER_FURNACE,
		PETRIFIED_CHEST,
		ANIMATED_BAMBOO_CRATE,
		EXTENDER_THINGY,
		HONEY_COMB,
		ANT_INVENTORY,
		SILO_INVENTORY,
		COMPOSTER,
		SMOOTHIE_MAKER, 
		LIQUIFIER;
	}

	public final int[][] places = new int[][] { { 1, 0, 0 }, { 1, 0, 1 }, { 0, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 }, { 0, 1, 1 }, { 0, 1, 0 }, { 0, 0, 0 } };

	public void registerKeyHandlers() {
		// Unused server side. -- see ClientProxy for implementation
	}

	public void registerItemAndBlockRenderers() {
	}

	public void setCustomStateMap(Block block, StateMap stateMap) {
	}

	public void postInit() {		
	}

	public void registerItemAndBlockColourRenderers() {
	}
	
	public void registerTileEntities() {
		registerTileEntity(TileEntityGaeanKeystone.class, "gaean_keystone");
		registerTileEntity(TileEntityBambooCrate.class, "bamboo_crate");
		registerTileEntity(TileEntityUmberFurnace.class, "umber_furnace");
		registerTileEntity(TileEntityErebusAltar.class, "altar_of_crafting");
		registerTileEntity(TileEntityErebusAltarEmpty.class, "altar_empty");
		registerTileEntity(TileEntityErebusAltarHealing.class, "altar_of_healing");
		registerTileEntity(TileEntityErebusAltarLightning.class, "altar_of_lighting");
		registerTileEntity(TileEntityErebusAltarRepair.class, "altar_of_repair");
		registerTileEntity(TileEntityErebusAltarXP.class, "altar_of_xp");
		registerTileEntity(TileEntityGlowingJar.class, "glowing_jar");
		registerTileEntity(TileEntityBambooBridge.class, "bamboo_bridge");
		registerTileEntity(TileEntityUmberGolemStatue.class, "umber_golem_statue");
		registerTileEntity(TileEntityPetrifiedWoodChest.class, "petrified_wood_chest");
		registerTileEntity(TileEntityBones.class, "block_of_bones");
		registerTileEntity(TileEntityExtenderThingy.class, "extender_thingy");
		registerTileEntity(TileEntityFluidJar.class, "fluid_jar");
		registerTileEntity(TileEntityHoneyComb.class, "honey_comb");
		registerTileEntity(TileEntitySiloTank.class, "silo_tank");
		registerTileEntity(TileEntityComposter.class, "composter");
		registerTileEntity(TileEntityOfferingAltar.class, "offering_altar");
		registerTileEntity(TileEntitySmoothieMaker.class, "smoothie_maker");
		registerTileEntity(TileEntityTempleTeleporter.class, "temple_teleporter");
		registerTileEntity(TileEntityPreservedBlock.class, "preserved_block");
		//registerTileEntity(TileEntityArmchair.class, "armchair");
		registerTileEntity(TileEntityBambooPipe.class, "bamboo_pipe");
		registerTileEntity(TileEntityBambooPipeExtract.class, "bamboo_pipe_extract");
		registerTileEntity(TileEntityLiquifier.class, "liquifier");
	}

	private void registerTileEntity(Class<? extends TileEntity> cls, String baseName) {
		GameRegistry.registerTileEntity(cls, Reference.MOD_ID + "." + baseName);
	}

	public void spawnCustomParticle(String particleName, World world, double x, double y, double z, double vecX, double vecY, double vecZ) {	
	}

	public void registerEnitityRenderers() {
	}
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		GuiID guiID = GuiID.values()[ID];
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		Entity entity = world.getEntityByID(x);

		switch (guiID) {
			case ANIMATED_BAMBOO_CRATE:
				if (entity != null && entity instanceof EntityAnimatedBambooCrate)
					return new ContainerAnimatedBambooCrate(player.inventory, (EntityAnimatedBambooCrate) entity);
			case ANT_INVENTORY:
				if (entity != null && entity instanceof EntityBlackAnt)
					return new ContainerAntInventory(player.inventory, (EntityBlackAnt) entity);
			case BAMBOO_CRATE:
				return new ContainerBambooCrate(player.inventory, (TileEntityBambooCrate) tile);
			case COLOSSAL_CRATE:
				List<TileEntityBambooCrate> list = new ArrayList<TileEntityBambooCrate>();
				for (int[] place : places) {
					TileEntity te = world.getTileEntity(pos.add(place[0], place[1], place[2]));
					if (te != null && te instanceof TileEntityBambooCrate)
						list.add((TileEntityBambooCrate) te);
					else
						return null;
				}
				return new ContainerColossalCrate(player.inventory, list);
			case COMPOSTER:
				return new ContainerComposter(player.inventory, (TileEntityComposter) tile);
			case EXTENDER_THINGY:
				return new ContainerExtenderThingy(player.inventory, (TileEntityExtenderThingy) world.getTileEntity(pos));
			case HONEY_COMB:
				return new ContainerHoneyComb(player.inventory, (TileEntityHoneyComb) tile);
			case PETRIFIED_CHEST:
				return new ContainerPetrifiedWoodChest(player.inventory, getContainer(world, pos, false), player);
			case PETRIFIED_CRAFT:
				return new ContainerPetrifiedCraftingTable(player.inventory, world, pos);
			case SILO_INVENTORY:
				return new ContainerSilo(player.inventory, (TileEntitySiloTank) tile);
			case SMOOTHIE_MAKER:
				return new ContainerSmoothieMaker(player.inventory, (TileEntitySmoothieMaker) tile);
			case UMBER_FURNACE:
				return new ContainerUmberFurnace(player.inventory, (TileEntityUmberFurnace) tile);
			case LIQUIFIER:
				return new ContainerLiquifier(player.inventory, (TileEntityLiquifier) tile);
			default:
				return null;
		}
	}

	@Nullable
	public ILockableContainer getContainer(World world, BlockPos pos, boolean allowBlocking) {
		TileEntity tileentity = world.getTileEntity(pos);
		IBlockState state = world.getBlockState(pos);
		Block blockIn = state.getBlock();

		if (!(tileentity instanceof TileEntityPetrifiedWoodChest)) {
			return null;
		} else {
			ILockableContainer ilockablecontainer = (TileEntityPetrifiedWoodChest) tileentity;

			if (!allowBlocking && ((BlockPetrifiedChest)blockIn).isBlocked(world, pos)) {
				return null;
			} else {
				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
					BlockPos blockpos = pos.offset(enumfacing);
					Block block = world.getBlockState(blockpos).getBlock();

					if (block instanceof BlockPetrifiedChest) {
						if (((BlockPetrifiedChest)block).isBlocked(world, blockpos)) {
							return null;
						}

						TileEntity tileentity1 = world.getTileEntity(blockpos);

						if (tileentity1 instanceof TileEntityPetrifiedWoodChest) {
							if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
								ilockablecontainer = new InventoryLargeChest("container.petrifiedChestDouble", ilockablecontainer, (TileEntityPetrifiedWoodChest) tileentity1);
							} else {
								ilockablecontainer = new InventoryLargeChest("container.petrifiedChestDouble", (TileEntityPetrifiedWoodChest) tileentity1, ilockablecontainer);
							}
						}
					}
				}

				return ilockablecontainer;
			}
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}