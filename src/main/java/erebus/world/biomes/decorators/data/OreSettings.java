package erebus.world.biomes.decorators.data;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import erebus.ModBlocks;
import erebus.world.feature.decoration.WorldGenErebusMinable;

public final class OreSettings {
	private static final byte[] checkX = new byte[] { -1, -1, 1, 1, 0, 0 }, checkY = new byte[] { 0, 0, 0, 0, -1, 1 }, checkZ = new byte[] { -1, 1, -1, 1, 0, 0 };

	private static final WorldGenErebusMinable genMinable = new WorldGenErebusMinable();

	private OreType oreType;
	private float chance;
	private byte minIterations, maxIterations;
	private byte minAmount, maxAmount;
	private byte minY, maxY;
	private byte checkArea;

	public OreSettings setType(OreType oreType) {
		this.oreType = oreType;
		return this;
	}

	public OreSettings reset() {
		chance = 1F;
		minY = 5;
		maxY = 112;
		checkArea = 3;
		return this;
	}

	public OreSettings setChance(float chance) {
		this.chance = chance;
		return this;
	}

	public OreSettings setIterations(int iterations) {
		minIterations = maxIterations = (byte) iterations;
		return this;
	}

	public OreSettings setIterations(int minIterations, int maxIterations) {
		this.minIterations = (byte) minIterations;
		this.maxIterations = (byte) maxIterations;
		return this;
	}

	public OreSettings setOreAmount(int amount) {
		minAmount = maxAmount = (byte) amount;
		return this;
	}

	public OreSettings setOreAmount(int minAmount, int maxAmount) {
		this.minAmount = (byte) minAmount;
		this.maxAmount = (byte) maxAmount;
		return this;
	}

	public OreSettings setY(int minY, int maxY) {
		this.minY = (byte) minY;
		this.maxY = (byte) maxY;
		return this;
	}

	public OreSettings setCheckArea(int checkArea) {
		this.checkArea = (byte) checkArea;
		return this;
	}

	public void generate(World world, Random rand, int x, int z) {
		if (rand.nextFloat() >= chance)
			return;

		int iterations = minIterations + rand.nextInt(maxIterations - minIterations + 1);

		for (int iteration = 0, attempt, xx, yy, zz, testX, testY, testZ, oreAmount, a; iteration < iterations; iteration++)
			for (attempt = 0; attempt < 12; attempt++) {
				xx = x + rand.nextInt(16);
				zz = z + rand.nextInt(16);
				yy = minY + rand.nextInt(Math.max(1, 1 + maxY - minY));

				for (a = 0; a < 6; a++) {
					testX = xx + checkX[a] * checkArea;
					testY = yy + checkY[a] * checkArea;
					testZ = zz + checkZ[a] * checkArea;

					if (testX >> 4 != x >> 4)
						testX = x;
					if (testZ >> 4 != z >> 4)
						testZ = z;

					if (world.isAirBlock(new BlockPos(testX, testY, testZ))) {
						if ((oreAmount = minAmount + rand.nextInt(maxAmount - minAmount + 1)) == 1) {
							if (world.getBlockState(new BlockPos(xx, yy, zz)) == ModBlocks.UMBERSTONE)
								world.setBlockState(new BlockPos(xx, yy, zz), oreType.oreBlock.getStateFromMeta(oreType.oreMeta), 16);
						} else {
							genMinable.prepare(oreType.oreBlock, oreType.oreMeta, oreAmount);
							genMinable.generate(world, rand, new BlockPos(xx, yy, zz));
						}

						attempt = 99;
						break;
					}
				}
			}
	}

