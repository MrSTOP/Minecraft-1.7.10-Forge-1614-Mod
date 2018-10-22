package com.github.mrstop.stdemo.client.block.render;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderBlockLoader {
    public RenderBlockLoader() {
        RenderingRegistry.registerBlockHandler(RenderWindmillGround.renderWindmillGround);
    }
}
