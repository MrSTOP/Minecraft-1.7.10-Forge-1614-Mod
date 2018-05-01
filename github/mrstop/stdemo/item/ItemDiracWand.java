package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemDiracWand extends Item {

    public ItemDiracWand()
    {
        super();
        this.setUnlocalizedName("diracWand");//国际化名称
        //this.setCreativeTab(CreativeTabs.tabBlock);//创造模式标签
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setMaxStackSize(1);//最大堆叠数量
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase entityLivingBase1, EntityLivingBase entityLivingBase2)
    {
        itemStack.damageItem(1, entityLivingBase2);
        if (!entityLivingBase2.worldObj.isRemote)
        {
            return true;
        }
        float angle = (entityLivingBase2.rotationYaw / 180) * STDemo.PI;
        float x = 3F * -MathHelper.sin(angle);
        float y = 1F;
        float z = 3F * MathHelper.cos(angle);
        entityLivingBase1.setVelocity(x, y, z);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (!world.isRemote)
        {
            EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ, entityPlayer);
            float angle = (entityPlayer.rotationYaw / 180F) * STDemo.PI;
            float angle2 = (-entityPlayer.rotationPitch / 180F) * STDemo.PI;
            final float speed = 2F;
            entityTNTPrimed.motionX = speed * MathHelper.sin(angle2);
            entityTNTPrimed.motionY = speed * MathHelper.cos(angle2) * -MathHelper.sin(angle);
            entityTNTPrimed.motionZ = speed * MathHelper.cos(angle2) * MathHelper.cos(angle);
            world.spawnEntityInWorld(entityTNTPrimed);
        }
        return itemStack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)//注册材质
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:diracwand");
    }
}
