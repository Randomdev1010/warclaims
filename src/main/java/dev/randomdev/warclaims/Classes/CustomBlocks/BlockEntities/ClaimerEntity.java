package dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities;

import dev.randomdev.warclaims.Classes.BlockEntities;
import dev.randomdev.warclaims.Classes.CustomBlocks.Claimer;
import dev.randomdev.warclaims.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class ClaimerEntity extends BlockEntity {
    public int energy;
    protected BlockPos pos;
    protected boolean isOn = false;
    public BlockCapabilityCache<IEnergyStorage, Direction> capCache;

    public ClaimerEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntities.CLAIMER_ENTITY.get(), pos, blockState);
        this.pos = pos;
    }
    public ClaimerEntity(BlockEntityType<?> type,BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.pos = pos;
    }

    @Override
    public void onLoad() {
        Level level = getLevel();
        if (level != null && !level.isClientSide) {
            this.capCache = BlockCapabilityCache.create(
                    Capabilities.EnergyStorage.BLOCK, // capability to cache
                    (ServerLevel) level, // level
                    pos,
                    null,
                    () -> !this.isRemoved(), // validity check (because the cache might outlive the object it belongs to)
                    this::onCapInvalidate // invalidation listener
            );
        }
        super.onLoad();
    }

    private void onCapInvalidate() {
        assert level != null;
        level.invalidateCapabilities(pos);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        // Will default to 0 if absent. See the NBT article for more information.
        this.energy = tag.getInt("energy");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", this.energy);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ClaimerEntity blockEntity) {
        if (blockEntity.energy > 0){
            blockEntity.energy -= Config.ENERGY_DRAIN.get();
            if(!blockEntity.isOn) {
                Claimer.turnOn(state,level,pos);
                blockEntity.isOn = true;
            }
        }else {
            if(blockEntity.isOn) {
                Claimer.turnOff(state, level, pos);
                blockEntity.isOn = false;
            }
        }
    }
}
