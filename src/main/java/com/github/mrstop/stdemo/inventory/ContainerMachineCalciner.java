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
     * ����Ұ�ס���һ�����ʱ���á�����븲��������������������ʱ��������
     * ************************************************************
     * Minecraft��ȡ�İ취���Ƚ���һ����Ҳ�������Ű�һ��ItemStack�Ĳ��ַ����һ�����õ���Ʒ�ۣ�
     * ���û�пɷ������Ʒ���򷵻�null�������Ȼ�����пɷ������Ʒ�ۣ��򷵻ؾɵ�ItemStack��
     * ************************************************************
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemStack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);
        //����������Ʒ���ǿյĻ���û����Ʒ���������itemstackִ���κθ�ֵ������������null(��Ϊû����Ʒ��Ҫ����������Ʒ��)
        if (slot != null && slot.getHasStack()) {
            //��ȡ�����Ƹ���������Ӧ��Ʒ�۵���Ʒ(Ӧ���Ǳ�������Ǹ���)
            ItemStack oldStack = slot.getStack();
            itemStack = oldStack.copy();

            // If itemStack is in Output stack
            // ���itemStack�������ջ��
            if (index == OUTPUT) {
                // try to place in player inventory / action bar; add 36+1 because mergeItemStack uses < index,
                // so the last slot in the inventory won't get checked if you don't add 1
                // ���Է�����ҿ��/������;���36 + 1����ΪmergeItemStackʹ��<index��
                //����������1���򲻻����嵥�е����һ�����
                //
                // mergeItemStack
                //
                // Merges provided ItemStack with the first avaliable one in the container/player inventor between minIndex
                // (included) and maxIndex (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the Container
                // implementation do not check if the item is valid for the slot
                //
                //�����ṩ��ItemStack�� ����/��� �������������minIndex(����)��maxIndex(������)֮��ĵ�һ�����õ���Ʒ�ۺϲ���
                // Args: stack��minIndex��maxIndex��negativDirection(Ӧ����negativeDirection��)��
                // ! Containerʵ�ֲ�������Ʒ����Ʒ����Ч(����Ʒ���Ƿ���Է������Ʒ(���))
                // ͨ������£�����ӷ���ұ�����GUI��Ʒ������ͼ����Ʒ���������Ʒ�ۣ���ôʹ�÷�����ң�
                // �������ұ�������ͼ����Ʒ������ұ��������ұ�����Ʒ�ۣ���ʹ��������ҡ�
                // ����ֵ���ڱ�ʶ�Ƿ�ɹ�����Ʒ�Ĳ��ַ�����һ�����õ���Ʒ�ۣ�����ɹ������ˣ��򷵻��棬���򷵻ؼ١�
                if (!this.mergeItemStack(oldStack, /*OUTPUT*/1+1, /*OUTPUT*/1+36+1, true)) {
                    //�������������Ʒ�ۣ���û���ҵ��ɷ������Ʒ�ۣ����԰���Լ������null
                    return null;
                }
                // ���ò���
                // if par2 has more items than par1, onCrafting(item,countIncrease) is called
                // ���itemStack����Ʒ����oldStack�������onCrafting(item��countIncrease)
                slot.onSlotChange(oldStack, itemStack);
            }
            // itemStack is in player inventory, try to place in appropriate furnace slot
            // itemStack����ҿ���У����Է����ʵ���¯��
            else if (/*index != FUEL &&*/ index != INPUT /*&& index != INPUT_2*/) {
                // if it can be smelted, place in the input slots
                // ����������������Է����������
                if (CraftingLoader.recipeCalciner.canCalcine(oldStack, false)) {
                    // try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
                    // ���Է�����һ����ۣ�maxIndex����Ϊ���һ������۵�����+1����ΪmergeItemStackʹ�� < maxIndex
                    // (�����������������Ժϲ�ItemStackʱ���������һ������)
                    if (!this.mergeItemStack(oldStack, INPUT, INPUT +1, false)) {
                        return null;
                    }
                }
                // if it's an energy source, place in Fuel slot
                // ���������Դ�������Fuel�����
//                else if (TileEntityArcaneInfuser.isItemFuel(oldStack)) {
//                    if (!this.mergeItemStack(oldStack, FUEL, FUEL+1, false)) {
//                        return null;
//                    }
//                }
                // item in player's inventory, but not in action bar
                // ��ҿ���е���Ŀ�������ڲ�������
//                else if (index >= OUTPUT+1 && index < OUTPUT+28) {
//                    // place in action bar
//                    // �����ڲ�������
//                    if (!this.mergeItemStack(oldStack, OUTPUT+28, OUTPUT+37, false)) {
//                        return null;
//                    }
//                }
//                // item in action bar - place in player inventory
//                // �������е���Ŀ - ��������ҿ����
//                else if (index >= OUTPUT+28 && index < OUTPUT+37 && !this.mergeItemStack(oldStack, OUTPUT+1, OUTPUT+28, false)) {
//                    return null;
//                }
            }
            // In one of the infuser slots; try to place in player inventory / action bar
            //������һ���������;���Է�����ҿ��/������
            else if (!this.mergeItemStack(oldStack, OUTPUT+1, OUTPUT+37, false)) {
                return null;
            }

            if (oldStack.stackSize <= 0) {
                //���������Ĳ��е�ItemStack.stackSizeС�ڵ���0(��û����Ʒ)����ձ�����Ĳۡ�
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if (oldStack.stackSize == itemStack.stackSize) {
                //���������Ʒ�����ItemStackû�з����ı䣬��ζ��û�пɷ���Ĳۡ�
                return null;
            }
            // ��ΪMojang���ص���Ʒ�ʽ���е���Ʒ�ۻ��ḲдonPickupFromSlot�������д���
            // Ϊ�˶������֧�֣�ͬʱ���������������
            slot.onPickupFromSlot(player, oldStack);
        }
        return itemStack;
    }
}
