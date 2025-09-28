package com.melonstudios.melonlib.command;

import com.melonstudios.melonlib.misc.FileUtil;
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Updated in 1.6
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

        File oreDictCommandOutputTxt = FileUtil.downloadsFile("oreDictCommandOutput.txt");

        StringBuilder out = new StringBuilder();

        if ("list".equals(args[0])) {
            String[] names = OreDictionary.getOreNames();
            sender.sendMessage(new TextComponentString("All OreDict entries written to file"));
            out.append("All OreDict entries").append("\n");
            for (String name : names) {
                out.append(name).append(" (").append(OreDictionary.getOres(name, false).size()).append(")").append("\n");
            }
        } else if ("get".equals(args[0])) {
            if (args.length == 1) throw new WrongUsageException("/oredict get <ore>");
            String ore = args[1];
            NonNullList<ItemStack> ores = OreDictionary.getOres(ore, false);
            if (!ores.isEmpty()) {
                sender.sendMessage(new TextComponentString("All entries tagged with " + ore + " written to file"));
                out.append("All entries tagged with ").append(ore).append("\n");
                for (ItemStack stack : ores) {
                    out.append(stack.getItem().getRegistryName().toString()).append("/").append(stack.getMetadata()).append("\n");
                }
            } else {
                sender.sendMessage(new TextComponentString("No ores with tag " + ore));
            }
        } else throw new WrongUsageException("/oredict <list|get>");

        String str = out.toString();
        if (!str.isEmpty()) {
            try (FileWriter writer = new FileWriter(oreDictCommandOutputTxt)) {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
                sender.sendMessage(new TextComponentString("Failed to save command output..."));
            }
        }
    }
}
