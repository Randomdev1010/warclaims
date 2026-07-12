package dev.randomdev.warclaims.Classes;

import dev.randomdev.warclaims.Classes.CapabilityHandlers.BlockEntityEnergyHandler;
import dev.randomdev.warclaims.Classes.Networking.CustomPacketPayloads.SelectTeamPacket;
import dev.randomdev.warclaims.Classes.Networking.CustomPayloadHandlers.SelectTeamHandler;
import dev.randomdev.warclaims.Config;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class IModBusEvents {
    @SubscribeEvent
    public void registerPayloads(RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                SelectTeamPacket.TYPE,
                SelectTeamPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        SelectTeamHandler::handleDataOnClient,
                        SelectTeamHandler::handleDataOnServer
                )
        );
    }
    @SubscribeEvent  // on the mod event bus
    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                BlockEntities.CLAIMER_ENTITY.get(),
                (be, side) -> new BlockEntityEnergyHandler(be,Config.CLAIMER_MAX.get())
        );
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                BlockEntities.CAPITAL_ENTITY.get(),
                (be, side) -> new BlockEntityEnergyHandler(be, Config.CAPITAL_MAX.get())
        );
    }
    @SubscribeEvent  // on the mod event bus
    public void registerTests(RegisterGameTestsEvent event) {
        event.register(GameTests.class);
    }
}
