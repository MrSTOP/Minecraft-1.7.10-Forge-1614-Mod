package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.fluid.FluidLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ItemBucketMercury extends ItemBucket {

    public ItemBucketMercury()
    {
        super(BlockLoader.fluidMercuryBlock);
        this.setContainerItem(Items.bucket);
        this.setUnlocalizedName("bucketMercury");
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        FluidContainerRegistry.registerFluidContainer(FluidLoader.fluidMercury, new ItemStack(this), FluidContainerRegistry.EMPTY_BUCKET);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:bucket_mercury");
    }

}
