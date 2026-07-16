package dev.randomdev.warclaims.Classes.CustomBlocks;

import dev.randomdev.warclaims.Classes.BlockEntities;
import dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities.CapitalEntity;
import dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities.ClaimerEntity;
import dev.randomdev.warclaims.Classes.DataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Capital extends Claimer{
    public Capital(Properties properties) {
        super(properties);
    }

    public static void turnOn(BlockState state, Level level, BlockPos pos){
        level.setBlock(pos,state.setValue(POWERED,true),2);
        level.playSound((Player) null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS);
        LevelChunk chunk = level.getChunkAt(pos);
        if(chunk.getData(DataAttachments.CHUNK_OWNER).isEmpty()){
            chunk.setData(DataAttachments.CHUNK_OWNER,state.getValue(COLORS).getSerializedName());
            chunk.setData(DataAttachments.OWNER_POS,pos);
            chunk.setData(DataAttachments.IS_CAPITAL,true);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        this.blockEntity = new CapitalEntity(blockPos,blockState);
        return this.blockEntity;
    }
    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return createTickerHelper(type, BlockEntities.CAPITAL_ENTITY.get(), CapitalEntity::tick);
    }
}
