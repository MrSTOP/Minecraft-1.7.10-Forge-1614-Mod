package com.thegrovesyproject.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.thegrovesyproject.creativetabs.TMCreativeTabs;
import com.thegrovesyproject.lib.Strings;

import cpw.mods.fml.common.registry.GameRegistry;

public class TMBlock {

	public static void mainRegistry() {
		initialiseBlock();
		registerBlock();
	}

	public static Block tutBlock;
	public static Block tutCrop;
	public static Block tutChest;
	public static Block multiSided;
	public static Block tutFurnace;
	public static Block tutFurnaceActive;

	public static void initialiseBlock() {
		tutBlock =  new TutBlock(Material.ground).setUnlocalizedName("TutBlock").setCreativeTab(TMCreativeTabs.tabBlock).setTextureName(Strings.MODID + ":TutBlock");
		multiSided = new MultiSided(Material.ground).setUnlocalizedName("MultiSided").setCreativeTab(TMCreativeTabs.tabBlock);
		tutCrop = new TutCrop().setUnlocalizedName("TutCrop").setCreativeTab(TMCreativeTabs.tabBlock).setTextureName(Strings.MODID + ":TutCrop");
		tutChest = new TutChest(0).setUnlocalizedName("TutChest").setCreativeTab(TMCreativeTabs.tabBlock).setTextureName(Strings.MODID + ":TutChest");
		
		tutFurnace= new TutFurnace(false).setUnlocalizedName("TutFurnace").setCreativeTab(TMCreativeTabs.tabBlock);
		tutFurnaceActive= new TutFurnace(true).setUnlocalizedName("TutFurnaceActive");
	}

	public static void registerBlock() {
		GameRegistry.registerBlock(tutBlock, tutBlock.getUnlocalizedName());
		GameRegistry.registerBlock(multiSided, multiSided.getUnlocalizedName());
		GameRegistry.registerBlock(tutCrop, tutCrop.getUnlocalizedName());
		GameRegistry.registerBlock(tutChest, tutChest.getUnlocalizedName());
		GameRegistry.registerBlock(tutFurnace, tutFurnace.getUnlocalizedName());
		GameRegistry.registerBlock(tutFurnaceActive, tutFurnaceActive.getUnlocalizedName());
	}
}
