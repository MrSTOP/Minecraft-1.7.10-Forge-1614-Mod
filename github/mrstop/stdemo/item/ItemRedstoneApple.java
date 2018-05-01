package com.github.mrstop.stdemo.item;


import com.github.mrstop.stdemo.creativetab.CreativeTabsLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;


public class ItemRedstoneApple extends ItemFood {

    public ItemRedstoneApple()
    {
        super(4, 0.6F, false);
        this.setAlwaysEdible();//该食物何时何地都可以被食用
        this.setUnlocalizedName("redstoneApple");
        this.setCreativeTab(CreativeTabsLoader.tabSTDemo);
        this.setPotionEffect(Potion.absorption.id, 10, 2, 1.0F);
        /*
        第一个参数表示对应药水效果的potionId，可以去net.minecraft.potion.Potion类中查看MC提供的二十四种药水效果，这里为伤害吸收。
        *第二个参数表示对应药水效果的持续时间，以秒计数，这里为十秒。
        *第三个参数表示对应药水效果的等级，很明显，0为一级，1为二级，2为三级，以此类推，这里为二级。
        *最后一个参数表示产生该药水效果的概率，这里为100%。
        * */
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("stdemo:redstone_apple");
    }
}
