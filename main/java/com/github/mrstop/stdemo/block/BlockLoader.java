package com.github.mrstop.stdemo.block;

import net.minecraft.block.Block;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockLoader {


    public static Block grassBlock = new BlockGrassBlock();//实例化方块
    public static Block chromiteBlock = new BlockChromiteBlock();
    public static Block fluidMercuryBlock = new BlockFluidMercury();
    public static Block colorBlock = new BlockColorCrops();


    public  BlockLoader(FMLPreInitializationEvent event)//构造方法
    {
        register(grassBlock,"grass_block");//注册草块
        register(chromiteBlock, "chromite_block");
        register(fluidMercuryBlock, "fluidMercury_block");
        register(colorBlock, "color_block");
    }

    private static void register(Block block,String name)//注册方块的方法
    {
        GameRegistry.registerBlock(block,name);
    }
}
