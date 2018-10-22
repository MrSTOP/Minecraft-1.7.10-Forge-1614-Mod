package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.client.gui.GuiContainerDemo;
import com.github.mrstop.stdemo.client.gui.GuiMetalFurnace;
import com.github.mrstop.stdemo.tileentity.TileEntityMetalFurnace;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiElementLoader implements IGuiHandler {

    public GuiElementLoader()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(STDemo.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case STDemo.GUIIDMetalFurnace:
                TileEntityMetalFurnace tileEntityMetalFurnace = (TileEntityMetalFurnace) world.getTileEntity(x, y, z);
                return new ContainerMetalFurnace(entityPlayer.inventory, tileEntityMetalFurnace);
            case STDemo.GUIIDDemo:
                return new ContainerDemo(entityPlayer);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case STDemo.GUIIDMetalFurnace:
            TileEntityMetalFurnace tileEntityMetalFurnace = (TileEntityMetalFurnace) world.getTileEntity(x, y, z);
            return new GuiMetalFurnace(entityPlayer.inventory, tileEntityMetalFurnace);
            case STDemo.GUIIDDemo:
                return new GuiContainerDemo(new ContainerDemo(entityPlayer));
            default:
                return null;
        }
    }
}
