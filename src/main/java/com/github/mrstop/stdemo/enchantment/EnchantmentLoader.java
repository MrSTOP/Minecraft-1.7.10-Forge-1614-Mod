package com.github.mrstop.stdemo.enchantment;

import com.github.mrstop.stdemo.common.Log;
import com.github.mrstop.stdemo.common.ConfigLoader;
import net.minecraft.enchantment.Enchantment;

public class EnchantmentLoader {

    public static Enchantment fireburn;

    public EnchantmentLoader()
    {
        try
        {
            fireburn = new EnchantmentFireBurn();
            Enchantment.addToBookList(fireburn);
        }
        catch (Exception e)
        {
            Log.Log.error("Duplicate or illegal enchantment id: {}, the registry of class '{}' will be skipped.", ConfigLoader.EnchantmentFireBurn, EnchantmentFireBurn.class.getName());
        }
    }
}
