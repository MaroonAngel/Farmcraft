package com.cumulus.farmcraft;

import com.cumulus.farmcraft.block.Blocks;
import com.cumulus.farmcraft.item.Items;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Farmcraft implements ModInitializer {

	public static final String NAMESPACE = "farmcraft";

	@Override
	public void onInitialize() {
		Items.register();
		Blocks.init();
	}

	public static Identifier id(String path) {
		return new Identifier(NAMESPACE, path);
	}
}
