package com.mod.tuto.events;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class DropsBlock
{
    @SubscribeEvent
    public void BlockDestroyed(HarvestDropsEvent events)
    {
        Random random = new Random();
        
        if(events.block == Blocks.stone)
        {
            events.drops.clear();
            events.drops.add(new ItemStack(Items.apple, 4 + random.nextInt(6)));
        }
    }
}