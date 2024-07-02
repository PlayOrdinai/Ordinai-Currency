package org.ordinaicurrency;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Ordinai_Currency_Mod implements ModInitializer {
    public static final Item ORDINAI_CURRENCY = new Ordinai_Currency();
    public static final Item WALLET_ITEM = new WalletItem(new Item.Settings().maxCount(1).group(ItemGroup.MISC));

    public static void registerItems(){
        Registry.register(Registry.ITEM, new Identifier("ordinai_currency", "coin"), ORDINAI_CURRENCY);
        Registry.register(Registry.ITEM, new Identifier("ordinai_currency", "wallet"), WALLET_ITEM);
    }

    @Override
    public void onInitialize() {
        registerItems();


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            WalletCommand.register(dispatcher);
        });


        // Replace all trades with the new currency
        TradeReplacer.replaceAllTradesWithCustomCurrency();
    }
}
