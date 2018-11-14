package com.github.mrstop.stdemo.client.render.tielentity;

import com.github.mrstop.stdemo.tileentity.TileEntityWindmill;
import cpw.mods.fml.client.registry.ClientRegistry;

public class TileEntityRenderLoader {
    public TileEntityRenderLoader(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindmill.class, new TileEntityRenderWindmill());
    }
}
