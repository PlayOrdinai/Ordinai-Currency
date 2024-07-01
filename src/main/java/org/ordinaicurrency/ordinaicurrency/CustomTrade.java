package org.ordinaicurrency.ordinaicurrency;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CustomTrade implements TradeOffers.Factory {
    private final ItemStack sell;
    private final int price;
    private final int maxUses;
    private final int experience;

    public CustomTrade(ItemStack sell, int price, int maxUses, int experience) {
        this.sell = sell;
        this.price = price;
        this.maxUses = maxUses;
        this.experience = experience;
    }

    // Method without @Override to avoid signature issues
    public TradeOffer create(VillagerEntity entity, Random random) {
        ItemStack buy = new ItemStack(Ordinai_Currency_Mod.ORDINAI_CURRENCY, price);
        return new TradeOffer(buy, sell, maxUses, experience, 2F);
    }

    @Nullable
    @Override
    public TradeOffer create(Entity entity, net.minecraft.util.math.random.Random random) {
        if (entity instanceof VillagerEntity villagerEntity) {
            return create(villagerEntity, new Random());
        }
        return null;
    }
}
