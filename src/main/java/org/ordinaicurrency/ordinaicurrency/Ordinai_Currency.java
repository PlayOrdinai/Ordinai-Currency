package org.ordinaicurrency.ordinaicurrency;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;


public class Ordinai_Currency extends Item {
    public Ordinai_Currency() {
        super(new Item.Settings().group(ItemGroup.MISC));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.ordinai_currency.coin.tooltip"));
    }
}
