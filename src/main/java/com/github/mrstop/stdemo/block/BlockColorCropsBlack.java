package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class BlockColorCropsBlack extends BlockColorFlower {

    public BlockColorCropsBlack() {
        super("colorBlackBlock");
    }

    @Override
    protected Item getSeed() {
        return ItemLoader.seedsColorBlack;
    }

    @Override
    protected void registerLastIcons(IIconRegister iIconRegister, int index) {
        this.icons[index] = iIconRegister.registerIcon("color_crop_black");
    }
}
