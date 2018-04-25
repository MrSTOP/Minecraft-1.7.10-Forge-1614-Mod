package com.github.mrstop.stdemo.potion;

import com.github.mrstop.stdemo.common.ConfigLoader;
import net.minecraft.potion.Potion;


public class PotionFallProtection extends Potion {

    public PotionFallProtection()
    {
        super(ConfigLoader.PotionFallProtection, false, 0xf36dd9);
        this.setPotionName("potion.fallProtection");
        this.setIconIndex(0, 0);
    }
}
