package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemArmorRedstoneBoots extends ItemRedstoneArmor
{
    public ItemArmorRedstoneBoots()
    {
        super(ItemRedstoneArmor.REDSTONE_ARMOR, 0, 3);
        this.setUnlocalizedName("redstoneBoots");
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:redstone_boots");
    }
}