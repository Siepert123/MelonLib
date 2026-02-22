package com.melonstudios.melonlib.command;

import com.melonstudios.melonlib.misc.FileUtil;
import com.melonstudios.melonlib.recipe.RecipeRegistry;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

//Added in 1.10.0
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandRecipeTypes extends CommandBase {
    @Override
    public String getName() {
        return "recipe_types";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.recipe_types.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException("/recipe_types <list|get|all>");

        File commandOutputTxt = FileUtil.downloadsFile("recipeTypesCommandOutput.txt");

        StringBuilder out = new StringBuilder();

        if ("list".equals(args[0])) {
            Set<String> names = RecipeRegistry.allRegisteredRecipeTypes();
            sender.sendMessage(new TextComponentString("All recipe types written to file"));
            out.append("All recipe types").append("\n");
            for (String name : names) {
                out.append(name).append(" (").append(RecipeRegistry.getRecipeType(name).getAllRecipeIDs().size()).append(")").append("\n");
            }
        } else if ("get".equals(args[0])) {
            if (args.length == 1) throw new WrongUsageException("/recipe_types get <id>");
            String id = args[1];
            Collection<String> names = RecipeRegistry.getRecipeType(id).getAllRecipeIDs();
            if (!names.isEmpty()) {
                sender.sendMessage(new TextComponentString("All recipes for recipe type " + id + " written to file"));
                out.append("All recipes in recipe type ").append(id).append("\n");
                for (String recipe : names) {
                    out.append("  ").append(recipe).append("\n");
                }
            } else {
                sender.sendMessage(new TextComponentString("No recipes in recipe type " + id));
            }
        } else if ("all".equals(args[0])) {
            Set<String> types = RecipeRegistry.allRegisteredRecipeTypes();
            sender.sendMessage(new TextComponentString("All recipe types with contents written to file"));
            for (String type : types) {
                out.append(type).append("\n");
                Collection<String> recipes = RecipeRegistry.getRecipeType(type).getAllRecipeIDs();
                for (String recipe : recipes) {
                    out.append("  ").append(recipe).append("\n");
                }
            }
        }

        String str = out.toString();
        if (!str.isEmpty()) {
            try (FileWriter writer = new FileWriter(commandOutputTxt)) {
                writer.write(str);
                writer.flush();
            } catch (IOException e) {
                sender.sendMessage(new TextComponentString("Failed to save command output: " + e));
            }
        }
    }
}
