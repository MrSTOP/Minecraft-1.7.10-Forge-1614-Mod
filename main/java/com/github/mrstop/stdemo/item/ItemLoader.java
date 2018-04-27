package com.github.mrstop.stdemo.item;


import net.minecraft.item.Item;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemLoader//加载物品
{
    //实例化物品
    public static Item goldenEgg = new ItemGoldenEgg();
    public static Item bestSword = new ItemBestSword();
    public static Item chromiteIngot = new ItemChromiteIngot();
    public static Item redstonePickaxe = new ItemRedstonePickaxe();
    public static Item redstoneApple = new ItemRedstoneApple();
    public static Item diamondApple = new ItemDiamondApple();
    public static Item redstoneHelmet = new ItemArmorRedstoneHelmet();
    public static Item redstoneChestplate = new ItemArmorRedstoneChestplate();
    public static Item redstoneLeggings = new ItemArmorRedstoneLegging();
    public static Item redstoneBoots = new ItemArmorRedstoneBoots();
    public static Item bucketMercury = new ItemBucketMercury();
    public static Item color = new ItemColor();

    public ItemLoader(FMLPreInitializationEvent event)//构造方法
    {
        register(goldenEgg, "golden_egg");                                              //注册金蛋
        register(bestSword, "best_sword");                                              //注册最好的剑
        register(chromiteIngot,"chromite_ingot");                                       //注册铬锭
        register(redstonePickaxe, "redstone_pickaxe");                                  //注册红石稿
        register(redstoneApple, "redstone_apple");                                      //注册红石苹果
        register(diamondApple, "diamond_apple");                                        //注册钻石苹果
        register(redstoneHelmet, "redstone_helmet");                                    //注册红石头盔
        register(redstoneChestplate, "redstone_chestplate");                            //注册红石胸甲
        register(redstoneLeggings, "redstone_legging");                                 //注册红石护腿
        register(redstoneBoots, "redstone_boots");                                      //注册红石靴子
        register(bucketMercury, "bucket_mercury");                                      //注册水银桶
        register(color, "color");
    }

    private static void register(Item item, String name)//注册物品的方法
    {
        GameRegistry.registerItem(item,name);
    }

}