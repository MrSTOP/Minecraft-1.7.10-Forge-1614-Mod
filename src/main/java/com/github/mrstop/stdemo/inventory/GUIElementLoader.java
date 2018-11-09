package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.client.gui.*;
import com.github.mrstop.stdemo.tileentity.*;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIElementLoader implements IGuiHandler {

    public GUIElementLoader() {
        NetworkRegistry.INSTANCE.registerGuiHandler(STDemo.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        switch (ID) {
            case STDemo.GUIIDMetalFurnace:
                //TileEntityMetalFurnace tileEntityMetalFurnace = (TileEntityMetalFurnace) world.getTileEntity(x, y, z);
                return new ContainerMetalFurnace(entityPlayer.inventory, (TileEntityMetalFurnace)tileEntity);
            case STDemo.GUIIDMachineRedstoneFluxFurnace:
                //TileEntityMachineRedstoneFluxFurnace tileEntityRedstoneFluxFurnace = (TileEntityMachineRedstoneFluxFurnace) world.getTileEntity(x, y, z);
                return new ContainerMachineRedstoneFluxFurnace(entityPlayer.inventory, (TileEntityMachineRedstoneFluxFurnace)tileEntity);
            case STDemo.GUIIDWindmill:
                //TileEntityWindmill tileEntityWindmill = (TileEntityWindmill)world.getTileEntity(x, y, z);
                return new ContainerWindmill(entityPlayer.inventory, (TileEntityWindmill)tileEntity);
            case STDemo.GUIDMachineElectrolyticMachine:
                //TileEntityMachineElectrolyticMachine tileEntityElectrolyticMachine = (TileEntityMachineElectrolyticMachine)world.getTileEntity(x, y, z);
                return new ContainerMachineElectrolyticMachine(entityPlayer.inventory, (TileEntityMachineElectrolyticMachine)tileEntity);
            case STDemo.GUIIDMachineCalciner:
                return new ContainerMachineCalciner(entityPlayer.inventory, (TileEntityMachineCalciner)tileEntity);
            case STDemo.GUIIDDemo:
                return new ContainerDemo(entityPlayer);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        switch (ID) {
            case STDemo.GUIIDMetalFurnace:
//                TileEntityMetalFurnace tileEntityMetalFurnace = (TileEntityMetalFurnace) world.getTileEntity(x, y, z);
                return new GUIMetalFurnace(entityPlayer.inventory, (TileEntityMetalFurnace)tileEntity);
            case STDemo.GUIIDMachineRedstoneFluxFurnace:
//                TileEntityMachineRedstoneFluxFurnace tileEntityRedstoneFluxFurnace = (TileEntityMachineRedstoneFluxFurnace) world.getTileEntity(x, y, z);
                return new GUIMachineRedstoneFluxFurnace(entityPlayer.inventory, (TileEntityMachineRedstoneFluxFurnace)tileEntity);
            case STDemo.GUIIDWindmill:
//                TileEntityWindmill tileEntityWindmill = (TileEntityWindmill)world.getTileEntity(x, y, z);
                return new GUIWindmill(entityPlayer.inventory, (TileEntityWindmill)tileEntity);
            case STDemo.GUIDMachineElectrolyticMachine:
//                TileEntityMachineElectrolyticMachine tileEntityElectrolyticMachine = (TileEntityMachineElectrolyticMachine)world.getTileEntity(x, y, z);
                return new GUIMachineElectrolyticMachine(entityPlayer.inventory, (TileEntityMachineElectrolyticMachine)tileEntity);
            case STDemo.GUIIDMachineCalciner:
                return new GUIMachineCalciner(entityPlayer.inventory, (TileEntityMachineCalciner)tileEntity);
            case STDemo.GUIIDDemo:
                return new GUIContainerDemo(new ContainerDemo(entityPlayer));
            default:
                return null;
        }
    }
}
