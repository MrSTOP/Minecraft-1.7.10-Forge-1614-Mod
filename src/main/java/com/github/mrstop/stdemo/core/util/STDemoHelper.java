package com.github.mrstop.stdemo.core.util;

import com.github.mrstop.stdemo.STDemo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class STDemoHelper {
    public static void dropItemStackInWorld(World world, ItemStack itemStack, int x, int y, int z, int delay) {
        float f1 = 0.7F;
        double rX = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        double rY = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        double rZ = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        EntityItem entityItem = new EntityItem(world, x + rX, y + rY, z + rZ, itemStack);
        entityItem.delayBeforeCanPickup = delay;
        world.spawnEntityInWorld(entityItem);
    }

    public static void registerIconArray(IIconRegister iconRegister, IIcon[] icons, String name) {
        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(STDemo.MOD_DOMAIN + name + i);
        }
    }
}
