package com.github.mrstop.stdemo.command;

import net.minecraft.command.CommandBase;

public abstract class STDemoCommandBase extends CommandBase {
    protected static final int COMMAND_LEVEL_ANY = 1;
    protected static final int COMMAND_LEVEL_COMMAND_BLOCK = 2;
    protected static final int COMMAND_LEVEL_THREE = 3;
    protected static final int COMMAND_LEVEL_OP = 4;

    protected final String COMMAND_USAGE;
    protected final String COMMAND_NAME;

    public STDemoCommandBase(String commandUsage, String commandName) {
        this.COMMAND_USAGE = commandUsage;
        this.COMMAND_NAME = commandName;
    }
}
