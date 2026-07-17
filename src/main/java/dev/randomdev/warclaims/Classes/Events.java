package dev.randomdev.warclaims.Classes;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities.ClaimerEntity;
import dev.randomdev.warclaims.Classes.CustomSaveData.SavedTeams;
import dev.randomdev.warclaims.Classes.Networking.CustomPacketPayloads.SelectTeamPacket;
import dev.randomdev.warclaims.Config;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.UUID;

public class Events {
    public static SavedTeams TEAMSDATA;

    @SubscribeEvent
    public void registerCommand(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("warClaims")
            .requires(arg-> arg.hasPermission(2))
            .then(
               Commands.literal("add").then(Commands.argument("Team", StringArgumentType.string()).then(Commands.argument("Player", EntityArgument.player()).executes(Command->{
                   String team = StringArgumentType.getString(Command,"Team");
                   Player player = EntityArgument.getPlayer(Command,"Player");

                   if(TEAMSDATA==null){return 0;}
                   SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();

                   editor.addToTeam(team,player.getUUID());
                   editor.save();

                   return 0;
               })))
            )
            .then(
                Commands.literal("remove").then(Commands.argument("Team", StringArgumentType.string()).then(Commands.argument("Player", EntityArgument.player()).executes(Command->{
                    String team = StringArgumentType.getString(Command,"Team");
                    Player player = EntityArgument.getPlayer(Command,"Player");

                    if(TEAMSDATA==null){return 0;}
                    SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();

                    ArrayList<UUID> list = editor.getTeam(team);

                    list.remove(player.getUUID());

                    editor.setTeam(team,list);
                    editor.save();

                    return 0;
                })))
            )
                .then(
                    Commands.literal("replacePlayer").then(Commands.argument("Player", EntityArgument.player()).then(Commands.argument("Team", StringArgumentType.string()).executes(Command->{
                        Player player = EntityArgument.getPlayer(Command,"Player");

                        if(TEAMSDATA==null){return 0;}
                        SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();
                        String team = editor.getTeamName(player.getUUID());
                        if (team == null){return 0;}

                        while (team!=null) {
                            ArrayList<UUID> list = editor.getTeam(team);

                            list.remove(player.getUUID());

                            editor.setTeam(team, list);
                            team = editor.getTeamName(player.getUUID());
                        }
                        editor.addToTeam(team,player.getUUID());
                        editor.save();

                        return 0;
                    })))
                )
            .then(
                Commands.literal("clearPlayer").then(Commands.argument("Player", EntityArgument.player()).executes(Command->{
                    Player player = EntityArgument.getPlayer(Command,"Player");

                    if(TEAMSDATA==null){return 0;}
                    SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();
                    String team = editor.getTeamName(player.getUUID());
                    if (team == null){return 0;}

                    while (team!=null) {
                        ArrayList<UUID> list = editor.getTeam(team);

                        list.remove(player.getUUID());

                        editor.setTeam(team, list);
                        team = editor.getTeamName(player.getUUID());
                    }
                    editor.save();

                    return 0;
                }))
            )
            .then(
                Commands.literal("clearTeam").then(Commands.argument("Team", StringArgumentType.string()).executes(Command->{
                    String team = StringArgumentType.getString(Command,"Team");

                    if(TEAMSDATA==null){return 0;}
                    SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();

                    editor.setTeam(team,new ArrayList<>());
                    editor.save();

                    return 0;
                }))
            )
            .then(
                Commands.literal("clear").executes(Command->{
                    if(TEAMSDATA==null){return 0;}
                    SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();

                    editor.getTeams().forEach((String name,ArrayList<UUID> list)->{
                        editor.setTeam(name,new ArrayList<>());
                    });
                    editor.save();

                    return 0;
                })
            )
            .then(
                Commands.literal("powerBelow").then(Commands.argument("PowerAmount", IntegerArgumentType.integer()).executes(Command->{
                    BlockPos pos = Command.getSource().getEntity().blockPosition().below();
                    int power = IntegerArgumentType.getInteger(Command,"PowerAmount");
                    Level level = Command.getSource().getLevel();
                    BlockEntity entity = level.getBlockEntity(pos);

                    IEnergyStorage capability = level.getCapability(Capabilities.EnergyStorage.BLOCK,pos, Direction.DOWN);
                    System.out.println(capability);
                    System.out.println(power);
                    System.out.println(pos);
                    if(capability==null&&entity!=null&&entity.getClass()==ClaimerEntity.class){
                        System.out.println("get cap cache");
                        capability = ((ClaimerEntity) entity).capCache.getCapability();
                    }
                    System.out.println(capability);

                    if (capability!=null) {
                        System.out.println(capability.getEnergyStored());
                        capability.receiveEnergy(power, true);
                    }

                    return 0;
                }))
            )
        );
    }
    @SubscribeEvent
    public void levelStartup(ServerStartingEvent event){
        TEAMSDATA = event.getServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(SavedTeams::create,SavedTeams::load),"ClaimTeams");
    }
    @SubscribeEvent
    public void playerJoined(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        UUID id = player.getUUID();

        if(TEAMSDATA==null){return;}
        SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();
        if(!editor.hasInAnyTeam(id)){
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SelectTeamPacket(""));
        }
    }
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event){
        BlockPos pos = event.getPos();
        Level level = (Level) event.getLevel();
        if(TEAMSDATA==null){return;}
        SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();
        String teamName = editor.getTeamName(event.getPlayer().getUUID());
        LevelChunk chunk = level.getChunkAt(pos);

        boolean isNotChunkOwner = !chunk.getData(DataAttachments.CHUNK_OWNER).equals(teamName) && !chunk.getData(DataAttachments.CHUNK_OWNER).isEmpty();
        if(isNotChunkOwner && level.getBlockState(pos).getBlock()==Blocks.CAPITAL.get()){
            event.setCanceled(true);
        }
        if(isNotChunkOwner && !Config.CAN_BREAK_ON_ENEMY.get()){
            event.setCanceled(true);
        }

        if (chunk.getData(DataAttachments.OWNER_POS).equals(pos)){
            chunk.setData(DataAttachments.CHUNK_OWNER,"");
            chunk.setData(DataAttachments.OWNER_POS,new BlockPos(0,0,0));
        }
    }
    @SubscribeEvent
    public void onMobKill(LivingDropsEvent event){
        if(TEAMSDATA==null){return;}
        Entity entity = event.getEntity();
        Level level = entity.level();
        LevelChunk chunk = level.getChunkAt(entity.getOnPos());
        SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();
        if (event.getSource().getEntity()==null){return;}
        String teamName = editor.getTeamName(event.getSource().getEntity().getUUID());

        if(chunk.getData(DataAttachments.CHUNK_OWNER).equals(teamName)){
            event.getDrops().forEach(itemEntity -> {
                Vec3 itemPos = itemEntity.position();
                ItemStack stack = itemEntity.getItem().copy();
                if(!chunk.getData(DataAttachments.IS_CAPITAL)) {
                    stack.setCount((int) (((double) stack.getCount() /2)*1.5));
                }
                level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, stack));
            });
        }else if(!chunk.getData(DataAttachments.CHUNK_OWNER).isEmpty()){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public void onBlockDrop(BlockDropsEvent event){
        if(TEAMSDATA==null){return;}
        LevelChunk chunk = event.getLevel().getChunkAt(event.getPos());
        Level level = event.getLevel();
        Block block = event.getState().getBlock();
        RecipeManager manager = level.getRecipeManager();
        SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();
        if (event.getBreaker()==null){return; }
        if (event.getDrops().isEmpty()){return; }
        String teamName = editor.getTeamName(event.getBreaker().getUUID());

        System.out.println(teamName);
        System.out.println(event.getBreaker().getUUID());
        System.out.println(chunk.getData(DataAttachments.CHUNK_OWNER));

        if(chunk.getData(DataAttachments.CHUNK_OWNER).equals(teamName)){
            Vec3 itemPos = event.getDrops().getLast().position();
            System.out.println(event.getDrops().getLast().getItem().getItem());
            System.out.println(block.asItem());
            ItemStack dropItem = event.getDrops().getLast().getItem();
            if(dropItem.getItem()!=block.asItem()&&dropItem.getItem().getClass()!=BlockItem.class) {
                int count = dropItem.getCount();
                if(!manager.byKey(BuiltInRegistries.ITEM.getKey(block.asItem())).isEmpty()){
                    count = count/2;
                }
                if(!chunk.getData(DataAttachments.IS_CAPITAL)) {
                    count = (int) (((double) count / 2) * 1.5);
                }
                ItemStack stack = dropItem.copy();
                System.out.println(count);
                stack.setCount(count);
                level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, stack));
            }
        }else if(!chunk.getData(DataAttachments.CHUNK_OWNER).isEmpty()){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void mobDamaged(LivingIncomingDamageEvent event){
        if(TEAMSDATA==null){return;}
        Entity entity = event.getEntity();
        Level level = entity.level();
        LevelChunk chunk = level.getChunkAt(entity.getOnPos());
        SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();

        if(event.getSource().getEntity()==null){return;}
        String teamName = editor.getTeamName(event.getSource().getEntity().getUUID());
        if(!chunk.getData(DataAttachments.CHUNK_OWNER).equals(teamName) && !chunk.getData(DataAttachments.CHUNK_OWNER).isEmpty() && !Config.CAN_HURT_ON_ENEMY.get()){
            event.setCanceled(true);
        }
    }
}
