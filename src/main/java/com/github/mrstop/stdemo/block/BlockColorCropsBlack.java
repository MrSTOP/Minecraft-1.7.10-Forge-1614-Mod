package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.core.util.STDemoHelper;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import java.util.Random;

public class BlockColorCropsBlack extends BlockColorCrops {

    public BlockColorCropsBlack() {
        super();
        super.icons = new IIcon[2];
        this.setUnlocalizedName("colorBlackBlock");
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        if (meta != maxGrowthStage) {
            return Item.getItemFromBlock(this);
        } else {
            return ItemLoader.seedsColorBlack;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        STDemoHelper.registerIconArray(iconRegister, icons, "color_crop_", 0, icons.length - 1);
        icons[icons.length - 1] = iconRegister.registerIcon(STDemo.MOD_DOMAIN + "color_black");
    }
}
