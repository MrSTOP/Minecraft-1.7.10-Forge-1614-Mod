package com.mod.tuto.init;

import com.mod.tuto.Reference;
import com.mod.tuto.entity.EntityTuto;
import com.mod.tuto.entity.EntityTuto2;
import com.mod.tuto.handlers.EntityHandler;
import com.mod.tuto.tileentity.TileEntityBlockTesr;

import cpw.mods.fml.common.registry.GameRegistry;

public class EntityMod
{
    public static void init()
    {
        EntityHandler.registerMonster(EntityTuto.class, "MobTuto");
        EntityHandler.registerAmbiants(EntityTuto2.class, "MobTuto2");
        
        //Block Tesr
        GameRegistry.registerTileEntity(TileEntityBlockTesr.class, Reference.MOD_ID + ":blockTesr");
    }
}