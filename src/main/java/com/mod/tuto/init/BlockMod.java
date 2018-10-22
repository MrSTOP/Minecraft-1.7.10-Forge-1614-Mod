package com.mod.tuto.init;

import com.mod.tuto.ModTuto;
import com.mod.tuto.Reference;
import com.mod.tuto.blocks.BlockBasic;
import com.mod.tuto.blocks.BlockCropsMod;
import com.mod.tuto.blocks.BlockFenceMod;
import com.mod.tuto.blocks.BlockIsbrhMod;
import com.mod.tuto.blocks.BlockStairsMod;
import com.mod.tuto.blocks.BlockTesr;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockMod
{
    public static Block block_test, block_stairs, block_fence, blockIsbrh, blockTesr, cultureTest;
    
    public static void init()
    {
        block_test = new BlockBasic(Material.rock, 2).setUnlocalizedName("block_test").setCreativeTab(ModTuto.tabTuto).setTextureName(Reference.MOD_ID + ":block_test");
        block_stairs = new BlockStairsMod(BlockMod.block_test, 0).setUnlocalizedName("block_stairs").setCreativeTab(CreativeTabs.tabBlock).setLightLevel(1.0F);
        block_fence = new BlockFenceMod(Reference.MOD_ID + ":block_test", Material.rock).setUnlocalizedName("block_fence").setLightLevel(1.0F);
        blockIsbrh = new BlockIsbrhMod(Material.rock).setUnlocalizedName("blockIsbrh").setTextureName("glowstone").setCreativeTab(ModTuto.tabTuto);
        blockTesr = new BlockTesr(Material.rock).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("blockTesr").setTextureName("glowstone");
        cultureTest = new BlockCropsMod().setUnlocalizedName("cultureTest").setTextureName(Reference.MOD_ID + ":cultureTest");
    }
    
    public static void register()
    {
        GameRegistry.registerBlock(block_test, block_test.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(block_stairs, block_stairs.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(block_fence, block_fence.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockIsbrh, blockIsbrh.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blockTesr, blockTesr.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(cultureTest, cultureTest.getUnlocalizedName().substring(5));
        
        
        GameRegistry.addRecipe(new ItemStack(BlockMod.block_test, 5), new Object[]{"###", "#F#", '#', Items.apple, 'F', Items.diamond});
        GameRegistry.addSmelting(Blocks.bedrock, new ItemStack(Items.diamond, 10), 1.0F);
    }
}
