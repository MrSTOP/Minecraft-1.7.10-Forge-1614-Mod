package com.github.mrstop.stdemo.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

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
        System.out.print("COMMAND\n");
        System.out.print("++++++++++ARGS++++++++++\n");
        for (String arg: args) {
            System.out.print(arg);
        }
        System.out.print("\n++++++++++++++++++++++++\n");

        if (args.length > 1) {
            throw new WrongUsageException(COMMAND_USAGE);
        } else {
            EntityPlayerMP entityPlayerMP = args.length > 0 ? CommandBase.getPlayer(sender, args[0])
                    : CommandBase.getCommandSenderAsPlayer(sender);
            sender.addChatMessage(new ChatComponentTranslation(COMMAND_SUCCESS,
                                                               entityPlayerMP.getDisplayName(),
                                                               String.format("%.2f", entityPlayerMP.posX),
                                                               String.format("%.2f", entityPlayerMP.posY),
                                                               String.format("%.2f", entityPlayerMP.posZ),
                                                               entityPlayerMP.worldObj.provider.getDimensionName()));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            String[] names = MinecraftServer.getServer().getAllUsernames();
            return CommandBase.getListOfStringsMatchingLastWord(args, names);
        }
        return null;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return COMMAND_LEVEL_COMMAND_BLOCK;
    }
}
