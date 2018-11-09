package com.github.mrstop.stdemo.common;


import com.github.mrstop.stdemo.achievement.AchievementLoader;
import com.github.mrstop.stdemo.block.BlockLoader;
import com.github.mrstop.stdemo.client.KeyLoader;
import com.github.mrstop.stdemo.common.utils.SpecialBlockHighLight;
import com.github.mrstop.stdemo.core.ICustomHighLight;
import com.github.mrstop.stdemo.enchantment.EnchantmentLoader;
import com.github.mrstop.stdemo.item.ItemLoader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
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
import org.lwjgl.opengl.GL11;


public class EventLoader {

    EntityPlayer entityPlayer;

    public static final EventBus EVENT_BUS = new EventBus();


    public EventLoader() {

        //大概Forge的所有的事件都在这下面了吧?
        //P \net
        //P  |-\minecraftforge
        //P  |---\client
        //P  |------\event
        //C  |          ClientChatReceivedEvent
        //C  |          DrawBlockHighlightEvent
        //C  |          EntityViewRenderEvent
        //C  |          EntityViewRenderEvent.FogColors
        //C  |          EntityViewRenderEvent.FogDensity
        //C  |          EntityViewRenderEvent.RenderFogEvent
        //C  |          FOVUpdateEvent
        //C  |          GuiOpenEvent
        //C  |          GuiScreenEvent
        //C  |          GuiScreenEvent.ActionPerformedEvent
        //C  |          GuiScreenEvent.ActionPerformedEvent.Post
        //C  |          GuiScreenEvent.ActionPerformedEvent.Pre
        //C  |          GuiScreenEvent.DrawScreenEvent
        //C  |          GuiScreenEvent.DrawScreenEvent.Post
        //C  |          GuiScreenEvent.DrawScreenEvent.Pre
        //C  |          GuiScreenEvent.InitGuiEvent
        //C  |          GuiScreenEvent.InitGuiEvent.Post
        //C  |          GuiScreenEvent.InitGuiEvent.Pre
        //C  |          MouseEvent
        //C  |          RenderBlockOverlayEvent
        //C  |          RenderGameOverlayEvent
        //C  |          RenderGameOverlayEvent.Chat
        //C  |          RenderGameOverlayEvent.Post
        //C  |          RenderGameOverlayEvent.Pre
        //C  |          RenderGameOverlayEvent.Text
        //C  |          RenderHandEvent
        //C  |          RenderItemInFrameEvent
        //C  |          RenderLivingEvent
        //C  |          RenderLivingEvent.Post
        //C  |          RenderLivingEvent.Pre
        //C  |          RenderLivingEvent.Specials
        //C  |          RenderLivingEvent.Specials.Post
        //C  |          RenderLivingEvent.Specials.Pre
        //C  |          RenderPlayerEvent
        //C  |          RenderPlayerEvent.Post
        //C  |          RenderPlayerEvent.Pre
        //C  |          RenderPlayerEvent.SetArmorModel
        //C  |          RenderPlayerEvent.Specials
        //C  |          RenderPlayerEvent.Specials.Post
        //C  |          RenderPlayerEvent.Specials.Pre
        //C  |          RenderWorldEvent
        //C  |          RenderWorldEvent.Post
        //C  |          RenderWorldEvent.Pre
        //C  |          RenderWorldLastEvent
        //C  |          TextureStitchEvent
        //C  |          TextureStitchEvent.Post
        //C  |          TextureStitchEvent.Pre
        //P  |---------\sound
        //C  |             PlayBackgroundMusicEvent
        //C  |             PlaySoundEffectEvent
        //C  |             PlaySoundEffectSourceEvent
        //C  |             PlaySoundEvent
        //C  |             PlaySoundEvent17
        //C  |             PlaySoundSourceEvent
        //C  |             PlayStreamingEvent
        //C  |             PlayStreamingSourceEvent
        //C  |             SoundEvent
        //C  |             SoundEvent.SoundSourceEvent
        //C  |             SoundLoadEvent
        //C  |             SoundResultEvent
        //C  |             SoundSetupEvent
        //P  |---\event
        //C  |      AnvilUpdateEvent
        //C  |      CommandEvent
        //C  |      ForgeEventFactory
        //C  |      ServerChatEvent
        //P  |-----\brewing
        //C  |        PotionBrewedEvent
        //C  |        PotionBrewEvent
        //C  |        PotionBrewEvent.Post
        //C  |        PotionBrewEvent.Pre
        //P  |-----\entity
        //C  |        EntityEvent
        //C  |        EntityEvent.CanUpdate
        //C  |        EntityEvent.EnteringChunk
        //C  |        EntityEvent.EntityConstructing
        //C  |        EntityJoinWorldEvent
        //C  |        EntityStruckByLightningEvent
        //C  |        PlaySoundAtEntityEvent
        //P  |-------\item
        //C  |          ItemEvent
        //C  |          ItemExpireEvent
        //C  |          ItemTossEvent
        //P  |-------\living
        //C  |          EnderTeleportEvent
        //C  |          LivingAttackEvent
        //C  |          LivingDeathEvent
        //C  |          LivingDropsEvent
        //C  |          LivingEvent
        //C  |          LivingEvent.LivingJumpEvent
        //C  |          LivingEvent.LivingUpdateEvent
        //C  |          LivingFallEvent
        //C  |          LivingHealEvent
        //C  |          LivingHurtEvent
        //C  |          LivingPackSizeEvent
        //C  |          LivingSetAttackTargetEvent
        //C  |          LivingSpawnEvent
        //C  |          LivingSpawnEvent.AllowDespawn
        //C  |          LivingSpawnEvent.CheckSpawn
        //C  |          LivingSpawnEvent.SpecialSpawn
        //C  |          ZombieEvent
        //C  |          ZombieEvent.SummonAidEvent
        //P  |-------\minecart
        //C  |          MinecartCollisionEvent
        //C  |          MinecartEvent
        //C  |          MinecartInteractEvent
        //C  |          MinecartUpdateEvent
        //P  |-------\player
        //C  |          AchievementEvent
        //C  |          AnvilRepairEvent
        //C  |          ArrowLooseEvent
        //C  |          ArrowNockEvent
        //C  |          AttackEntityEvent
        //C  |          BonemealEvent
        //C  |          EntityInteractEvent
        //C  |          EntityItemPickupEvent
        //C  |          FillBucketEvent
        //C  |          ItemTooltipEvent
        //C  |          PlayerDestroyItemEvent
        //C  |          PlayerDropsEvent
        //C  |          PlayerEvent
        //C  |          PlayerEvent.BreakSpeed
        //C  |          PlayerEvent.Clone
        //C  |          PlayerEvent.HarvestCheck
        //C  |          PlayerEvent.LoadFromFile
        //C  |          PlayerEvent.NameFormat
        //C  |          PlayerEvent.SaveToFile
        //C  |          PlayerEvent.StartTracking
        //C  |          PlayerEvent.StopTracking
        //C  |          PlayerFlyableFallEvent
        //C  |          PlayerInteractEvent
        //C  |          PlayerOpenContainerEvent
        //C  |          PlayerPickupXpEvent
        //C  |          PlayerSleepInBedEvent
        //C  |          PlayerUseItemEvent
        //C  |          PlayerUseItemEvent.Finish
        //C  |          PlayerUseItemEvent.Start
        //C  |          PlayerUseItemEvent.Stop
        //C  |          PlayerUseItemEvent.Tick
        //C  |          PlayerWakeUpEvent
        //C  |          UseHoeEvent
        //P  |-----\terraingen
        //C  |        BiomeEvent
        //C  |        BiomeEvent.BiomeColor
        //C  |        BiomeEvent.CreateDecorator
        //C  |        BiomeEvent.GetFoliageColor
        //C  |        BiomeEvent.GetGrassColor
        //C  |        BiomeEvent.GetVillageBlockID
        //C  |        BiomeEvent.GetVillageBlockMeta
        //C  |        BiomeEvent.GetWaterColor
        //C  |        ChunkProviderEvent
        //C  |        ChunkProviderEvent.InitNoiseField
        //C  |        ChunkProviderEvent.ReplaceBiomeBlocks
        //C  |        DecorateBiomeEvent
        //C  |        DecorateBiomeEvent.Decorate
        //C  |        DecorateBiomeEvent.Post
        //C  |        DecorateBiomeEvent.Pre
        //C  |        DeferredBiomeDecorator
        //C  |        InitMapGenEvent
        //C  |        InitNoiseGensEvent
        //C  |        OreGenEvent
        //C  |        OreGenEvent.GenerateMinable
        //C  |        OreGenEvent.Post
        //C  |        OreGenEvent.Pre
        //C  |        PopulateChunkEvent
        //C  |        PopulateChunkEvent.Populate
        //C  |        PopulateChunkEvent.Post
        //C  |        PopulateChunkEvent.Pre
        //C  |        SaplingGrowTreeEvent
        //C  |        TerrainGen
        //C  |        WorldTypeEvent
        //C  |        WorldTypeEvent.BiomeSize
        //C  |        WorldTypeEvent.InitBiomeGens
        //P  |-----\world
        //C  |        BlockEvent
        //C  |        BlockEvent.BreakEvent
        //C  |        BlockEvent.HarvestDropsEvent
        //C  |        BlockEvent.MultiPlaceEvent
        //C  |        BlockEvent.PlaceEvent
        //C  |        ChunkDataEvent
        //C  |        ChunkDataEvent.Load
        //C  |        ChunkDataEvent.Save
        //C  |        ChunkEvent
        //C  |        ChunkEvent.Load
        //C  |        ChunkEvent.Unload
        //C  |        ChunkWatchEvent
        //C  |        ChunkWatchEvent.UnWatch
        //C  |        ChunkWatchEvent.Watch
        //C  |        ExplosionEvent
        //C  |        ExplosionEvent.Detonate
        //C  |        ExplosionEvent.Start
        //C  |        NoteBlockEvent
        //C  |        NoteBlockEvent.Change
        //C  |        NoteBlockEvent.Play
        //C  |        WorldEvent
        //C  |        WorldEvent.CreateSpawnPosition
        //C  |        WorldEvent.Load
        //C  |        WorldEvent.PotentialSpawns
        //C  |        WorldEvent.Save
        //C  |        WorldEvent.Unload
        MinecraftForge.EVENT_BUS.register(this);

        //按键绑定和合成事件在注册到FML中才有效?
        //或许是下面这些事件都是注册到FML的事件总线才有效?
        //大概FML的所有的事件都在这下面了吧?
        //P  \cpw
        //P  |-\mods
        //P  |----\fml
        //P  |-------\client
        //P  |-----------\event
        //C  |                ConfigChangedEvent
        //C  |                ConfigChangedEvent.OnConfigChangedEvent
        //C  |                ConfigChangedEvent.PostConfigChangedEvent
        //P  |-------\common
        //P  |-----------\event
        //C  |                FMLConstructionEvent
        //C  |                FMLEvent
        //C  |                FMLFingerprintViolationEvent
        //C  |                FMLInitializationEvent
        //C  |                FMLInterModComms
        //C  |                FMLInterModComms.IMCEvent
        //C  |                FMLInterModComms.IMCMessage
        //C  |                FMLLoadCompleteEvent
        //C  |                FMLLoadEvent
        //C  |                FMLMissingMappingsEvent
        //C  |                FMLMissingMappingsEvent.MissingMapping
        //C  |                FMLModDisabledEvent
        //C  |                FMLModIdMappingEvent
        //C  |                FMLPostInitializationEvent
        //C  |                FMLPreInitializationEvent
        //C  |                FMLServerAboutToStartEvent
        //C  |                FMLServerStartedEvent
        //C  |                FMLServerStartingEvent
        //C  |                FMLServerStoppedEvent
        //C  |                FMLServerStoppingEvent
        //C  |                FMLStateEvent
        //C  |
        //P  |------------\gameevent
        //C  |                InputEvent
        //C  |                InputEvent.KeyInputEvent
        //C  |                InputEvent.MouseInputEvent
        //C  |                PlayerEvent
        //C  |                PlayerEvent.ItemCraftedEvent
        //C  |                PlayerEvent.ItemPickupEvent
        //C  |                PlayerEvent.ItemSmeltedEvent
        //C  |                PlayerEvent.PlayerChangedDimensionEvent
        //C  |                PlayerEvent.PlayerLoggedInEvent
        //C  |                PlayerEvent.PlayerLoggedOutEvent
        //C  |                PlayerEvent.PlayerRespawnEvent
        //C  |                TickEvent
        //C  |                TickEvent.ClientTickEvent
        //C  |                TickEvent.PlayerTickEvent
        //C  |                TickEvent.RenderTickEvent
        //C  |                TickEvent.ServerTickEvent
        //C  |                TickEvent.WorldTickEvent
        FMLCommonHandler.instance().bus().register(this);

        EventLoader.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event) {
        //if (!event.player.isServerWorld())
        {
            String info = String.format("%s picks up: %s", event.player.getGameProfile().getName(), event.pickedUp.getEntityItem().getUnlocalizedName());
            Log.Log.info(info);
            Log.Log.info("ONPLAYERITEMPICKUP");
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.world.isRemote) {
            String info = String.format("%s interacts with: %d, %d, %d, ", event.entityPlayer.getGameProfile().getName(), event.x, event.y, event.z);
            Log.Log.info(info);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void test2(LivingFallEvent event) {
        if (event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
            entityPlayer.addChatMessage(new ChatComponentText("Falling Star! You fell "+event.distance+ " meters. That's cool, man! I will protect you from the falling damage!"));
            event.distance = 0.0F;
        }
    }


    @SubscribeEvent
    public void test1(LivingFallEvent event) {
        if(event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayer entityPlayer = (EntityPlayer)event.entityLiving;
            entityPlayer.addChatMessage(new ChatComponentText("Falling Star! You fell "+event.distance+ " meters.That's cool, man!"));
        }
    }

    @SubscribeEvent
    public void onPlayerClickGrassBlock(PlayerRightClickGrassBlockEvent event) {
        //生成TNT
        if (!event.world.isRemote) {
            int blockX = event.blockX;
            int blockY = event.blockY;
            int blockZ = event.blockZ;
            Entity tnt = new EntityTNTPrimed(event.world, blockX + 0.5, blockY + 0.5, blockZ + 0.5, null);
            event.world.spawnEntityInWorld(tnt);
            event.entityPlayer.attackEntityFrom(DamageSource.lava, 8.0F);
            event.entityPlayer.triggerAchievement(AchievementLoader.explosionFromGrassBlock);
        }

        //生成金鸡
        if (!event.world.isRemote) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();
            if (heldItem == null) {
                return;
            }
            if (ItemLoader.goldenEgg.equals(heldItem.getItem())) {
                EntityLiving entityLiving = new EntityChicken(event.world);
                int blockX = event.blockX;
                int blcokY = event.blockY;
                int blockZ = event.blockZ;
                entityLiving.setPositionAndUpdate(blockX + 0.5D, blcokY + 1.0D, blockZ + 0.5D);
                if (heldItem != null) {
                    --heldItem.stackSize;
                    event.world.spawnEntityInWorld(entityLiving);
                }
                return;
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event) {
        EntityPlayer player = event.entityPlayer;
        if (player.isServerWorld() && event.target instanceof EntityPig) {
            EntityPig pig = (EntityPig)  event.target;
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && (stack.getItem() == Items.wheat || stack.getItem() == Items.wheat_seeds)) {
                player.attackEntityFrom((new DamageSource("byPig")).setDifficultyScaled().setExplosion(), 8.0F);
                player.worldObj.createExplosion(pig, pig.posX, pig.posY, pig.posZ, 2.0F, false);
                pig.setDead();
            }
        }
    }

    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        if (!event.world.isRemote && event.harvester != null) {
            ItemStack itemstack = event.harvester.getHeldItem();
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentLoader.fireburn.effectId, itemstack) > 0 && itemstack.getItem() != Items.shears) {
                for (int i = 0; i < event.drops.size(); ++i) {
                    ItemStack stack = event.drops.get(i);
                    ItemStack newStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (newStack != null) {
                        newStack = newStack.copy();
                        newStack.stackSize = stack.stackSize;
                        event.drops.set(i, newStack);
                    }
                    else if (stack != null) {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        boolean b = (block == null);
                        if (!b &&
                                (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.DOWN))
                                || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.EAST))
                                || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.NORTH))
                                || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.SOUTH))
                                || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.UP))
                                || (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.WEST))) {
                            event.drops.remove(i);
                        }
                    }
                }
            }
        }
    }

