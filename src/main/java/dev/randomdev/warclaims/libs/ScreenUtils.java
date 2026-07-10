package dev.randomdev.warclaims.libs;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.TabButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ScreenUtils <T extends Screen> {
    T screen;
    public ScreenUtils(T screen){
        this.screen = screen;
    }

    public int yPercent(int percent){
        return (int)(((double)percent/100)*screen.height);
    }
    public int xPercent(int percent){
        return (int)(((double)percent/100)*screen.width);
    }
    public StringWidget makeText(int x, int y, String text, Font font){
        return new StringWidget(x-(font.width(text)/2),y,font.width(text),5, Component.literal(text), font);
    }
    public Button makeButton(int x, int y,int width, Component text, Button.OnPress onPress, Font font){
        return Button.builder(text,onPress).bounds(x,y,width,20).build();
    }
    public Button makeButton(int x, int y, Component text, Button.OnPress onPress, Font font){
        return this.makeButton(x,y,font.width(text)+15,text,onPress,font);
    }
    public Button makeButton(int x, int y, String text,Button.OnPress onPress, Font font){
        return this.makeButton(x,y,Component.literal(text),onPress,font);
    }
}
