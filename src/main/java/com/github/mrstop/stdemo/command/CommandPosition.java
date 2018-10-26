package com.github.mrstop.stdemo.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Vec3;

public class CommandPosition extends CommandBase {

    @Override
    public String getCommandName() {
        return "position";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "command.position.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 1) {
            throw new WrongUsageException("commands.position.usage");
        }
        else {
            EntityPlayerMP entityPlayerMP = args.length > 0 ? CommandBase.getPlayer(sender, args[0])
                    : CommandBase.getCommandSenderAsPlayer(sender);
            sender.addChatMessage(new ChatComponentTranslation("commands.position.success", entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.worldObj.provider.getDimensionName()));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
