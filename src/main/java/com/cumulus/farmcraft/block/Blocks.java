package com.cumulus.farmcraft.block;

import com.cumulus.farmcraft.Farmcraft;
import com.cumulus.farmcraft.block.trellis.VineBlock;
import com.cumulus.farmcraft.block.trellis.TrellisBlock;
import com.cumulus.farmcraft.block.trellis.TrellisBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class Blocks {

    public static final TrellisBlock TRELLIS_BLOCK = new TrellisBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).nonOpaque().hardness(0.3f));
    //public static final VineBlock GRAPE_TRELLIS = new VineBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque().hardness(0.1f));
    public static BlockEntityType<TrellisBlockEntity> TRELLIS_BLOCK_ENTITY;
    public static BlockEntityType<TrellisBlockEntity> VINE_TRELLIS_BLOCK_ENTITY;

    public static void init() {
        Registry.register(Registry.BLOCK, Farmcraft.id("trellis_block"), TRELLIS_BLOCK);
        Registry.register(Registry.ITEM, Farmcraft.id("trellis_block"), new BlockItem(TRELLIS_BLOCK, new Item.Settings()));

        //Registry.register(Registry.BLOCK, Farmcraft.id("grape_trellis"), GRAPE_TRELLIS);
        //Registry.register(Registry.ITEM, Farmcraft.id("grape_trellis"), new BlockItem(GRAPE_TRELLIS, new Item.Settings()));

        VINE_TRELLIS_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Farmcraft.id("trellis_block"), BlockEntityType.Builder.create(
                () -> new TrellisBlockEntity(Farmcraft.id("trellis_block")), TRELLIS_BLOCK).build(null));

    }

}

