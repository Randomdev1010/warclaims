package dev.randomdev.warclaims.Classes.Networking.CustomPayloadHandlers;

import dev.randomdev.warclaims.Classes.CustomSaveData.SavedTeams;
import dev.randomdev.warclaims.Classes.Items;
import dev.randomdev.warclaims.Classes.Networking.CustomPacketPayloads.SelectTeamPacket;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static dev.randomdev.warclaims.Classes.Events.TEAMSDATA;

public class SelectTeamHandlerServer {
    public static void handleDataOnServer(final SelectTeamPacket data, final IPayloadContext context) {
        // Do something with the data, on the main thread
        if(TEAMSDATA==null){return;}
        SavedTeams.SafeEditor edit = TEAMSDATA.editInfo();
        if (edit.getTeam(data.teamName()).isEmpty()) {
            context.player().addItem(new ItemStack(Items.CAPITAL::get));
        }
        edit.addToTeam(data.teamName(), context.player().getUUID());
        edit.save();
    }
}
