package com.github.mrstop.stdemo.achievement;

import com.github.mrstop.stdemo.STDemo;
import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import net.minecraft.init.Blocks;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;

public class AchievementLoader {
    public final static Achievement worseThanPig = new Achievement("achievement.stdemo.worseThanPig", "stdemo.worseThanPig", 5, -4, ItemLoader.goldenEgg, AchievementList.buildSword);
    public final static Achievement buildGrassBlock = new Achievement("achievement.stdemo.buildGrassBlock", "stdemo.buildGrassBlock", 0, 0, Blocks.vine, null);
    public final static Achievement explosionFromGrassBlock = new Achievement("achievement.stdemo.explosionFromGrassBlock", "stdemo.explosionFromGrassBlock", 2, -1, BlockLoader.grassBlock, buildGrassBlock);

    public final static AchievementPage pageSTDemo = new AchievementPage(STDemo.NAME, buildGrassBlock, explosionFromGrassBlock);

    public AchievementLoader() {
        worseThanPig.setSpecial().registerStat();
        buildGrassBlock.initIndependentStat().registerStat();
        explosionFromGrassBlock.setSpecial().registerStat();

        AchievementPage.registerAchievementPage(pageSTDemo);
    }
}
