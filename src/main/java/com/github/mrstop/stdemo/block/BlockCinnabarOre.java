package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockCinnabarOre extends Block {
    protected BlockCinnabarOre() {
        super(Material.rock);
        this.setUnlocalizedName("cinnabarOre");
        this.setHardness(0.8F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(STDemo.MOD_DOMAIN + "cinnabar_ore");
    }
}
