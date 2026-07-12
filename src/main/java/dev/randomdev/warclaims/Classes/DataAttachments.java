package dev.randomdev.warclaims.Classes;

import com.mojang.serialization.Codec;
import dev.randomdev.warclaims.Classes.CustomBlocks.Claimer;
import dev.randomdev.warclaims.WarClaims;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DataAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = WarClaims.ATTACHMENT_TYPES;

    public static final Supplier<AttachmentType<String>> CHUNK_OWNER = ATTACHMENT_TYPES.register(
            "chunk_owner", () -> AttachmentType.builder(() -> "").serialize(Codec.STRING).build()
    );
    public static final Supplier<AttachmentType<BlockPos>> OWNER_POS = ATTACHMENT_TYPES.register(
            "owner_pos", () -> AttachmentType.builder(() -> new BlockPos(0,0,0)).serialize(BlockPos.CODEC).build()
    );
    public static final Supplier<AttachmentType<Boolean>> IS_CAPITAL = ATTACHMENT_TYPES.register(
            "is_capital", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build()
    );

    public static void register(){
    }
}
