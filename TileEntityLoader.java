package com.github.mrstop.stdemo.tileentity;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLoader {

    public TileEntityLoader(FMLPreInitializationEvent event)
    {
        registerTileEntity(TileEntityMetalFurnace.class, "MetalFurnace");
        registerTileEntity(TileEntityRedstoneFluxFurnace.class, "RedstoneFluxFurnace");
        registerTileEntity(TileEntityWindmill.class, "Windmill");
    }

    public void registerTileEntity(Class <? extends TileEntity> tileentityClass, String id)
    {
        GameRegistry.registerTileEntity(tileentityClass, id);
    }
}
