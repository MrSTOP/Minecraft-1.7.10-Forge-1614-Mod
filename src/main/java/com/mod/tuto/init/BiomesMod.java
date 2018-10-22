package com.mod.tuto.init;

import com.mod.tuto.biomes.BiomeTuto;

import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class BiomesMod
{
    public static void init()
    {
        BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(new BiomeTuto(100), 100));
    }
}