//    @SubscribeEvent
//    public void onLivingHurt(LivingHurtEvent event)
//    {
//        if (event.source.getDamageType().equals("fall"))
//        {
//            PotionEffect effect = event.entityLiving.getActivePotionEffect(PotionLoader.potionFallProtection);
//            if (effect != null)
//            {
//                if (effect.getAmplifier() == 0)
//                {
//                    event.ammount /= 2;
//                }
//                else
//                {
//                    event.ammount = 0;
//                }
//            }
//        }
//    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayer && event.source.getDamageType().equals("byPig")) {
            ((EntityPlayer) event.entityLiving).triggerAchievement(AchievementLoader.worseThanPig);
        }
    }

    @SubscribeEvent
    public void onPlayerItemCrafting(PlayerEvent.ItemCraftedEvent event) {
        event.player.worldObj.playSoundAtEntity(event.player, "stdemo:stdemo.test", 1.0F, 1.0F);
        //if (event.crafting.getItem() == Item.getItemFromBlock(BlockLoader.grassBlock))
        {
            event.player.triggerAchievement(AchievementLoader.buildGrassBlock);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        System.out.println("KeyInputEvent");
        if (KeyLoader.showTime.isPressed()) {
            System.out.println("KeyInputEventKeyK");
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            World world = Minecraft.getMinecraft().theWorld;
            player.addChatComponentMessage(new ChatComponentTranslation("chat.stdemo.time", world.getTotalWorldTime()));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void drawBLockHighLight(DrawBlockHighlightEvent event){
        if (event.target.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK)){
            Block block = event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
            if (block instanceof ICustomHighLight){
                drawBlockHighLight((ICustomHighLight) block);
            }
        }
    }

    //TODO 完善选中高亮
    @SideOnly(Side.CLIENT)
    private void drawBlockHighLight(ICustomHighLight block){

    }

    @Cancelable
    public static class PlayerRightClickGrassBlockEvent extends net.minecraftforge.event.entity.player.PlayerEvent {
        public final int blockX;
        public final int blockY;
        public final int blockZ;
        public final World world;
        public PlayerRightClickGrassBlockEvent(EntityPlayer player, int x, int y,int z, World world) {
            super(player);
            this.blockX = x;
            this.blockY = y;
            this.blockZ = z;
            this.world = world;
        }
    }


    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event) {
        //System.out.print("OnFillBucket");
        int x = event.target.blockX;
        int y = event.target.blockY;
        int z = event.target.blockZ;
        Block block = event.world.getBlock(x, y, z);
        Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
        if (fluid != null && event.world.getBlockMetadata(x, y, z) == 0) {
            //System.out.print("IfOnFillBucket");
            FluidStack fluidstack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
            event.world.setBlockToAir(x, y, z);
            event.result = FluidContainerRegistry.fillFluidContainer(fluidstack, event.current);
            event.setResult(Event.Result.ALLOW);
        }
    }
}
