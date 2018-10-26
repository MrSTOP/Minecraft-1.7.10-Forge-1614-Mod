package com.github.mrstop.stdemo.tileentity;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLoader {

    public TileEntityLoader(FMLPreInitializationEvent event)
    {
        registerTileEntity(TileEntityMetalFurnace.class, "STDemo.MetalFurnace");
        registerTileEntity(TileEntityRedstoneFluxFurnace.class, "STDemo.RedstoneFluxFurnace");
        registerTileEntity(TileEntityWindmill.class, "STDemo.Windmill");
        registerTileEntity(TileEntityQuartzFurnace.class, "STDemo.QuartzFurnace");
    }

    private void registerTileEntity(Class <? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, id);
    }
}
