package com.github.mrstop.stdemo.item;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.common.Log;
import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class ItemSpawnEggGoldenChicken extends ItemMonsterPlacer{

        @SideOnly(Side.CLIENT)
        private IIcon theIcon;

        protected int colorBase = 0xFF0000;
        protected int colorSpots = 0x00FF00;
        protected String entityToSpawnName = "";
        protected String entityToSpawnNameFull = "";
        protected EntityLiving entityToSpawn = null;

        public ItemSpawnEggGoldenChicken()
        {
            super();
        }

        public ItemSpawnEggGoldenChicken(String entityToSpawnName, int primaryColor, int SecondaryColor)
        {
            this.setHasSubtypes(false);
            this.setMaxStackSize(64);
            this.setUnlocalizedName(entityToSpawnName);
            this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
            this.setEntityToSpawnName(entityToSpawnName);
            //this.iconString = entityToSpawnName;
            this.setColors(primaryColor, SecondaryColor);
            //System.out.println("Spawn egg constructor for" + entityToSpawnName);
        }

        @Override
        public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int blockX, int blockY, int blockZ, int array, float faceY, float faceZ, float par7)
        {
            if (world.isRemote)
            {
                return true;
            }
            else
            {
                Block block = world.getBlock(blockX, blockY, blockZ);
                blockX += Facing.offsetsXForSide[array];
                blockY += Facing.offsetsYForSide[array];
                blockZ += Facing.offsetsZForSide[array];
                double d0 = 0.0D;
                if (array == 1 &&block.getRenderType() == 11)
                {
                    d0 = 0.5D;
                }
                Entity entity = spawnEntity(world, blockX + 0.5D, blockY + d0, blockZ + 0.5D);
                if (entity != null)
                {
                    if (entity instanceof EntityLivingBase && itemStack.hasDisplayName())
                    {
                        ((EntityLiving) entity).setCustomNameTag(itemStack.getDisplayName());
                    }
                    if (!entityPlayer.capabilities.isCreativeMode)
                    {
                        --itemStack.stackSize;
                    }
                }
                return true;
            }
        }

        @Override
        public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
        {
            if (world.isRemote)
            {
                return itemStack;
            }
            else
            {
                MovingObjectPosition movingObjectPosition = getMovingObjectPositionFromPlayer(world, entityPlayer, true);
                if (movingObjectPosition == null)
                {
                    return itemStack;
                }
                else
                {
                    if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                    {
                        int x = movingObjectPosition.blockX;
                        int y = movingObjectPosition.blockY;
                        int z = movingObjectPosition.blockZ;
                        if (!entityPlayer.canPlayerEdit(x, y, z, movingObjectPosition.sideHit, itemStack))
                        {
                            return itemStack;
                        }
                        if (world.getBlock(x, y, z) instanceof BlockLiquid)
                        {
                            Entity entity = spawnEntity(world, x, y, z);
                            if (entity != null)
                            {
                                if (entity instanceof EntityLivingBase && itemStack.hasDisplayName())
                                {
                                    ((EntityLiving) entity).setCustomNameTag(itemStack.getDisplayName());
                                }
                                if (!entityPlayer.capabilities.isCreativeMode)
                                {
                                    --itemStack.stackSize;
                                }
                            }
                        }
                    }
                    return itemStack;
                }
            }
        }

        public  Entity spawnEntity(World world, double parX, double parY, double parZ)
        {
            if (!world.isRemote)
            {
                entityToSpawnNameFull = STDemo.MODID + "." + entityToSpawnName;
                if (EntityList.stringToClassMapping.containsKey(entityToSpawnNameFull))
                {
                    entityToSpawn = (EntityLiving) EntityList.createEntityByName(entityToSpawnNameFull, world);
                    entityToSpawn.setLocationAndAngles(parX, parY, parZ, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
                    world.spawnEntityInWorld(entityToSpawn);
                    entityToSpawn.onSpawnWithEgg((IEntityLivingData) null);
                    entityToSpawn.playLivingSound();
                }
                else
                {
                    System.out.println("Entity NOT Found" + entityToSpawnName);
                }
            }
            return entityToSpawn;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
        {
            list.add(new ItemStack(item, 1, 0));
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getColorFromItemStack(ItemStack itemStack, int colorType)
        {
            return (colorType == 0) ? colorBase : colorSpots;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public boolean requiresMultipleRenderPasses()
        {
            return true;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegister iIconRegister)
        {
            //Log.Log.info("EGGICON=>iconString>" + this.iconString);
            //super.registerIcons(iIconRegister);
            itemIcon = iIconRegister.registerIcon(STDemo.MODID + ":" + entityToSpawnName);
            theIcon = iIconRegister.registerIcon(STDemo.MODID + ":" + entityToSpawnName + "_overlay");
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IIcon getIconFromDamageForRenderPass(int damageVal, int renderPass)
        {
            return renderPass > 0 ? theIcon : super.getIconFromDamageForRenderPass(damageVal, renderPass);
        }

        public void setColors(int colorBase, int colorSpots)
        {
            this.colorBase = colorBase;
            this.colorSpots = colorSpots;
        }

        public int getColorBase(int colorBase) {
            return this.colorBase;
        }

        public int getColorSpots(int colorSpots) {
            return this.colorSpots;
        }

        public void setEntityToSpawn(String entityToSpawnName)
        {
            this.entityToSpawnName = entityToSpawnName;
            this.entityToSpawnNameFull = STDemo.MODID + "." + entityToSpawnName;
        }

        public void setEntityToSpawnName(String entityToSpawnName)
        {
            this.entityToSpawnName = entityToSpawnName;
            this.entityToSpawnNameFull = STDemo.MODID + "." + entityToSpawnName;
        }
}
