package nl.melonstudios.melonlib.command;

import nl.melonstudios.melonlib.blockdict.BlockDictionary;
import nl.melonstudios.melonlib.misc.FileUtil;
import nl.melonstudios.melonlib.misc.MetaBlock;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Updated in 1.6
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

        File blockDictCommandOutputTxt = FileUtil.downloadsFile("blockDictCommandOutput.txt");

        if (blockDictCommandOutputTxt.exists()) blockDictCommandOutputTxt.delete();

        StringBuilder out = new StringBuilder();

        if ("list".equals(args[0])) {
            String[] names = BlockDictionary.getOreNames();
            sender.sendMessage(new TextComponentString("All BlockDict entries written to file"));
            out.append("All BlockDict entries").append("\n");
            for (String name : names) {
                out.append(name).append(" (").append(BlockDictionary.getOres(name, false).size()).append(")").append("\n");
            }
        } else if ("get".equals(args[0])) {
            if (args.length == 1) throw new WrongUsageException("/blockdict get <ore>");
            String ore = args[1];
            NonNullList<MetaBlock> ores = BlockDictionary.getOres(ore, false);
            if (!ores.isEmpty()) {
                sender.sendMessage(new TextComponentString("All entries tagged with " + ore + " written to file"));
                out.append("All entries tagged with ").append(ore).append("\n");
                for (MetaBlock block : ores) {
                    out.append(block.toString()).append("\n");
                }
            } else {
                sender.sendMessage(new TextComponentString("No ores with tag " + ore));
            }
        } else throw new WrongUsageException("/blockdict <list|get>");

        String str = out.toString();
        if (!str.isEmpty()) {
            try (FileWriter writer = new FileWriter(blockDictCommandOutputTxt)) {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
                sender.sendMessage(new TextComponentString("Failed to save command output..."));
            }
        }
    }
}
