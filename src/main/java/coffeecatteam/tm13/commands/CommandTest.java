package coffeecatteam.tm13.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 * @author CoffeeCatRailway
 * Created: 15/01/2019
 */
public class CommandTest {

    private static final int MIN_DIAMONDS = 1;
    private static final int MAX_DIAMONDS = 2304; // Max amount of diamonds you can hold in a survival inventory (64 * (9 * 4))

    private static final String COUNT_ARG = "count";
    private static final String BLOCK_ARG = "block";

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("tm13_diamonds").executes(context ->
                givePlayerDiamonds(context, MAX_DIAMONDS, false)
        ).then(Commands.argument(COUNT_ARG, IntegerArgumentType.integer(MIN_DIAMONDS, MAX_DIAMONDS))
                .then(Commands.argument(BLOCK_ARG, BoolArgumentType.bool()).executes(context ->
                        givePlayerDiamonds(context, IntegerArgumentType.getInteger(context, COUNT_ARG), BoolArgumentType.getBool(context, BLOCK_ARG))
                )).executes(context ->
                        givePlayerDiamonds(context, IntegerArgumentType.getInteger(context, COUNT_ARG), false)
                )));
    }

    private static int givePlayerDiamonds(CommandContext<CommandSource> context, int count, boolean block) throws CommandSyntaxException {
        String msg = TextFormatting.YELLOW + context.getSource().getDisplayName().getString() + TextFormatting.RESET + " just got " + count + " diamond" + (block ? " blocks!" : "s!");
        context.getSource().getServer().getPlayerList().sendMessage(new TextComponentString(msg));
        context.getSource().asPlayer().inventory.addItemStackToInventory(new ItemStack(block ? Blocks.DIAMOND_BLOCK : Items.DIAMOND, count));
        return 1;
    }
}
