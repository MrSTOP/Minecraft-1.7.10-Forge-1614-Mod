package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public abstract class ItemSeedsColor extends ItemSeeds {

    public ItemSeedsColor(Block plant, String unlocalizedName) {
        this(plant, Blocks.farmland, unlocalizedName);
    }

    public ItemSeedsColor(Block plant, Block onBlock, String unlocalizedName) {
        this(plant, onBlock, unlocalizedName, CreativeTabsLoader.tabSTDemo);
    }

    public ItemSeedsColor(Block plant, Block onBlock, String unlocalizedName, CreativeTabs creativeTab) {
        super(plant, onBlock);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    abstract public void registerIcons(IIconRegister iIconRegister);
//        this.itemIcon = iIconRegister.registerIcon("stdemo:seeds_color");
}
