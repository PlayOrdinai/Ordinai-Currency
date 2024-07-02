package org.ordinaicurrency;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WalletItem extends Item {

    public WalletItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        int coinCount = getCoinCount(stack);
        return Text.literal("Wallet (" + coinCount + " Ordinai Coins)").formatted(Formatting.GOLD);
    }

    public int getCoinCount(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        return tag.getInt("CoinCount");
    }

    public void addCoins(ItemStack stack, int amount) {
        NbtCompound tag = stack.getOrCreateNbt();
        int current = tag.getInt("CoinCount");
        tag.putInt("CoinCount", current + amount);
    }

    public void removeCoins(ItemStack stack, int amount) {
        NbtCompound tag = stack.getOrCreateNbt();
        int current = tag.getInt("CoinCount");
        tag.putInt("CoinCount", Math.max(0, current - amount));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack walletStack = player.getStackInHand(hand);

            if (player.isSneaking()) {
                int currentCoins = getCoinCount(walletStack);
                if (currentCoins > 0) {
                    int coinsToTake = Math.min(currentCoins, 64); // max stack size is 64
                    ItemStack coinsToGive = new ItemStack(Registry.ITEM.get(new Identifier("ordinai_currency", "coin")), coinsToTake);
                    player.getInventory().insertStack(coinsToGive);
                    removeCoins(walletStack, coinsToTake);
                    player.sendMessage(Text.literal("Removed " + coinsToTake + " Ordinai Coins from your wallet."), true);
                    return new TypedActionResult<>(ActionResult.SUCCESS, walletStack);
                } else {
                    player.sendMessage(Text.literal("No Ordinai Coins in your wallet."), true);
                }
            } else {
                ItemStack coinStack = findCoinStack(player);

                if (coinStack != ItemStack.EMPTY) {
                    int amount = coinStack.getCount();
                    addCoins(walletStack, amount);
                    player.getInventory().removeOne(coinStack);
                    player.sendMessage(Text.literal("Added " + amount + " Ordinai Coins to your wallet."), true);
                    return new TypedActionResult<>(ActionResult.SUCCESS, walletStack);
                } else {
                    player.sendMessage(Text.literal("No Ordinai Coins in your inventory."), true);
                }
            }
        }
        return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
    }

    private ItemStack findCoinStack(PlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == Registry.ITEM.get(new Identifier("ordinai_currency", "coin"))) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
