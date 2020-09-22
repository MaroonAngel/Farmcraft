package com.cumulus.farmcraft.block.trellis;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VineBlockEntity extends BlockEntity {

    public VineBlockEntity(Identifier id) {
        super(Registry.BLOCK_ENTITY_TYPE.get(id));
    }
}
