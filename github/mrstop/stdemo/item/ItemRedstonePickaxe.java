package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.sun.org.apache.regexp.internal.RE;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;

public class ItemRedstonePickaxe extends ItemPickaxe {

    public static final Item.ToolMaterial REDSTONE = EnumHelper.addToolMaterial("REDSTONE", 3, 16, 16.0F, 0.0F, 10);

    public ItemRedstonePickaxe()
    {
        super(REDSTONE);
        this.setUnlocalizedName("redstonePickaxe");
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:redstone_pickaxe");
    }
}
