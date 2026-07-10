package dev.randomdev.warclaims.Classes;

import dev.randomdev.warclaims.Classes.Networking.CustomPacketPayloads.SelectTeamPacket;
import dev.randomdev.warclaims.Classes.Networking.CustomPayloadHandlers.SelectTeamHandler;
import net.neoforged.bus.api.SubscribeEvent;
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
}
