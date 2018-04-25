package com.github.mrstop.stdemo.creativetab;


import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class CreativeTabsSTDemo extends CreativeTabs
{
    public CreativeTabsSTDemo()
    {
        super("STDemo");
        this.setBackgroundImageName("stdemo.png");//设置创作模式物品背景
    }

    @Override
    public Item getTabIconItem()//标签上显示的物品
    {
        return ItemLoader.goldenEgg;
    }

    @Override
    public boolean hasSearchBar()//是否有搜索框
    {
        return true;
    }


}
