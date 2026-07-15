package dev.randomdev.warclaims.Classes.Networking.CustomPayloadHandlers;

import dev.randomdev.warclaims.Classes.Networking.CustomPacketPayloads.SelectTeamPacket;
import dev.randomdev.warclaims.Classes.Screens.TeamSelectionScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SelectTeamHandler {
    public static void handleDataOnClient(final SelectTeamPacket data, final IPayloadContext context) {
        // Do something with the data, on the main thread
        if (FMLEnvironment.dist == Dist.CLIENT){
            Minecraft.getInstance().setScreen(new TeamSelectionScreen("Choose Your Team"));
        }
    }
}
