package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import com.github.mrstop.stdemo.entity.EntityGoldenEgg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemGoldenEgg extends Item{

    public ItemGoldenEgg()//构造方法
    {
        super();
        this.setUnlocalizedName("goldenEgg");//国际化名称
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setMaxStackSize(32);//最大堆叠数量
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (!entityPlayer.capabilities.isCreativeMode)
        {
            --itemStack.stackSize;
        }
        if (!world.isRemote)
        {
            if (entityPlayer.isSneaking())
            {
                int blockX = (int)entityPlayer.posX;
                int blockY = (int)entityPlayer.posY;
                int blockZ = (int)entityPlayer.posZ;
                int guiID = STDemo.GUIIDDemo;
                entityPlayer.openGui(STDemo.instance, guiID, world, blockX, blockY, blockZ);
            }
            world.spawnEntityInWorld(new EntityGoldenEgg(world, entityPlayer));
        }
        return itemStack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)//注册材质
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:golden_egg");
    }
}
