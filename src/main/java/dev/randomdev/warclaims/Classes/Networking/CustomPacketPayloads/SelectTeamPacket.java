package dev.randomdev.warclaims.Classes.Networking.CustomPacketPayloads;

import dev.randomdev.warclaims.WarClaims;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SelectTeamPacket(String teamName) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SelectTeamPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WarClaims.MODID, "select_team_packet"));

    public static final StreamCodec<ByteBuf, SelectTeamPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SelectTeamPacket::teamName,
            SelectTeamPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
