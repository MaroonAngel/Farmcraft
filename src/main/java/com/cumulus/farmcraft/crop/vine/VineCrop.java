package com.cumulus.farmcraft.crop.vine;

import com.cumulus.farmcraft.block.trellis.VineBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VineCrop {

    public Identifier id;

    private Item item;
    private VineBlock block;
    private FoodComponent food;

    private ItemGroup itemGroup;

    public VineCrop(Identifier id) {
        this.id = id;
        this.itemGroup = ItemGroup.FOOD;
        this.food = null;
    }

    public Item getItem() {
        return this.item;
    }

    public VineBlock getBlock() {
        return this.block;
    }

    public VineCrop setFood(FoodComponent food) {
        this.food = food;
        return this;
    }

    public VineCrop setItemGroup(ItemGroup group) {
        this.itemGroup = group;
        return this;
    }

    public VineCrop build() {
        this.block = new VineBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH), new Identifier(this.id.getNamespace(), this.id.getPath() + "_trellis"));
        this.item = new Item(new Item.Settings().group(this.itemGroup).food(this.food));
        return this;
    }

    public void register() {
        Registry.register(Registry.ITEM, this.id, this.item);
        this.block.register(new Identifier(this.id.getNamespace(), this.id.getPath() + "_trellis"));
    }
}
