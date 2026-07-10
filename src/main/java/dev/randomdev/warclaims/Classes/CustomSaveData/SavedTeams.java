package dev.randomdev.warclaims.Classes.CustomSaveData;

import dev.randomdev.warclaims.customTypes.ClaimTeams;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class SavedTeams extends SavedData {
    ClaimTeams teams = new ClaimTeams();

    // Create new instance of saved data
    public static SavedTeams create() {
        return new SavedTeams();
    }

    // Load existing instance of saved data
    public static SavedTeams load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        SavedTeams data = SavedTeams.create();
        data.setTeams(ClaimTeams.fromTag(tag));

        // Load saved data
        return data;
    }

    private void setTeams(ClaimTeams teams) {
        this.teams.copy(teams);
    }

    public SafeEditor editInfo(){
        return new SafeEditor(this,this.teams);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        this.teams.putInTag(tag);
        // Write data to tag
        return tag;
    }

    public static class SafeEditor extends ClaimTeams{
        private SavedTeams teams;

        protected SafeEditor(SavedTeams teams,ClaimTeams claimTeams){
            this.teams = teams;
            this.copy(claimTeams);
        }
        public void save(){
            this.teams.setTeams(this);
            this.teams.setDirty();
        }
    }
}
