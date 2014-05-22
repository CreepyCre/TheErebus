package erebus.core.helper;

import java.awt.Color;
import java.util.LinkedHashMap;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Utils {

	public static final int getFlowerMetadata(Object obj) {
		int meta = -1;
		if (obj instanceof ItemStack)
			meta = ((ItemStack) obj).getItemDamage();
		else if (obj instanceof Integer)
			meta = (Integer) obj;

		if (meta >= 2 && meta <= 8 || meta == 14)
			meta++;
		else if (meta >= 9 && meta <= 13)
			meta += 2;

		return meta;
	}

	public static final void dropStack(World world, int x, int y, int z, ItemStack is) {
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			float f = 0.7F;
			double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, is);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static final int getColour(int R, int G, int B) {
		return new Color(R, G, B).getRGB() & 0x00ffffff;
	}

	@SuppressWarnings("unchecked")
	public static final <T> T getTileEntity(IBlockAccess world, int x, int y, int z, Class<T> cls) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!cls.isInstance(tile))
			return null;
		return (T) tile;
	}

	public static final boolean addItemStackToInventory(IInventory inventory, ItemStack stack, int startIndex) {
		if (stack == null || inventory == null)
			return true;
		int openSlot = -1;
		for (int i = startIndex; i < inventory.getSizeInventory(); i++)
			if (compareStacks(stack, inventory.getStackInSlot(i), false) && inventory.getStackInSlot(i).getMaxStackSize() > inventory.getStackInSlot(i).stackSize) {
				final int hold = inventory.getStackInSlot(i).getMaxStackSize() - inventory.getStackInSlot(i).stackSize;
				if (hold >= stack.stackSize) {
					final ItemStack itemStack = inventory.getStackInSlot(i);
					itemStack.stackSize += stack.stackSize;
					stack = null;
					return true;
				}
				ItemStack itemStack2 = stack;
				itemStack2.stackSize -= hold;
				ItemStack itemStack3 = inventory.getStackInSlot(i);
				itemStack3.stackSize += hold;
			} else if (inventory.getStackInSlot(i) == null && openSlot == -1)
				openSlot = i;

		if (openSlot <= -1)
			return false;
		inventory.setInventorySlotContents(openSlot, stack);

		return true;
	}

	public static final boolean addItemStackToInventory(IInventory inventory, ItemStack stack) {
		return addItemStackToInventory(inventory, stack, 0);
	}

	public static final boolean compareStacks(ItemStack stack1, ItemStack stack2, boolean testSize) {
		if (stack1 == null || stack2 == null)
			return false;
		if (stack1.getItem() == stack2.getItem())
			if (stack1.getItemDamage() == stack2.getItemDamage())
				if (compareNBT(stack1, stack2))
					if (!testSize || stack1.stackSize == stack2.stackSize)
						return true;
		return false;
	}

	private static final boolean compareNBT(ItemStack stack1, ItemStack stack2) {
		if (stack1.hasTagCompound() && !stack2.hasTagCompound())
			return false;
		if (!stack1.hasTagCompound() && stack2.hasTagCompound())
			return false;
		if (!stack1.hasDisplayName() && !stack2.hasTagCompound())
			return true;
		else
			return stack1.getTagCompound().equals(stack2.getTagCompound());
	}

	public static final LinkedHashMap<Short, Short> getEnchantments(ItemStack stack) {
		LinkedHashMap<Short, Short> map = new LinkedHashMap<Short, Short>();
		NBTTagList list = stack.getItem() == Items.enchanted_book ? Items.enchanted_book.func_92110_g(stack) : stack.getEnchantmentTagList();

		if (list != null)
			for (int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound tag = list.getCompoundTagAt(i);
				map.put(tag.getShort("id"), tag.getShort("lvl"));
			}
		return map;
	}
}