package dev.randomdev.warclaims.Classes.Screens;

import dev.randomdev.warclaims.Classes.Networking.CustomPacketPayloads.SelectTeamPacket;
import dev.randomdev.warclaims.WarClaims;
import dev.randomdev.warclaims.libs.ColorsEnum;
import dev.randomdev.warclaims.libs.ScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jline.reader.Widget;

import java.awt.*;

import static net.minecraft.client.gui.screens.worldselection.CreateWorldScreen.TAB_HEADER_BACKGROUND;

public class TeamSelectionScreen extends Screen {
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private ScreenUtils<TeamSelectionScreen> screenUtils;

    public TeamSelectionScreen(String title) {
        super(Component.literal(title));
        this.screenUtils = null;
    }

    @Override
    protected void init() {
        this.screenUtils = new ScreenUtils<>(this);

        this.addRenderableWidget(screenUtils.makeText(screenUtils.xPercent(50),5,"Select Your Team",this.font));

        addColor(
                screenUtils.xPercent(15),
                screenUtils.yPercent(10),
                ColorsEnum.RED
        );
        addColor(
                screenUtils.xPercent(55),
                screenUtils.yPercent(10),
                ColorsEnum.BLUE
        );
        addColor(
                screenUtils.xPercent(15),
                screenUtils.yPercent(20),
                ColorsEnum.YELLOW
        );
        addColor(
                screenUtils.xPercent(55),
                screenUtils.yPercent(20),
                ColorsEnum.GREEN
        );
        addColor(
                screenUtils.xPercent(15),
                screenUtils.yPercent(30),
                ColorsEnum.ORANGE
        );
        addColor(
                screenUtils.xPercent(55),
                screenUtils.yPercent(30),
                ColorsEnum.PURPLE
        );
        addColor(
                screenUtils.xPercent(15),
                screenUtils.yPercent(40),
                ColorsEnum.PINK
        );
        addColor(
                screenUtils.xPercent(55),
                screenUtils.yPercent(40),
                ColorsEnum.LIME
        );
        addColor(
                screenUtils.xPercent(15),
                screenUtils.yPercent(50),
                ColorsEnum.CYAN
        );
        addColor(
                screenUtils.xPercent(55),
                screenUtils.yPercent(50),
                ColorsEnum.LIGHTBLUE
        );
        addColor(
                screenUtils.xPercent(15),
                screenUtils.yPercent(60),
                ColorsEnum.MAGENTA
        );
        addColor(
                screenUtils.xPercent(55),
                screenUtils.yPercent(60),
                ColorsEnum.BROWN
        );
        addColor(
                screenUtils.xPercent(15),
                screenUtils.yPercent(70),
                ColorsEnum.WHITE
        );
        addColor(
                screenUtils.xPercent(55),
                screenUtils.yPercent(70),
                ColorsEnum.LIGHTGRAY
        );
        addColor(
                screenUtils.xPercent(15),
                screenUtils.yPercent(80),
                ColorsEnum.GRAY
        );
        addColor(
                screenUtils.xPercent(55),
                screenUtils.yPercent(80),
                ColorsEnum.BLACK
        );
    }

    private final int targetWidth = 30;
    private void addColor(int x, int y, ColorsEnum color){
        StringBuilder text = new StringBuilder(color.getSerializedName());

        this.addRenderableWidget(screenUtils.makeButton(x,y,screenUtils.xPercent(targetWidth),Component.literal(text.toString()).withColor(color.getColor().getRGB()),a->{
            sendPacket(color);
        },this.font));
    }

    private void sendPacket(ColorsEnum col){
        PacketDistributor.sendToServer(new SelectTeamPacket(col.getSerializedName()));
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void renderMenuBackground(GuiGraphics partialTick) {
        partialTick.blit(ResourceLocation.withDefaultNamespace("textures/block/dirt.png"),0,0,0.0F, 0.0F,this.width,this.height,16,16);
        partialTick.blit(TAB_HEADER_BACKGROUND, 0, 0, 0.0F, 0.0F, this.width, this.layout.getHeaderHeight(), 16, 16);
    }
}
