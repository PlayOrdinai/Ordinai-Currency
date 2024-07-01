package org.ordinaicurrency.ordinaicurrency;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Ordinai_Currency_Mod implements ModInitializer {

    public static final Item ORDINAI_CURRENCY = new Ordinai_Currency();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("ordinai_currency", "coin"), ORDINAI_CURRENCY);

        // Replace all trades with the new currency
        TradeReplacer.replaceAllTradesWithCustomCurrency();
    }
}
