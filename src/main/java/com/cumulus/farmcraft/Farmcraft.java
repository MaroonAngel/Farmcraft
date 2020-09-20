package com.cumulus.farmcraft;

import net.fabricmc.api.ModInitializer;

public class Farmcraft implements ModInitializer {

	public static final String NAMESPACE = "farmcraft";

	@Override
	public void onInitialize() {
		
	}

	public static Identifier id(String path) {
		return new Identifier(NAMESPACE, path);
	}
}
