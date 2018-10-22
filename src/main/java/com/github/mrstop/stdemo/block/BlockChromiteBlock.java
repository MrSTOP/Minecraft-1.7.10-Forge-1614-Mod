package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockChromiteBlock extends Block {


    public BlockChromiteBlock()
    {
        super(Material.rock);
        this.setUnlocalizedName("chromiteOre");
        this.setHardness(0.8F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister BlockIcon)
    {
        this.blockIcon = BlockIcon.registerIcon("stdemo:chromite_ore");
    }
}
