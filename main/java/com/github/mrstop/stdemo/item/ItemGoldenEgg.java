package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
//import net.minecraft.creativetab.CreativeTabs;

public class ItemGoldenEgg extends Item{

    public ItemGoldenEgg()//构造方法
    {
        super();
        this.setUnlocalizedName("goldenEgg");//国际化名称
        //this.setCreativeTab(CreativeTabs.tabBlock);//创造模式标签
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setMaxStackSize(32);//最大堆叠数量
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)//注册材质
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:golden_egg");
    }
}
