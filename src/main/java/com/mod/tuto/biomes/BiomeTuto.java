package com.mod.tuto.biomes;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeTuto extends BiomeGenBase
{
    public BiomeTuto(int p_i1971_1_)
    {
        super(p_i1971_1_);
        this.topBlock = Blocks.dirt;
        this.fillerBlock = Blocks.lava;
        this.enableRain = false;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCreeper.class, 50, 10, 50));
        this.waterColorMultiplier = 16718080;
        this.addDefaultFlowers();
    }
    
    public void decorate(World p_76728_1_, Random p_76728_2_, int p_76728_3_, int p_76728_4_)
    {
        
    }
}