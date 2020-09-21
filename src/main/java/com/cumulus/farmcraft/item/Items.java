package com.cumulus.farmcraft.item;

import com.cumulus.farmcraft.Farmcraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class Items {

    public static final Item GRAPES = new Item((new Item.Settings().group(ItemGroup.FOOD)));

    public static void register() {
        Registry.register(Registry.ITEM, Farmcraft.id("grapes"), GRAPES);
    }

}
