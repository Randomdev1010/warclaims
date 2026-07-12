package dev.randomdev.warclaims.Classes;

import dev.randomdev.warclaims.Classes.CustomBlocks.Capital;
import dev.randomdev.warclaims.Classes.CustomBlocks.Claimer;
import dev.randomdev.warclaims.WarClaims;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Blocks {
    public static final DeferredRegister.Blocks BLOCKS = WarClaims.BLOCKS;

    public static final DeferredBlock<Claimer> CLAIMER = BLOCKS.register(
            "claimer",
            registryName->new Claimer(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BLACK)
                            .instrument(NoteBlockInstrument.BIT)
                            .requiresCorrectToolForDrops()
                            .strength(25.0F, 1200.0F)
            ));

    public static final DeferredBlock<Capital> CAPITAL = BLOCKS.register(
            "capital",
            registryName->new Capital(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BLACK)
                            .instrument(NoteBlockInstrument.BIT)
                            .requiresCorrectToolForDrops()
                            .strength(25.0F, 1200.0F)
            ));

    public static void register(){
    }
}
