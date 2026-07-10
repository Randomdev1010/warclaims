package dev.randomdev.warclaims.customTypes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.Items;
import net.minecraft.world.scores.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ClaimTeams {
    private HashMap<String, ArrayList<UUID>> teams;

    public ClaimTeams(){
        this.teams = new HashMap<>();
        teams.put("white",new ArrayList<>());
        teams.put("lightgray",new ArrayList<>());
        teams.put("gray",new ArrayList<>());
        teams.put("black",new ArrayList<>());
        teams.put("blue",new ArrayList<>());
        teams.put("lightblue",new ArrayList<>());
        teams.put("cyan",new ArrayList<>());
        teams.put("green",new ArrayList<>());
        teams.put("lime",new ArrayList<>());
        teams.put("magenta",new ArrayList<>());
        teams.put("pink",new ArrayList<>());
        teams.put("purple",new ArrayList<>());
        teams.put("red",new ArrayList<>());
        teams.put("yellow",new ArrayList<>());
        teams.put("orange",new ArrayList<>());
        teams.put("brown",new ArrayList<>());
    }

    public ArrayList<UUID> getTeam(String name){
        return this.teams.get(name);
    }

    public HashMap<String, ArrayList<UUID>> getTeams(){
        return this.teams;
    }

    public void addToTeam(String name, UUID obj){
        ArrayList<UUID> team = this.teams.get(name);
        if(team!=null){
            team.add(obj);
        }
    }

    public boolean hasInTeam(String name, UUID obj){
        ArrayList<UUID> team = this.teams.get(name);
        return team != null && team.contains(obj);
    }

    public boolean hasInAnyTeam(UUID obj){
        AtomicBoolean returnVal = new AtomicBoolean(false);
        this.teams.forEach((String name,ArrayList<UUID> list)->{
            returnVal.set(list.contains(obj) || returnVal.get());
        });
        return returnVal.get();
    }

    public String getTeamName(UUID id){
        AtomicReference<String> returnVal = new AtomicReference<>(null);
        this.teams.forEach((String name,ArrayList<UUID> list)->{
            returnVal.set((list.contains(id))?name:returnVal.get());
        });
        return returnVal.get();
    }

    public void putInTag(CompoundTag tag){
        this.teams.forEach((String name,ArrayList<UUID> list)->{
            CompoundTag listTag = new CompoundTag();

            list.forEach(obj->{
                listTag.putUUID(obj.toString(),obj);
            });
            tag.put(name,listTag);
        });
    }

    public void copy(ClaimTeams teams){
        this.teams = teams.getTeams();
    }

    public static ClaimTeams fromTag(CompoundTag tag){
        ClaimTeams obj = new ClaimTeams();
        if (!tag.isEmpty()){
            tag.getAllKeys().forEach(key->{
                CompoundTag listTag = tag.getCompound(key);

                listTag.getAllKeys().forEach(uuidKey->{
                    obj.addToTeam(key,listTag.getUUID(uuidKey));
                });
            });
        }
        return obj;
    }
}
