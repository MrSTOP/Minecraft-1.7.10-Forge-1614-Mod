package com.github.mrstop.stdemo.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.github.mrstop.stdemo.core.util.ColorRGBA;

public interface ICustomHighLight {

    //��ȡ��Ⱦ���������AABB��
    AxisAlignedBB[] getBlockBoxs(World world, int x, int y, int z, EntityPlayer player);

    //��ȡ��Ⱦ�������������ֵ
    default double getExpantion(){
        return 0.002;
    }

    //��ȡ��Ⱦ����������������
    default float getLineWidth(){
        return 2.0F;
    }

    //��ȡ��Ⱦ���������������ɫ
    default ColorRGBA getLineColor(){
        return new ColorRGBA();
    };
}
