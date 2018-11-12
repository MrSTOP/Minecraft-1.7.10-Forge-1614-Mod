package com.github.mrstop.stdemo.inventory;

import com.github.mrstop.stdemo.crafting.CraftingLoader;
import com.github.mrstop.stdemo.tileentity.TileEntityMachineCalciner;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCalciner extends Container {
    public static final int INPUT = 0;
    public static final int OUTPUT = 1;

//    public static final int INPUT_2 = 1;
//    public static final int FUEL = 2;

    TileEntityMachineCalciner tileEntityMachineCalciner;
    private int processTime = 0;
    private int fluidID = 0;
    private int fluidAmount = 0;
    private int energyAmount = 0;

    public ContainerMachineCalciner(InventoryPlayer inventoryPlayer, TileEntityMachineCalciner tileEntityMachineCalciner) {
        this.tileEntityMachineCalciner = tileEntityMachineCalciner;
        this.addSlotToContainer(new Slot(tileEntityMachineCalciner, INPUT, 56, 32));
        this.addSlotToContainer(new Slot(tileEntityMachineCalciner, OUTPUT, 105, 32));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onCraftGuiOpened(ICrafting iCrafting) {
        super.onCraftGuiOpened(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityMachineCalciner.getProcessTime());
        iCrafting.sendProgressBarUpdate(this, 1, this.tileEntityMachineCalciner.getEnergyAmount());
        iCrafting.sendProgressBarUpdate(this, 2, this.tileEntityMachineCalciner.getFluidAmount(0));
        iCrafting.sendProgressBarUpdate(this, 3, this.tileEntityMachineCalciner.getFluidID(0));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int tempProcessTime = this.tileEntityMachineCalciner.getProcessTime();
        int tempEnergyAmount = this.tileEntityMachineCalciner.getEnergyAmount();
        int tempFluidAmount = this.tileEntityMachineCalciner.getFluidAmount(0);
        int tempFluidID = this.tileEntityMachineCalciner.getFluidID(0);
        for (Object crafter: this.crafters) {
            ICrafting iCrafting = (ICrafting) crafter;
            if (this.processTime != tempProcessTime){
                iCrafting.sendProgressBarUpdate(this, 0, tempProcessTime);
            }
            if (this.energyAmount != tempEnergyAmount){
                iCrafting.sendProgressBarUpdate(this, 1, tempEnergyAmount);
            }
            if (this.fluidAmount != tempFluidAmount){
                iCrafting.sendProgressBarUpdate(this, 2, tempFluidAmount);
            }
            if (this.fluidID != tempFluidID){
                iCrafting.sendProgressBarUpdate(this, 3, tempFluidID);
            }
        }
        this.processTime = tempProcessTime;
        this.fluidAmount = tempFluidAmount;
        this.energyAmount = tempEnergyAmount;
        this.fluidID = tempFluidID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int ID, int data) {
        switch (ID){
            case 0:
                this.tileEntityMachineCalciner.GUIProcessTime = data;
                break;
            case 1:
                this.tileEntityMachineCalciner.GUIEnergyAmount = data;
                break;
            case 2:
                this.tileEntityMachineCalciner.GUIFluidAmount = data;
                break;
            case 3:
                this.tileEntityMachineCalciner.GUIFluidID = data;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntityMachineCalciner.isUseableByPlayer(player);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     * 当玩家按住点击一个插槽时调用。你必须覆盖它，否则当有人这样做时你会崩溃。
     * ************************************************************
     * Minecraft采取的办法是先进行一步，也就是试着把一个ItemStack的部分放入第一个可用的物品槽，
     * 如果没有可放入的物品槽则返回null，如果仍然可能有可放入的物品槽，则返回旧的ItemStack。
     * ************************************************************
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemStack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);
        //如果点击的物品槽是空的或者没有物品，将不会对itemstack执行任何赋值操作，即返回null(因为没有物品需要放入其他物品槽)
        if (slot != null && slot.getHasStack()) {
            //获取并复制给定索引对应物品槽的物品(应该是被点击的那个槽)
            ItemStack oldStack = slot.getStack();
            itemStack = oldStack.copy();

            // If itemStack is in Output stack
            // 如果itemStack在输出堆栈中
            if (index == OUTPUT) {
                // try to place in player inventory / action bar; add 36+1 because mergeItemStack uses < index,
                // so the last slot in the inventory won't get checked if you don't add 1
                // 尝试放入玩家库存/操作栏;添加36 + 1，因为mergeItemStack使用<index，
                //如果您不添加1，则不会检查清单中的最后一个插槽
                //
                // mergeItemStack
                //
                // Merges provided ItemStack with the first avaliable one in the container/player inventor between minIndex
                // (included) and maxIndex (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the Container
                // implementation do not check if the item is valid for the slot
                //
                //将所提供的ItemStack与 容器/玩家 库存中索引介于minIndex(包含)与maxIndex(不包含)之间的第一个可用的物品槽合并。
                // Args: stack，minIndex，maxIndex，negativDirection(应该是negativeDirection吧)。
                // ! Container实现不检查该物品对物品槽有效(即物品槽是否可以放入该物品(大概))
                // 通常情况下，如果从非玩家背包的GUI物品槽中试图将物品放入玩家物品槽，那么使用反向查找，
                // 如果从玩家背包中试图将物品放入玩家背包或非玩家背包物品槽，则使用正向查找。
                // 返回值用于标识是否成功把物品的部分放入了一个可用的物品槽，如果成功放入了，则返回真，否则返回假。
                if (!this.mergeItemStack(oldStack, /*OUTPUT*/1+1, /*OUTPUT*/1+36+1, true)) {
                    //遍历玩家所有物品槽，但没有找到可放入的物品槽，所以按照约定返回null
                    return null;
                }
                // 作用不明
                // if par2 has more items than par1, onCrafting(item,countIncrease) is called
                // 如果itemStack的物品多于oldStack，则调用onCrafting(item，countIncrease)
                slot.onSlotChange(oldStack, itemStack);
            }
            // itemStack is in player inventory, try to place in appropriate furnace slot
            // itemStack在玩家库存中，尝试放入适当的炉槽
            else if (/*index != FUEL &&*/ index != INPUT /*&& index != INPUT_2*/) {
                // if it can be smelted, place in the input slots
                // 如果可以熔炼，尝试放入输入槽中
                if (CraftingLoader.recipeCalciner.canCalcine(oldStack, false)) {
                    // try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
                    // 尝试放入任一输入槽，maxIndex参数为最后一个输入槽的索引+1，因为mergeItemStack使用 < maxIndex
                    // (即遍历给定索引尝试合并ItemStack时不包含最后一个参数)
                    if (!this.mergeItemStack(oldStack, INPUT, INPUT +1, false)) {
                        return null;
                    }
                }
                // if it's an energy source, place in Fuel slot
                // 如果它是能源，请放在Fuel插槽中
//                else if (TileEntityArcaneInfuser.isItemFuel(oldStack)) {
//                    if (!this.mergeItemStack(oldStack, FUEL, FUEL+1, false)) {
//                        return null;
//                    }
//                }
                // item in player's inventory, but not in action bar
                // 玩家库存中的项目，但不在操作栏中
//                else if (index >= OUTPUT+1 && index < OUTPUT+28) {
//                    // place in action bar
//                    // 放置在操作栏中
//                    if (!this.mergeItemStack(oldStack, OUTPUT+28, OUTPUT+37, false)) {
//                        return null;
//                    }
//                }
//                // item in action bar - place in player inventory
//                // 操作栏中的项目 - 放置在玩家库存中
//                else if (index >= OUTPUT+28 && index < OUTPUT+37 && !this.mergeItemStack(oldStack, OUTPUT+1, OUTPUT+28, false)) {
//                    return null;
//                }
            }
            // In one of the infuser slots; try to place in player inventory / action bar
            //在其中一个输入槽中;尝试放入玩家库存/操作栏
            else if (!this.mergeItemStack(oldStack, OUTPUT+1, OUTPUT+37, false)) {
                return null;
            }

            if (oldStack.stackSize <= 0) {
                //如果被点击的槽中的ItemStack.stackSize小于等于0(即没有物品)，清空被点击的槽。
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if (oldStack.stackSize == itemStack.stackSize) {
                //被点击的物品槽里的ItemStack没有发生改变，意味着没有可放入的槽。
                return null;
            }
            // 因为Mojang独特的设计方式，有的物品槽还会覆写onPickupFromSlot方法进行处理，
            // 为了对其进行支持，同时调用了这个方法：
            slot.onPickupFromSlot(player, oldStack);
        }
        return itemStack;
    }
}
