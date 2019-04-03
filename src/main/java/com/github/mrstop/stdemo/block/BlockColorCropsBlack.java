package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.core.util.STDemoHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockColorCropsBlack extends BlockColorCrops {

    public BlockColorCropsBlack() {
        super();
        super.icons = new IIcon[2];
        this.setUnlocalizedName("colorBlackBlock");
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        STDemoHelper.registerIconArray(iconRegister, icons, "color_black_");
    }
}
