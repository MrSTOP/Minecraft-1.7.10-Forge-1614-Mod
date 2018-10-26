package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.client.gui.GuiContainerDemo;
import com.github.mrstop.stdemo.client.gui.GuiMetalFurnace;
import com.github.mrstop.stdemo.client.gui.GuiRedstoneFluxFurnace;
import com.github.mrstop.stdemo.client.gui.GuiWindmill;
import com.github.mrstop.stdemo.tileentity.TileEntityMetalFurnace;
import com.github.mrstop.stdemo.tileentity.TileEntityRedstoneFluxFurnace;
import com.github.mrstop.stdemo.tileentity.TileEntityWindmill;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiElementLoader implements IGuiHandler {

    public GuiElementLoader() {
        NetworkRegistry.INSTANCE.registerGuiHandler(STDemo.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch (ID) {
            case STDemo.GUIIDMetalFurnace:
                TileEntityMetalFurnace tileEntityMetalFurnace = (TileEntityMetalFurnace) world.getTileEntity(x, y, z);
                return new ContainerMetalFurnace(entityPlayer.inventory, tileEntityMetalFurnace);
            case STDemo.GUIIDRedstoneFluxFurnace:
                TileEntityRedstoneFluxFurnace tileEntityRedstoneFluxFurnace = (TileEntityRedstoneFluxFurnace) world.getTileEntity(x, y, z);
                return new ContainerRedstoneFluxFurnace(entityPlayer.inventory, tileEntityRedstoneFluxFurnace);
            case STDemo.GUIIDWindmill:
                TileEntityWindmill tileEntityWindmill = (TileEntityWindmill)world.getTileEntity(x, y, z);
                return new ContainerWindmill(entityPlayer.inventory, tileEntityWindmill);
            case STDemo.GUIIDDemo:
                return new ContainerDemo(entityPlayer);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch (ID) {
            case STDemo.GUIIDMetalFurnace:
                TileEntityMetalFurnace tileEntityMetalFurnace = (TileEntityMetalFurnace) world.getTileEntity(x, y, z);
                return new GuiMetalFurnace(entityPlayer.inventory, tileEntityMetalFurnace);
            case STDemo.GUIIDRedstoneFluxFurnace:
                TileEntityRedstoneFluxFurnace tileEntityRedstoneFluxFurnace = (TileEntityRedstoneFluxFurnace) world.getTileEntity(x, y, z);
                return new GuiRedstoneFluxFurnace(entityPlayer.inventory, tileEntityRedstoneFluxFurnace);
            case STDemo.GUIIDWindmill:
                TileEntityWindmill tileEntityWindmill = (TileEntityWindmill)world.getTileEntity(x, y, z);
                return new GuiWindmill(entityPlayer.inventory, tileEntityWindmill);
            case STDemo.GUIIDDemo:
                return new GuiContainerDemo(new ContainerDemo(entityPlayer));
            default:
                return null;
        }
    }
}
