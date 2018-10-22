package com.mod.tuto.structures;

import com.mod.tuto.init.BlockMod;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class StructureTuto1
{
    public static void init(World world, int x, int y, int z)
    {
        if(world.getBlock(x, y-1, z) == Blocks.grass && world.getBlock(x, y+7, z) == Blocks.air)
        {
            world.setBlock(x, y, z, Blocks.stone);
            world.setBlock(x, y+1, z, Blocks.stone);
            world.setBlock(x, y+2, z, Blocks.stone);
            world.setBlock(x, y+3, z, Blocks.stone);
            world.setBlock(x, y+4, z, Blocks.stone);
            world.setBlock(x, y+5, z, Blocks.stone);
            world.setBlock(x, y+6, z, Blocks.stone);
            world.setBlock(x, y+7, z, Blocks.stone);
            world.setBlock(x, y+8, z, BlockMod.block_test);
        }
    }
}