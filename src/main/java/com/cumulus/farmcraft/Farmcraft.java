package com.cumulus.farmcraft;

import com.cumulus.farmcraft.block.Blocks;
import com.cumulus.farmcraft.item.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Farmcraft implements ModInitializer {

	public static final String NAMESPACE = "farmcraft";
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(VinePlants.grapes.getItem()));

	@Override
	public void onInitialize() {
		Items.register();
		Blocks.init();
		VinePlants.register();
	}

	public static Identifier id(String path) {
		return new Identifier(NAMESPACE, path);
	}
}
