package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class BlockColorFlowerBlack extends BlockColorFlower {

    public BlockColorFlowerBlack() {
        super("colorFlowerBlackBlock");
    }

    @Override
    protected Item getSeed() {
        return ItemLoader.seedsColorBlack;
    }

    @Override
    protected void registerLastIcons(IIconRegister iIconRegister, int index) {
        this.icons[index] = iIconRegister.registerIcon(STDemo.MOD_DOMAIN + "color_flower_black");
    }
}
