package dev.randomdev.warclaims.Classes;

import dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities.CapitalEntity;
import dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities.ClaimerEntity;
import dev.randomdev.warclaims.WarClaims;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = WarClaims.BLOCK_ENTITY_TYPES;

    public static final Supplier<BlockEntityType<ClaimerEntity>> CLAIMER_ENTITY = BLOCK_ENTITY_TYPES.register(
            "claimer_entity",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            // The supplier to use for constructing the block entity instances.
                            ClaimerEntity::new,
                            // A vararg of blocks that can have this block entity.
                            // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                            Blocks.CLAIMER.get()
                    )
                    // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
                    .build(null)
    );
    public static final Supplier<BlockEntityType<CapitalEntity>> CAPITAL_ENTITY = BLOCK_ENTITY_TYPES.register(
            "capital_entity",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            // The supplier to use for constructing the block entity instances.
                            CapitalEntity::new,
                            // A vararg of blocks that can have this block entity.
                            // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                            Blocks.CAPITAL.get()
                    )
                    // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
                    .build(null)
    );

    public static void register(){
    }
}
