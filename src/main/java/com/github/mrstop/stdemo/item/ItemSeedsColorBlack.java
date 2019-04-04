package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.block.BlockLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemSeedsColorBlack extends ItemSeedsColor {
    public ItemSeedsColorBlack() {
        super(BlockLoader.blockColorFlowerBlack, "seedsColorBlack");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iIconRegister) {
        this.itemIcon = iIconRegister.registerIcon("seeds_color_black");
    }
}
