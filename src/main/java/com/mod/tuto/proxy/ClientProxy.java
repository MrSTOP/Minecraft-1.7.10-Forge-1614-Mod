package com.mod.tuto.proxy;

import com.mod.tuto.entity.EntityTuto;
import com.mod.tuto.init.RenderMod;
import com.mod.tuto.models.ModelTuto;
import com.mod.tuto.renderer.RenderBlockIsbrh;
import com.mod.tuto.renderer.TesrRenderInventory;
import com.mod.tuto.renderer.TileEntityBlockTesrSpecialRenderer;
import com.mod.tuto.renders.RenderTuto;
import com.mod.tuto.tileentity.TileEntityBlockTesr;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy
{
    public static int renderIsbrh;
    public static int renderTesr;
    
    @Override
    public void registerRenders()
    {
        RenderMod.init();
        
        //Block ISBRH
        renderIsbrh = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderBlockIsbrh());
        
        //Block TESR
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockTesr.class, new TileEntityBlockTesrSpecialRenderer());
        renderTesr = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new TesrRenderInventory());
    }
}