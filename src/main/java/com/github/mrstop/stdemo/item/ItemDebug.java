package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemDebug extends Item {
    public ItemDebug() {
        super();
        this.setUnlocalizedName("debugItem");
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }
        entityPlayer.addChatMessage(new ChatComponentText("ItemStack: " + itemStack + " EntityPlayer: " + entityPlayer + " World: " + world + " X: " + x + " Y: " + y + " Z: " + z + " HitX: " + hitX + " HitY: " + hitY + " HitZ: " + hitZ));
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(STDemo.MOD_DOMAIN + "debug");
    }
}
