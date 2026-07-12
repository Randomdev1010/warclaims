package dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities;

import dev.randomdev.warclaims.Classes.BlockEntities;
import dev.randomdev.warclaims.Classes.CustomBlocks.Capital;
import dev.randomdev.warclaims.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CapitalEntity extends ClaimerEntity{
    public CapitalEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntities.CAPITAL_ENTITY.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ClaimerEntity blockEntity) {
        if (blockEntity.energy > 0){
            blockEntity.energy -= Config.ENERGY_DRAIN.get();
            if(!blockEntity.isOn) {
                Capital.turnOn(state,level,pos);
                blockEntity.isOn = true;
            }
        }else {
            if(blockEntity.isOn) {
                Capital.turnOff(state, level, pos);
                blockEntity.isOn = false;
            }
        }
    }
}
