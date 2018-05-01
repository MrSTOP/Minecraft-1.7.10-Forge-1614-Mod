package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.entity.EntityExplosionEgg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemExplosionEgg extends Item{

    public ItemExplosionEgg()//构造方法
    {
        super();
        this.setUnlocalizedName("explosionEgg");//国际化名称
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setMaxStackSize(32);//最大堆叠数量
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World worldIn, EntityPlayer entityPlayer)
    {
        if (!entityPlayer.capabilities.isCreativeMode)
        {
            --itemStack.stackSize;
        }
        if (!worldIn.isRemote)
        {
            worldIn.spawnEntityInWorld(new EntityExplosionEgg(worldIn, entityPlayer));
        }
        return itemStack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)//注册材质
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:explosion_egg");
    }
}
