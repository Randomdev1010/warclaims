package dev.randomdev.warclaims.Classes.CapabilityHandlers;

import dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities.ClaimerEntity;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class BlockEntityEnergyHandler implements IEnergyStorage {
    ClaimerEntity entity;
    int maxEnergy;
    public BlockEntityEnergyHandler(ClaimerEntity entity,int maxEnergy){
        this.entity = entity;
        this.maxEnergy = maxEnergy;
    }

    @Override
    public int receiveEnergy(int i, boolean b) {
        int energy = entity.energy + i;
        entity.energy = Math.min(this.getMaxEnergyStored(),energy);
        return Math.max(energy-this.getMaxEnergyStored(),0);
    }

    @Override
    public int extractEnergy(int i, boolean b) {
        int energy = entity.energy - i;
        entity.energy = Math.max(0,energy);
        return Math.min(energy-this.getMaxEnergyStored(),0);
    }

    @Override
    public int getEnergyStored() {
        return entity.energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxEnergy;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
