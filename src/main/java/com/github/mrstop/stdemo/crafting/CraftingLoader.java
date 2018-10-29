package com.github.mrstop.stdemo.crafting;


import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.common.ConfigLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;


public class CraftingLoader
{
    public CraftingLoader() {
        registerRecipe();
        registerSmelting();
        registerFuel();
    }

    private static void registerRecipe() {
        //注册合成表
        //有序合成：金蛋
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.goldenEgg, 10), new Object[]
                 {
                       "###", "#*#", "###",'#', Items.gold_ingot, '*', Items.egg
                 });//有序合成

        //
        /*GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.explosionEgg, 5), new Object[]
                {
                        "###", "#*#", "###",'#', Items.gunpowder, '*', Items.egg
                });*/

        //有序合成：草块
        GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.grassBlock), new Object[]
                {
                        "# #", "# #", " # ", '#', Blocks.vine
                });//有序合成

        //有序合成：红石稿
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.redstonePickaxe), new Object[]
                {
                        "###", " * ", " * ", '#', Items.redstone, '*', Items.stick
                });

        //有序合成：红石苹果
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.redstoneApple), new Object[]
                {
                        "###", "#*#", "###", '#', Items.redstone, '*', Items.apple
                });

        //有序合成：钻石苹果
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.diamondApple), new Object[]
                {
                        "###", "#*#", "###", '#', Items.diamond, '*', Items.apple
                });

        //有序合成：红石头盔
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.redstoneHelmet), new Object[]
                {
                        "###", "# #", "   ", '#', Items.redstone
                });

        //有序合成：红石胸甲
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.redstoneChestplate), new Object[]
                {
                        "# #", "###", "###", '#', Items.redstone
                });

        //有序合成：红石护腿
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.redstoneLeggings), new Object[]
                {
                        "###", "# #", "# #", '#', Items.redstone
                });

        //有序合成：红石靴子
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.redstoneBoots), new Object[]
                {
                        "# #", "# #", "   ", '#', Items.redstone
                });

        //矿物词典：有序合成：最好的剑
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.bestSword), new Object[]
                {
                        "#*#", "#*#", " % ", '*', "ingotCopper", '#', "dustRedstone", '%', Items.stick
                }));

        //无序合成：草块
        //GameRegistry.addShapelessRecipe(new ItemStack(BlockLoader.grassBlock), Blocks.vine);

        //无序合成：藤蔓
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.vine, 4), BlockLoader.grassBlock);

    }

    private static void registerSmelting()//注册烧练
    {
        //烧练：草块——》煤炭
        GameRegistry.addSmelting(BlockLoader.grassBlock, new ItemStack(Items.coal), 0.5F);
        //烧练：铬矿石——》铬锭
        GameRegistry.addSmelting(BlockLoader.chromiteOre, new ItemStack(ItemLoader.chromiteIngot), 1F);
    }

    private static void registerFuel()//注册燃料
    {
        //燃料：钻石
        GameRegistry.registerFuelHandler(new IFuelHandler() {
            @Override
            public int getBurnTime(ItemStack fuel) {
                return Items.diamond != fuel.getItem() ? 0 : Math.max(0, ConfigLoader.DiamondBurnTime);
            }
        });
        /*
        *这里的烧炼时间为gametick，一秒为20个gametick，下面列出一些常见的烧炼时间数据：
        *树苗　　100
        *木板　　200
        *煤炭　　1600
        *烈焰棒　2400
        *煤炭块　16000
        *岩浆桶　20000
         */
    }
}
