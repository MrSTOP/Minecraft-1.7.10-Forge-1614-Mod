package com.github.mrstop.stdemo.potion;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.potion.Potion;

public class PotionLoader {
    public static Potion potionFallProtection;

    public PotionLoader(FMLPreInitializationEvent event) {
        potionFallProtection = new PotionFallProtection();
    }
}
