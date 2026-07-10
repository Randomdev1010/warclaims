package dev.randomdev.warclaims.Classes;

import dev.randomdev.warclaims.Classes.CustomBlocks.Claimer;
import dev.randomdev.warclaims.WarClaims;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Blocks {
    public static final DeferredRegister.Blocks BLOCKS = WarClaims.BLOCKS;

    public static final DeferredBlock<Claimer> CLAIMER = BLOCKS.register(
            "claimer",
            registryName->new Claimer(
                    BlockBehaviour.Properties.of()
            ));

    public static void register(){
    }
}
