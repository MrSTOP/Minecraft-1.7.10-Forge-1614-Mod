package com.github.mrstop.stdemo.common;


import com.github.mrstop.stdemo.enchantment.EnchantmentLoader;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventBus;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.processing.SupportedSourceVersion;


public class EventLoader {

    EntityPlayer entityPlayer;

    public static final EventBus EVENT_BUS = new EventBus();


    public EventLoader()
    {
        MinecraftForge.EVENT_BUS.register(this);
        EventLoader.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event)
    {
        //if (!event.player.isServerWorld())
        {
            String info = String.format("%s picks up: %s", event.player.getGameProfile().getName(), event.pickedUp.getEntityItem().getUnlocalizedName());
            Log.Log.info(info);
            Log.Log.info("ONPLAYERITEMPICKUP");
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (!event.world.isRemote)
        {
            String info = String.format("%s interacts with: %d, %d, %d, ", event.entityPlayer.getGameProfile().getName(), event.x, event.y, event.z);
            Log.Log.info(info);
        }
    }

    @SubscribeEvent
    public void test1(LivingFallEvent event)
    {
        if(event.entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer)event.entityLiving;
            entityPlayer.addChatMessage(new ChatComponentText("Falling Star! You fell "+event.distance+ " meters.That's cool, man!"));
        }
    }

    @SubscribeEvent
    public void onPlayerClickGrassBlock(PlayerRightClickGrassBlockEvent event)
    {
        if (!event.world.isRemote)
        {
            int blockX = event.blockX;
            int blockY = event.blockY;
            int blockZ = event.blockZ;
            Entity tnt = new EntityTNTPrimed(event.world, blockX + 0.5, blockY + 0.5, blockZ + 0.5, null);
            event.world.spawnEntityInWorld(tnt);
            event.entityPlayer.attackEntityFrom(DamageSource.lava, 8.0F);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        if (player.isServerWorld() && event.target instanceof EntityPig)
        {
            EntityPig pig = (EntityPig)  event.target;
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && (stack.getItem() == Items.wheat || stack.getItem() == Items.wheat_seeds))
            {
                player.attackEntityFrom((new DamageSource("byPig")).setDifficultyScaled().setExplosion(), 8.0F);
                player.worldObj.createExplosion(pig, pig.posX, pig.posY, pig.posZ, 2.0F, false);
                pig.setDead();
            }
        }
    }

    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (!event.world.isRemote && event.harvester != null)
        {
            ItemStack itemstack = event.harvester.getHeldItem();
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentLoader.fireburn.effectId, itemstack) > 0 && itemstack.getItem() != Items.shears)
            {
                for (int i = 0; i < event.drops.size(); ++i)
                {
                    ItemStack stack = event.drops.get(i);
                    ItemStack newStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (newStack != null)
                    {
                        newStack = newStack.copy();
                        newStack.stackSize = stack.stackSize;
                        event.drops.set(i, newStack);
                    }
                    else if (stack != null)
                    {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        boolean b = (block == null);
                        if (!b &&
                                        (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.DOWN))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.EAST))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.NORTH))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.SOUTH))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.UP))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.WEST)))
                        {
                            event.drops.remove(i);
                        }
                    }
                }

            }
        }
    }

    @Cancelable
    public static class PlayerRightClickGrassBlockEvent extends net.minecraftforge.event.entity.player.PlayerEvent
    {
        public final int blockX;
        public final int blockY;
        public final int blockZ;
        public final World world;
        public PlayerRightClickGrassBlockEvent(EntityPlayer player, int x, int y,int z, World world)
        {
            super(player);
            this.blockX = x;
            this.blockY = y;
            this.blockZ = z;
            this.world = world;
        }
    }


    /*@SubscribeEvent
    public void onFillBucket(FillBucketEvent event)
    {
        BlockPos event.target.
    }*/
}
