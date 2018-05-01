package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemColor extends Item {

    public ItemColor()
    {
        super();
        this.setUnlocalizedName("Color");
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:color");
    }
}
