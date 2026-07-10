package dev.randomdev.warclaims.Classes;

import dev.randomdev.warclaims.Classes.CustomBlocks.Claimer;
import dev.randomdev.warclaims.WarClaims;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Items {
    public static final DeferredRegister.Items ITEMS = WarClaims.ITEMS;

    public static final DeferredItem<BlockItem> CLAIMER = ITEMS.register(
            "claimer",
            ()->new BlockItem(Blocks.CLAIMER.get(), new Item.Properties())
    );

    public static void register(){
    }
}
