package com.github.mrstop.stdemo.worldgen;

import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.common.ConfigLoader;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class WorldGeneratorLoader
{

    private static WorldGenerator worldGenMinable = new WorldGenMinable(BlockLoader.chromiteOre, ConfigLoader.ChromiteOreSpawnRate);

    public WorldGeneratorLoader()
    {
        MinecraftForge.ORE_GEN_BUS.register(this);
    }

    /*
    private int x;
    private int y;
    private int z;

    @SubscribeEvent
    public void onOreGenPost(OreGenEvent.Post event)
    {
        /*if (!isPosXYZequal(event.worldX, event.worldZ))
        {
            this.x = event.worldX;
            this.z = event.worldZ;
            GenTest.generate(event.world, event.rand, event.worldX, event.world)
        }
    }*/

    @SubscribeEvent
    public void onOreGenGenerateMinable(OreGenEvent.GenerateMinable event)
    {
        /*if(event.type == OreGenEvent.GenerateMinable.EventType.COAL)
        {
            event.setResult(Event.Result.DENY);
        }*/

        if (event.type != OreGenEvent.GenerateMinable.EventType.IRON)
            return;

        if (!TerrainGen.generateOre(event.world, event.rand, worldGenMinable, event.worldX, event.worldZ, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            return;

        for (int i = 0; i < 40; i++)
        {
            int posX = event.worldX + event.rand.nextInt(16);
            int posY = 16 + event.rand.nextInt(16);
            int posZ = event.worldZ + event.rand.nextInt(16);
            worldGenMinable.generate(event.world, event.rand, posX, posY, posZ);
//            Log.Log.info("GEN ORE");
        }

    }

    /*private boolean isPosXYZequal(int x, int z)
    {
        if (x == this.x  && z == this.z)
        {
            return true;
        }
        else
        {
            return false;
        }
    }*/
}
