package com.melonstudios.melonlib.command;

import com.melonstudios.melonlib.misc.MetaBlock;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandOreDict extends CommandBase {
    @Override
    public String getName() {
        return "oredict";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.oredict.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException("/oredict <list|get>");

        if ("list".equals(args[0])) {
            String[] names = OreDictionary.getOreNames();
            sender.sendMessage(new TextComponentString("All OreDict entries:"));
            for (String name : names) {
                sender.sendMessage(new TextComponentString(name + " (" + OreDictionary.getOres(name, false).size() + ")"));
            }
        } else if ("get".equals(args[0])) {
            if (args.length == 1) throw new WrongUsageException("/oredict get <ore>");
            String ore = args[1];
            NonNullList<ItemStack> ores = OreDictionary.getOres(ore, false);
            if (!ores.isEmpty()) {
                sender.sendMessage(new TextComponentString("All entries tagged with " + ore + ":"));
                for (ItemStack stack : ores) {
                    sender.sendMessage(new TextComponentString(stack.getItem().getRegistryName().toString() + "/" + stack.getMetadata()));
                }
            } else {
                sender.sendMessage(new TextComponentString("No ores with tag " + ore));
            }
        } else throw new WrongUsageException("/oredict <list|get>");
    }
}
