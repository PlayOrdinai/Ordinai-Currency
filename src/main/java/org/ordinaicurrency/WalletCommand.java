package org.ordinaicurrency;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class WalletCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("addcoins")
                .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            int amount = IntegerArgumentType.getInteger(context, "amount");

                            ItemStack stack = source.getPlayer().getMainHandStack();
                            if (stack.getItem() instanceof WalletItem) {
                                WalletItem wallet = (WalletItem) stack.getItem();
                                wallet.addCoins(stack, amount);
                                source.sendFeedback(Text.literal("Added " + amount + " Ordinai Coins to your wallet."), true);
                                return 1;
                            } else {
                                source.sendError(Text.literal("You must hold a wallet in your main hand to use this command."));
                                return 0;
                            }
                        })));

        dispatcher.register(CommandManager.literal("removecoins")
                .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            int amount = IntegerArgumentType.getInteger(context, "amount");

                            ItemStack stack = source.getPlayer().getMainHandStack();
                            if (stack.getItem() instanceof WalletItem) {
                                WalletItem wallet = (WalletItem) stack.getItem();
                                wallet.removeCoins(stack, amount);
                                source.sendFeedback(Text.literal("Removed " + amount + " Ordinai Coins from your wallet."), true);
                                return 1;
                            } else {
                                source.sendError(Text.literal("You must hold a wallet in your main hand to use this command."));
                                return 0;
                            }
                        })));

    }
}
