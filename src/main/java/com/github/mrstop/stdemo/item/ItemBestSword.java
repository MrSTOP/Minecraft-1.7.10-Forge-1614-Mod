package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemSword;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.common.util.EnumHelper;

public class ItemBestSword extends ItemSword {

    public ItemBestSword()
    {
        super(EnumHelper.addToolMaterial("CHROME", 4, 3000, 20F, 50F, 30));
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setUnlocalizedName("bestSword");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:best_sword");
    }
}
