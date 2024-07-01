package org.ordinaicurrency.ordinaicurrency;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

public class TradeReplacer {

    public static void replaceAllTradesWithCustomCurrency() {
        for (Map.Entry<VillagerProfession, Int2ObjectMap<TradeOffers.Factory[]>> entry : TradeOffers.PROFESSION_TO_LEVELED_TRADE.entrySet()) {
            VillagerProfession profession = entry.getKey();
            Int2ObjectMap<TradeOffers.Factory[]> leveledTrades = entry.getValue();

            for (int level : leveledTrades.keySet()) {
                TradeOffers.Factory[] trades = leveledTrades.get(level);
                for (int i = 0; i < trades.length; i++) {
                    TradeOffers.Factory oldTrade = trades[i];
                    trades[i] = new TradeOffers.Factory() {

                        public TradeOffer create(Entity entity, Random random) {
                            TradeOffer oldOffer = oldTrade.create(entity, (net.minecraft.util.math.random.Random) random);
                            if (oldOffer == null) return null;

                            ItemStack buyA = replaceEmeraldWithCustomCurrency(oldOffer.getOriginalFirstBuyItem());
                            ItemStack buyB = replaceEmeraldWithCustomCurrency(oldOffer.getSecondBuyItem());
                            ItemStack sell = oldOffer.getSellItem();

                            return new TradeOffer(buyA, buyB, sell, oldOffer.getUses(), oldOffer.getMaxUses(), oldOffer.getMerchantExperience(), oldOffer.getPriceMultiplier());
                        }

                        @Nullable
                        @Override
                        public TradeOffer create(Entity entity, net.minecraft.util.math.random.Random random) {
                            TradeOffer oldOffer = oldTrade.create(entity, random);
                            if (oldOffer == null) return null;

                            ItemStack buyA = replaceEmeraldWithCustomCurrency(oldOffer.getOriginalFirstBuyItem());
                            ItemStack buyB = replaceEmeraldWithCustomCurrency(oldOffer.getSecondBuyItem());
                            ItemStack sell = oldOffer.getSellItem();

                            return new TradeOffer(buyA, buyB, sell, oldOffer.getUses(), oldOffer.getMaxUses(), oldOffer.getMerchantExperience(), oldOffer.getPriceMultiplier());
                        }
                    };
                }
            }
        }
    }

    private static ItemStack replaceEmeraldWithCustomCurrency(ItemStack itemStack) {
        if (itemStack.getItem() == Items.EMERALD) {
            return new ItemStack(Ordinai_Currency_Mod.ORDINAI_CURRENCY, itemStack.getCount());
        }
        return itemStack;
    }
}
