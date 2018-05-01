package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public class ItemSeedsColor extends ItemSeeds {

        public ItemSeedsColor()
        {
            super(BlockLoader.colorBlock, Blocks.farmland);
            this.setUnlocalizedName("seedsColor");
            this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iIconRegister)
    {
        this.itemIcon = iIconRegister.registerIcon("stdemo:seeds_color");
    }
}