	public static enum OreType {
		COAL(ModBlocks.ORE_COAL, true),
		IRON(ModBlocks.ORE_IRON, true),
		GOLD(ModBlocks.ORE_GOLD, true),
		LAPIS(ModBlocks.ORE_LAPIS, true),
		EMERALD(ModBlocks.ORE_EMERALD, true),
		DIAMOND(ModBlocks.ORE_DIAMOND, true),
		DIAMOND_ENCRUSTED(ModBlocks.ORE_ENCRUSTED_DIAMOND, true),
		JADE(ModBlocks.ORE_JADE, true),
		PETRIFIED_WOOD(ModBlocks.ORE_PETRIFIED_WOOD, true),
		FOSSIL(ModBlocks.ORE_FOSSIL, true),
		ALUMINIUM(ModBlocks.ORE_ALUMINIUM, false),
		COPPER(ModBlocks.ORE_COPPER, false),
		LEAD(ModBlocks.ORE_LEAD, false),
		SILVER(ModBlocks.ORE_SILVER, false),
		TIN(ModBlocks.ORE_TIN, false),
		GNEISS(ModBlocks.ORE_GNEISS, true),
		QUARTZ(ModBlocks.ORE_QUARTZ, true),
		TEMPLE(ModBlocks.ORE_TEMPLE, true);

		final Block oreBlock;
		final byte oreMeta;
		boolean isEnabled;

		OreType(Block oreBlock, int oreMeta, boolean isEnabled) {
			this.oreBlock = oreBlock;
			this.oreMeta = (byte) oreMeta;
			this.isEnabled = isEnabled;
		}

		OreType(Block oreBlock, boolean isEnabled) {
			this(oreBlock, 0, isEnabled);
		}

		public boolean isEnabled() {
			return isEnabled;
		}

		public void setEnabled(boolean flag) {
			isEnabled = flag;
		}

		public void setupDefault(OreSettings settings, boolean extraOres) {
			settings.reset();
			settings.setType(this);

			switch (this) {
				case COAL:
					settings.setIterations(extraOres ? 6 : 8).setOreAmount(9, 12);
					break;
				case IRON:
					settings.setIterations(extraOres ? 7 : 9, extraOres ? 8 : 10).setOreAmount(6, 10);
					break;
				case GOLD:
					settings.setIterations(extraOres ? 4 : 5).setOreAmount(6);
					break;
				case LAPIS:
					settings.setIterations(3).setOreAmount(5).setCheckArea(2);
					break;
				case EMERALD:
					settings.setChance(0.33F).setIterations(0, 2).setOreAmount(3).setCheckArea(1);
					break;
				case DIAMOND:
					settings.setChance(0.66F).setIterations(2, 4).setOreAmount(1).setCheckArea(1);
					break;
				case DIAMOND_ENCRUSTED:
					settings.setChance(0F);
					break;
				case JADE:
					settings.setChance(0.5F).setIterations(1, 4).setOreAmount(4).setCheckArea(2);
					break;
				case PETRIFIED_WOOD:
					settings.setIterations(extraOres ? 1 : 2, extraOres ? 3 : 4).setOreAmount(7, 9).setCheckArea(2);
					break;
				case FOSSIL:
					settings.setChance(0.25F).setIterations(1, 2).setOreAmount(8, 11).setY(36, 112);
					break;
				case ALUMINIUM:
					settings.setChance(1F).setIterations(2, 3).setOreAmount(3, 4).setCheckArea(2);
					break;
				case COPPER:
					settings.setChance(1F).setIterations(7, 9).setOreAmount(5, 7);
					break;
				case LEAD:
					settings.setChance(1F).setIterations(4).setOreAmount(3).setCheckArea(2);
					break;
				case SILVER:
					settings.setChance(1F).setIterations(5).setOreAmount(6, 8);
					break;
				case TIN:
					settings.setChance(1F).setIterations(2, 4).setOreAmount(3, 4).setCheckArea(2);
					break;
				case QUARTZ:
					settings.setIterations(extraOres ? 1 : 2, extraOres ? 3 : 4).setOreAmount(7, 9).setCheckArea(2);
					break;
				case GNEISS:
					settings.setIterations(extraOres ? 1 : 2, extraOres ? 3 : 4).setOreAmount(2, 3).setCheckArea(2);
					break;
				case TEMPLE:
					settings.setIterations(extraOres ? 1 : 2, extraOres ? 3 : 4).setOreAmount(7, 9).setCheckArea(2);
					break;
			}
		}
	}
}