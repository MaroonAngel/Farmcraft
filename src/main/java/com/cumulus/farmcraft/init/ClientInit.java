package com.cumulus.farmcraft.init;

import com.cumulus.farmcraft.VinePlants;
import com.cumulus.farmcraft.block.Blocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(VinePlants.grapes.getBlock(), RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.TRELLIS_BLOCK, RenderLayer.getCutout());
    }
}
