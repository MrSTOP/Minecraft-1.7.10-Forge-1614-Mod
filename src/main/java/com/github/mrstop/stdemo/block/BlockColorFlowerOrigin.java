package com.github.mrstop.stdemo.block;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockColorFlowerOrigin extends BlockColorFlower {
    public BlockColorFlowerOrigin() {
        super("colorFlowerOriginBlock");
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
//        player.addChatMessage(new ChatComponentText(player.getHeldItem().getItem().getClass().toString()));
//        for (int i = 1000; i <= 1022; i++) {
//            worldIn.playAuxSFX(2005, x, y, z, 0);
//        }
//        for (int i = 2000; i <= 2006; i++) {
//            worldIn.playAuxSFX(2005, x, y, z, 0);
//        }
        float rate = 0.1F;
        boolean cancelBlockPlace = false;
        if (worldIn.isRemote) {
            cancelBlockPlace = true;
        } else {
            if (worldIn.getBlockMetadata(x, y, z) != MAX_GROWTH_STAGE) {
                cancelBlockPlace = false;
            } else {
                ItemStack itemStack = player.getHeldItem();
                for (int i: OreDictionary.getOreIDs(itemStack)) {
                    if (i == OreDictionary.getOreID("dye") && itemStack != null) {
                        itemStack.stackSize--;
                    }
                    if (worldIn.rand.nextFloat() < rate) {
                        cancelBlockPlace = true;
                        if (i == OreDictionary.getOreID("dyeBlack")) {
                            worldIn.setBlock(x, y, z, BlockLoader.blockColorFlowerBlack);
                        } else if (i == OreDictionary.getOreID("dyeWhite")) {
                            worldIn.setBlock(x, y, z, BlockLoader.blockColorFlowerWhite);
                        } else if (i == OreDictionary.getOreID("dyeRed")) {
                            worldIn.setBlock(x, y, z, BlockLoader.blockColorFlowerRed);
                        } else if (i == OreDictionary.getOreID("dyeGreen")) {
                            worldIn.setBlock(x, y, z, BlockLoader.blockColorFlowerGreen);
                        } else if (i == OreDictionary.getOreID("dyeBlue")) {
                            worldIn.setBlock(x, y, z, BlockLoader.blockColorFlowerBlue);
                        } else if (i == OreDictionary.getOreID("dyeYellow")) {
                            worldIn.setBlock(x, y, z, BlockLoader.blockColorFlowerYellow);
                        } else if (i == OreDictionary.getOreID("dyeBrown")) {
                            worldIn.setBlock(x, y, z, BlockLoader.blockColorFlowerBrown);
                        } else {
                            cancelBlockPlace = false;
                        }
                        if (cancelBlockPlace) {
                            worldIn.playAuxSFX(2006, x, y, z, 0);
                        }
                    } else {
                        cancelBlockPlace = false;
                    }
                }
            }
        }
        return cancelBlockPlace;
    }

    @Override
    protected Item getSeed() {
        return ItemLoader.seedsColorOrigin;
    }

    @Override
    protected void registerLastIcons(IIconRegister iIconRegister, int index) {
        this.icons[index] = iIconRegister.registerIcon(STDemo.MOD_DOMAIN + "color_flower_origin");
    }
}
