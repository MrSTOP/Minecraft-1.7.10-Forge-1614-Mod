package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class BlockColorFlowerBlue extends BlockColorFlower {
    public BlockColorFlowerBlue() {
        super("colorFlowerBlueBlock");
    }

    @Override
    protected Item getSeed() {
        return ItemLoader.seedsColorBlue;
    }

    @Override
    protected void registerLastIcons(IIconRegister iIconRegister, int index) {
        this.icons[index] = iIconRegister.registerIcon(STDemo.MOD_DOMAIN + "color_flower_blue");
    }
}
