package com.github.mrstop.stdemo.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class CommandPosition extends STDemoCommandBase {
    private static final String COMMAND_SUCCESS = "commands.position.success";

    public CommandPosition() {
        super("commands.position.usage", "position");
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return COMMAND_USAGE;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        System.out.println("COMMAND");
        System.out.println("++++++++++++++++");
        for (String arg: args) {
            System.out.print(arg);
        }
        System.out.println("++++++++++++++++");

        if (args.length > 1) {
            throw new WrongUsageException(COMMAND_USAGE);
        } else {
            EntityPlayerMP entityPlayerMP = args.length > 0 ? CommandBase.getPlayer(sender, args[0])
                    : CommandBase.getCommandSenderAsPlayer(sender);
            sender.addChatMessage(new ChatComponentTranslation(COMMAND_SUCCESS, entityPlayerMP.getDisplayName(), entityPlayerMP.worldObj.provider.getDimensionName(), entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return COMMAND_LEVEL_COMMAND_BLOCK;
    }
}
