package com.github.mrstop.stdemo.common;


import com.github.mrstop.stdemo.achievement.AchievementLoader;
import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.client.KeyLoader;
import com.github.mrstop.stdemo.enchantment.EnchantmentLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import com.github.mrstop.stdemo.potion.PotionLoader;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;


public class EventLoader {

    EntityPlayer entityPlayer;

    public static final EventBus EVENT_BUS = new EventBus();


    public EventLoader()
    {
        MinecraftForge.EVENT_BUS.register(this);
        EventLoader.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event)
    {
        //if (!event.player.isServerWorld())
        {
            String info = String.format("%s picks up: %s", event.player.getGameProfile().getName(), event.pickedUp.getEntityItem().getUnlocalizedName());
            Log.Log.info(info);
            Log.Log.info("ONPLAYERITEMPICKUP");
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (!event.world.isRemote)
        {
            String info = String.format("%s interacts with: %d, %d, %d, ", event.entityPlayer.getGameProfile().getName(), event.x, event.y, event.z);
            Log.Log.info(info);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void test2(LivingFallEvent event)
    {
        if (event.entityLiving instanceof EntityPlayerMP)
        {
            EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
            entityPlayer.addChatMessage(new ChatComponentText("Falling Star! You fell "+event.distance+ " meters. That's cool, man! I will protect you from the falling damage!"));
            event.distance = 0.0F;
        }
    }


    @SubscribeEvent
    public void test1(LivingFallEvent event)
    {
        if(event.entityLiving instanceof EntityPlayerMP)
        {
            EntityPlayer entityPlayer = (EntityPlayer)event.entityLiving;
            entityPlayer.addChatMessage(new ChatComponentText("Falling Star! You fell "+event.distance+ " meters.That's cool, man!"));
        }
    }

    @SubscribeEvent
    public void onPlayerClickGrassBlock(PlayerRightClickGrassBlockEvent event)
    {
        //生成TNT
        if (!event.world.isRemote)
        {
            int blockX = event.blockX;
            int blockY = event.blockY;
            int blockZ = event.blockZ;
            Entity tnt = new EntityTNTPrimed(event.world, blockX + 0.5, blockY + 0.5, blockZ + 0.5, null);
            event.world.spawnEntityInWorld(tnt);
            event.entityPlayer.attackEntityFrom(DamageSource.lava, 8.0F);
            event.entityPlayer.triggerAchievement(AchievementLoader.explosionFromGrassBlock);
        }

        //生成金鸡
        if (!event.world.isRemote)
        {
            ItemStack heldItem = event.entityPlayer.getHeldItem();
            if (heldItem == null)
            {
                return;
            }
            if (ItemLoader.goldenEgg.equals(heldItem.getItem()))
            {
                EntityLiving entityLiving = new EntityChicken(event.world);
                int blockX = event.blockX;
                int blcokY = event.blockY;
                int blockZ = event.blockZ;
                entityLiving.setPositionAndUpdate(blockX + 0.5D, blcokY + 1.0D, blockZ + 0.5D);
                if (heldItem != null)
                {
                    --heldItem.stackSize;
                    event.world.spawnEntityInWorld(entityLiving);
                }
                return;
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        if (player.isServerWorld() && event.target instanceof EntityPig)
        {
            EntityPig pig = (EntityPig)  event.target;
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && (stack.getItem() == Items.wheat || stack.getItem() == Items.wheat_seeds))
            {
                player.attackEntityFrom((new DamageSource("byPig")).setDifficultyScaled().setExplosion(), 8.0F);
                player.worldObj.createExplosion(pig, pig.posX, pig.posY, pig.posZ, 2.0F, false);
                pig.setDead();
            }
        }
    }

    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (!event.world.isRemote && event.harvester != null)
        {
            ItemStack itemstack = event.harvester.getHeldItem();
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentLoader.fireburn.effectId, itemstack) > 0 && itemstack.getItem() != Items.shears)
            {
                for (int i = 0; i < event.drops.size(); ++i)
                {
                    ItemStack stack = event.drops.get(i);
                    ItemStack newStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (newStack != null)
                    {
                        newStack = newStack.copy();
                        newStack.stackSize = stack.stackSize;
                        event.drops.set(i, newStack);
                    }
                    else if (stack != null)
                    {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        boolean b = (block == null);
                        if (!b &&
                                        (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.DOWN))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.EAST))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.NORTH))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.SOUTH))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.UP))
                                        || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.WEST)))
                        {
                            event.drops.remove(i);
                        }
                    }
                }

            }
        }
    }


    /*@SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
        if (event.source.getDamageType().equals("fall"))
        {
            PotionEffect effect = event.entityLiving.getActivePotionEffect(PotionLoader.potionFallProtection);
            if (effect != null)
            {
                if (effect.getAmplifier() == 0)
                {
                    event.ammount /= 2;
                }
                else
                {
                    event.ammount = 0;
                }
            }
        }
    }*/

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        if (event.entityLiving instanceof EntityPlayer && event.source.getDamageType().equals("byPig"))
        {
            ((EntityPlayer) event.entityLiving).triggerAchievement(AchievementLoader.worseThanPig);
        }
    }

    @SubscribeEvent
    public void onPlayerItemCrafting(PlayerEvent.ItemCraftedEvent event)
    {
        event.player.worldObj.playSoundAtEntity(event.player, "stdemo:stdemo.test", 1.0F, 1.0F);
        //if (event.crafting.getItem() == Item.getItemFromBlock(BlockLoader.grassBlock))
        {
            event.player.triggerAchievement(AchievementLoader.buildGrassBlock);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        System.out.println("KeyInputEvent");
        if (KeyLoader.showTime.isPressed())
        {
            System.out.println("KeyInputEventKeyK");
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            World world = Minecraft.getMinecraft().theWorld;
            player.addChatComponentMessage(new ChatComponentTranslation("chat.stdemo.time", world.getTotalWorldTime()));
        }
    }

<<<<<<< HEAD
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void drawBLockHighLight(DrawBlockHighlightEvent event){
        if (event.target.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK)){
            if (event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ).isAssociatedBlock(BlockLoader.windmillBlock)){
                drawSpecialBlockHighLight(event, SpecialBlockHighLight.SPECIAL_BLOCK_WINDMILL);
            }
            if (event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ).isAssociatedBlock(BlockLoader.windmillGroundBlock)){
                drawSpecialBlockHighLight(event, SpecialBlockHighLight.SPECIAL_BLOCK_WINDMILL_GROUND);
            }
        }
    }

    private void drawSpecialBlockHighLight(DrawBlockHighlightEvent event, SpecialBlockHighLight blockEnum){
        event.setCanceled(true);

        double x = event.player.lastTickPosX + (event.player.posX - event.player.lastTickPosX) * event.partialTicks;
        double y = event.player.lastTickPosY + (event.player.posY - event.player.lastTickPosY) * event.partialTicks;
        double z = event.player.lastTickPosZ + (event.player.posZ - event.player.lastTickPosZ) * event.partialTicks;

        float expand = 0.002f;
        //?????????????????????????????????????????????????????????????????????????????????????????????
        AxisAlignedBB bounds = event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ).getSelectedBoundingBoxFromPool(event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ).expand(expand, expand, expand).getOffsetBoundingBox(-x, -y, -z);
        //?????????????????????????????????????????????????????????????????????????????????????????????

        int metadata = event.player.worldObj.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ);


        Tessellator tessellator = Tessellator.instance;

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(false);

        switch (blockEnum){
            case SPECIAL_BLOCK_WINDMILL:
                drawWindmillBlockHighLight(tessellator, bounds, metadata);
                break;
            case SPECIAL_BLOCK_WINDMILL_GROUND:
                drawWindmillGroundBlockHighLight(tessellator, bounds, metadata);
                break;
        }

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    //TODO 完善选中高亮
    private void drawWindmillBlockHighLight(Tessellator tessellator, AxisAlignedBB bounds, int metadata){
        if (metadata < 4){
            //竖着的四条边
            //GL_LINES 每两对顶点被解释为一条直线，如果有多对顶点，每对顶点之间互不连接
            //GL_LINE_STRIP 一系列的连续直线
            tessellator.startDrawing(GL11.GL_LINES);
            tessellator.addVertex(bounds.minX, bounds.minY - 0 - metadata, bounds.minZ);
            tessellator.addVertex(bounds.minX, bounds.maxY + 3 - metadata, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.minY - 0 - metadata, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.maxY + 3 - metadata, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.minY - 0 - metadata, bounds.maxZ);
            tessellator.addVertex(bounds.maxX, bounds.maxY + 3 - metadata, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.minY - 0 - metadata, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.maxY + 3 - metadata, bounds.maxZ);
            tessellator.draw();
            //上面的四条边
            //按顺时针方向绘制了一个矩形
            tessellator.startDrawing(GL11.GL_LINE_STRIP);
            tessellator.addVertex(bounds.minX, bounds.maxY + 3 - metadata, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.maxY + 3 - metadata, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.maxY + 3 - metadata, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.maxY + 3 - metadata, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.maxY + 3 - metadata, bounds.minZ);
            tessellator.draw();
            //下面四条边
            //按顺时针方向绘制了一个矩形
            tessellator.startDrawing(GL11.GL_LINE_STRIP);
            tessellator.addVertex(bounds.minX, bounds.minY - 0 - metadata, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.minY - 0 - metadata, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.minY - 0 - metadata, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.minY - 0 - metadata, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.minY - 0 - metadata, bounds.minZ);
            tessellator.draw();

        }
        else {
            //竖着的四条边
            tessellator.startDrawing(GL11.GL_LINES);
            tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
            tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);

            tessellator.addVertex(bounds.maxX, bounds.minY, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.minZ);

            tessellator.addVertex(bounds.maxX, bounds.minY, bounds.maxZ);
            tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.maxZ);

            tessellator.addVertex(bounds.minX, bounds.minY, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.maxY, bounds.maxZ);
            tessellator.draw();
            //上面的四条边
            //按顺时针方向绘制了一个矩形
            tessellator.startDrawing(GL11.GL_LINE_STRIP);
            tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.maxY, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);
            tessellator.draw();
            //下面四条边
            //按顺时针方向绘制了一个矩形
            tessellator.startDrawing(GL11.GL_LINE_STRIP);
            tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.minY, bounds.minZ);
            tessellator.addVertex(bounds.maxX, bounds.minY, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.minY, bounds.maxZ);
            tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
            tessellator.draw();
        }
    }
    private void drawWindmillGroundBlockHighLight(Tessellator tessellator, AxisAlignedBB bounds, int metadata){
        switch (metadata){
            case 0:
                //竖着的四条边
                tessellator.startDrawing(GL11.GL_LINES);
                tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
                tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);

                tessellator.addVertex(bounds.maxX, bounds.minY, bounds.minZ);
                tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.minZ);

                tessellator.addVertex(bounds.maxX, bounds.minY, bounds.maxZ);
                tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.maxZ);

                tessellator.addVertex(bounds.minX, bounds.minY, bounds.maxZ);
                tessellator.addVertex(bounds.minX, bounds.maxY, bounds.maxZ);
                tessellator.draw();
                //上面的四条边
                //按顺时针方向绘制了一个矩形
                tessellator.startDrawing(GL11.GL_LINE_STRIP);
                tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);
                tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.minZ);
                tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.maxZ);
                tessellator.addVertex(bounds.minX, bounds.maxY, bounds.maxZ);
                tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);
                tessellator.draw();
                //下面四条边
                //按顺时针方向绘制了一个矩形
                tessellator.startDrawing(GL11.GL_LINE_STRIP);
                tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
                tessellator.addVertex(bounds.maxX, bounds.minY, bounds.minZ);
                tessellator.addVertex(bounds.maxX, bounds.minY, bounds.maxZ);
                tessellator.addVertex(bounds.minX, bounds.minY, bounds.maxZ);
                tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
                tessellator.draw();
                break;
            case 1:
                //竖着的四条边
                tessellator.startDrawing(GL11.GL_LINES);
                tessellator.addVertex(bounds.minX + 0.0, bounds.minY, bounds.minZ + 0.0);
                tessellator.addVertex(bounds.minX + 0.0, bounds.maxY, bounds.minZ + 0.0);

                tessellator.addVertex(bounds.maxX + 1.7, bounds.minY, bounds.minZ + 0.0);
                tessellator.addVertex(bounds.maxX + 1.7, bounds.maxY, bounds.minZ + 0.0);

                tessellator.addVertex(bounds.maxX + 1.7, bounds.minY, bounds.maxZ + 1.7);
                tessellator.addVertex(bounds.maxX + 1.7, bounds.maxY, bounds.maxZ + 1.7);

                tessellator.addVertex(bounds.minX + 0.0, bounds.minY, bounds.maxZ + 1.7);
                tessellator.addVertex(bounds.minX + 0.0, bounds.maxY, bounds.maxZ + 1.7);
                tessellator.draw();
                //下面四条边
                tessellator.startDrawing(GL11.GL_LINE_STRIP);
                tessellator.addVertex(bounds.minX + 0.0, bounds.minY, bounds.minZ + 0.0);
                tessellator.addVertex(bounds.maxX + 1.7, bounds.minY, bounds.minZ + 0.0);
                tessellator.addVertex(bounds.maxX + 1.7, bounds.minY, bounds.maxZ + 1.7);
                tessellator.addVertex(bounds.minX + 0.0, bounds.minY, bounds.maxZ + 1.7);
                tessellator.addVertex(bounds.minX + 0.0, bounds.minY, bounds.minZ + 0.0);
                tessellator.draw();
                //上面四条边
                tessellator.startDrawing(GL11.GL_LINE_STRIP);
                tessellator.addVertex(bounds.minX + 0.0, bounds.maxY, bounds.minZ + 0.0);
                tessellator.addVertex(bounds.maxX + 1.7, bounds.maxY, bounds.minZ + 0.0);
                tessellator.addVertex(bounds.maxX + 1.7, bounds.maxY, bounds.maxZ + 1.7);
                tessellator.addVertex(bounds.minX + 0.0, bounds.maxY, bounds.maxZ + 1.7);
                tessellator.addVertex(bounds.minX + 0.0, bounds.maxY, bounds.minZ + 0.0);
                tessellator.draw();
        }
    }

=======
>>>>>>> parent of 3634a08... 修改风车选中时的高亮
    @Cancelable
    public static class PlayerRightClickGrassBlockEvent extends net.minecraftforge.event.entity.player.PlayerEvent
    {
        public final int blockX;
        public final int blockY;
        public final int blockZ;
        public final World world;
        public PlayerRightClickGrassBlockEvent(EntityPlayer player, int x, int y,int z, World world)
        {
            super(player);
            this.blockX = x;
            this.blockY = y;
            this.blockZ = z;
            this.world = world;
        }
    }


    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event)
    {
        //System.out.print("OnFillBucket");
        int x = event.target.blockX;
        int y = event.target.blockY;
        int z = event.target.blockZ;
        Block block = event.world.getBlock(x, y, z);
        Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
        if (fluid != null && event.world.getBlockMetadata(x, y, z) == 0)
        {
            //System.out.print("IfOnFillBucket");
            FluidStack fluidstack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
            event.world.setBlockToAir(x, y, z);
            event.result = FluidContainerRegistry.fillFluidContainer(fluidstack, event.current);
            event.setResult(Event.Result.ALLOW);
        }
    }
}
