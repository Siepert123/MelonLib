package com.melonstudios.melonlib.command;

import com.melonstudios.melonlib.blockdict.BlockDictionary;
import com.melonstudios.melonlib.misc.MetaBlock;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandBlockDict extends CommandBase {
    @Override
    public String getName() {
        return "blockdict";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.blockdict.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException("/blockdict <list|get>");

        if ("list".equals(args[0])) {
            String[] names = BlockDictionary.getOreNames();
            sender.sendMessage(new TextComponentString("All BlockDict entries:"));
            for (String name : names) {
                sender.sendMessage(new TextComponentString(name + " (" + BlockDictionary.getOres(name, false).size() + ")"));
            }
        } else if ("get".equals(args[0])) {
            if (args.length == 1) throw new WrongUsageException("/blockdict get <ore>");
            String ore = args[1];
            NonNullList<MetaBlock> ores = BlockDictionary.getOres(ore, false);
            if (!ores.isEmpty()) {
                sender.sendMessage(new TextComponentString("All entries tagged with " + ore + ":"));
                for (MetaBlock block : ores) {
                    sender.sendMessage(new TextComponentString(block.toString()));
                }
            } else {
                sender.sendMessage(new TextComponentString("No ores with tag " + ore));
            }
        } else throw new WrongUsageException("/blockdict <list|get>");
    }
}
