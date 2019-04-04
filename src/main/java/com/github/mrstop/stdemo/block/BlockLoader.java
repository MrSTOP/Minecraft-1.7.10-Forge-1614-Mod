package com.github.mrstop.stdemo.block;

import net.minecraft.block.Block;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockLoader {

    public static Block grassBlock = new BlockGrassBlock();//实例化方块
    public static Block chromiteOre = new BlockChromiteOre();
    public static Block fluidMercuryBlock = new BlockFluidMercury();
    public static Block blockColorFlowerBlack = new BlockColorFlowerBlack();
    public static Block blockColorFlowerWhite = new BlockColorFlowerWhite();
    public static Block blockColorFlowerRed = new BlockColorFlowerRed();
    public static Block blockColorFlowerGreen = new BlockColorFlowerGreen();
    public static Block blockColorFlowerYellow = new BlockColorFlowerYellow();
    public static Block metalFurnaceActive = new BlockMetalFurnace(true);
    public static Block metalFurnaceInactive = new BlockMetalFurnace(false);
    public static Block blockMachineRedstoneFluxFurnaceActive = new BlockMachineRedstoneFluxFurnace(true);
    public static Block blockMachineRedstoneFluxFurnaceInactive = new BlockMachineRedstoneFluxFurnace(false);
    public static Block windmillBlock = new BlockWindmill();
    public static Block windmillGroundBlock = new BlockWindmillGround();
    public static Block quartzFurnaceActive = new BlockQuartzFurnace(true);
    public static Block quartzFurnaceInactive = new BlockQuartzFurnace(false);
    public static Block machineElectrolyticMachine = new BlockMachineElectrolyticMachine();
    public static Block cinnabarOre = new BlockCinnabarOre();
    public static Block machineCalciner = new BlockMachineCalciner();

    //构造方法
    public BlockLoader(FMLPreInitializationEvent event) {
        register(chromiteOre, "chromite_ore");
        register(cinnabarOre, "cinnabar_ore");
        register(grassBlock,"grass_block");//注册草块
        register(fluidMercuryBlock, "fluidMercury_block");
        register(blockColorFlowerBlack, "color_flower_black_block");
        register(blockColorFlowerWhite, "color_flower_white_block");
        register(blockColorFlowerRed, "color_flower_red_block");
        register(blockColorFlowerGreen, "color_flower_green_block");
        register(blockColorFlowerYellow, "color_flower_yellow_block");
        register(metalFurnaceActive, "metal_furnace_active");
        register(metalFurnaceInactive, "metal_furnace_inactive");
        register(blockMachineRedstoneFluxFurnaceActive, "machine_redstoneflux_furnace_active");
        register(blockMachineRedstoneFluxFurnaceInactive, "machine_redstoneflux_furnace_inactive");
        register(windmillBlock, "windmill_block");
        register(windmillGroundBlock, "windmill_ground_block");
        register(quartzFurnaceActive, "quartz_furnace_active");
        register(quartzFurnaceInactive, "quartz_furnace_inactive");
        register(machineElectrolyticMachine, "machine_electrolytic_machine");
        register(machineCalciner, "machine_calciner");
    }

    private static void register(Block block,String name) {
        //注册方块的方法
        GameRegistry.registerBlock(block,name);
    }
}
