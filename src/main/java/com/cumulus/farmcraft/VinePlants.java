package com.cumulus.farmcraft;

import com.cumulus.farmcraft.crop.vine.VineCrop;
import net.minecraft.item.FoodComponent;

public class VinePlants {

    public static final VineCrop grapes = new VineCrop(Farmcraft.id("grapes"))
            .setFood((new FoodComponent.Builder()).hunger(2).saturationModifier(0.3f).build())
            .setItemGroup(Farmcraft.GROUP)
            .build();



    public static void register() {
        grapes.register();
    }


}
