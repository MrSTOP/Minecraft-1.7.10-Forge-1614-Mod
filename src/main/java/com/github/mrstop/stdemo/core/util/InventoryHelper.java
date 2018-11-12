package com.github.mrstop.stdemo.core.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InventoryHelper {
    public static void dropInventoryItems(World world, IInventory iInventory, int x, int y, int z){
        for (int i = 0; i < iInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = iInventory.getStackInSlot(i);
            if (itemStack != null && itemStack.stackSize > 0){
                float f1 = 0.7F;
                double rX = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
                double rY = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
                double rZ = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
                EntityItem entityItem = new EntityItem(world, x + rX, y + rY, z + rZ, itemStack);
                entityItem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityItem);
            }
        }
    }
}
