package com.github.mrstop.stdemo.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.github.mrstop.stdemo.core.util.ColorRGBA;

public interface ICustomHighLight {

    //获取渲染高亮所需的AABB盒
    AxisAlignedBB[] getBlockBoxs(World world, int x, int y, int z, EntityPlayer player);

    //获取渲染高亮所需的扩张值
    default double getExpantion(){
        return 0.002;
    }

    //获取渲染高亮所需的线条宽度
    default float getLineWidth(){
        return 2.0F;
    }

    //获取渲染高亮所需的线条颜色
    default ColorRGBA getLineColor(){
        return new ColorRGBA();
    };
}
