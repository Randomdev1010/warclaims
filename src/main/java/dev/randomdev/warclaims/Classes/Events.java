package dev.randomdev.warclaims.Classes;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.randomdev.warclaims.Classes.CustomSaveData.SavedTeams;
import dev.randomdev.warclaims.Classes.Networking.CustomPacketPayloads.SelectTeamPacket;
import dev.randomdev.warclaims.Classes.Networking.CustomPayloadHandlers.SelectTeamHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handlers.ClientPayloadHandler;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.UUID;

public class Events {
    public static SavedTeams TEAMSDATA;

    @SubscribeEvent
    public void registerCommand(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("teams")
            .requires(arg-> arg.hasPermission(2))
            .then(
               Commands.literal("add").then(Commands.argument("Team", StringArgumentType.string()).then(Commands.argument("Player", EntityArgument.player()).executes(Command->{
                   String team = StringArgumentType.getString(Command,"Team");
                   Player player = EntityArgument.getPlayer(Command,"Player");


                   return 0;
               })))
            )
        );
    }
    @SubscribeEvent
    public void levelStartup(ServerStartingEvent event){
        TEAMSDATA = event.getServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(SavedTeams::create,SavedTeams::load),"ClaimTeams");
    }
    @SubscribeEvent
    public void changeGamemode(PlayerEvent.PlayerChangeGameModeEvent event){
        System.out.println("changedgamemode");
    }
    @SubscribeEvent
    public void playerJoined(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        UUID id = player.getUUID();

        SavedTeams.SafeEditor editor = TEAMSDATA.editInfo();
        if(!editor.hasInAnyTeam(id)){
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SelectTeamPacket(""));
        }
    }
}
