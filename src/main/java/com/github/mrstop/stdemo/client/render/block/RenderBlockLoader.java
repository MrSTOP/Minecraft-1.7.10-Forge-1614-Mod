package com.github.mrstop.stdemo.client.render.block;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderBlockLoader {
    public RenderBlockLoader() {
        RenderingRegistry.registerBlockHandler(RenderWindmillGround.renderWindmillGround);
    }
}
