package com.github.mrstop.stdemo.common;


import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


public class ConfigLoader
{
    private static Configuration config;


    //配置文件变量开始
    public static int DiamondBurnTime;
    public static int ChromiteOreSpawnRate;
    public static int EnchantmentFireBurn;
    public static int PotionFallProtection;
    //配置文件变量结束


    public ConfigLoader(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        load();
    }

    public static void load()
    {
        Log.Log.info("Start Load STDemo's Config.");

        String comment;
        comment = "en_US:How many seconds can a diamond burn in a furnace.\nzh_CN:一颗钻石可以烧多少秒 ";
        DiamondBurnTime = config.get(Configuration.CATEGORY_GENERAL, "DiamondBurnTime", 640, comment).getInt();

        comment = "en_US:Chromite ore spawn rate.\nzh_CN:铬矿石生成率 ";
        ChromiteOreSpawnRate = config.get(Configuration.CATEGORY_GENERAL, "ChromiteOreSpawnRate", 16, comment).getInt();

        comment = "en_US:Fire burn enchantment id.\nzh_CN:火焰灼烧附魔编号";
        EnchantmentFireBurn = config.get(Configuration.CATEGORY_GENERAL, "EnchantmentFireBurn", 36, comment).getInt();

        comment = "en_US:\nzh_CN:跌落保护编号";
        PotionFallProtection = config.get(Configuration.CATEGORY_GENERAL, "potionFallProtection", 50, comment).getInt();

        config.save();
        Log.Log.info("Finished loading STDemo's Config.");
    }

}
