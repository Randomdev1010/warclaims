package dev.randomdev.warclaims.libs;

import net.minecraft.util.StringRepresentable;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public enum ColorsEnum implements StringRepresentable {
    WHITE("white",Color.WHITE),
    LIGHTGRAY("lightgray",Color.LIGHT_GRAY),
    GRAY("gray",Color.GRAY),
    BLACK("black",Color.BLACK),
    BLUE("blue",Color.BLUE),
    LIGHTBLUE("lightblue",new Color(3847130)),
    CYAN("cyan",Color.CYAN),
    GREEN("green",Color.GREEN),
    LIME("lime",new Color(8439583)),
    MAGENTA("magenta",Color.MAGENTA),
    PINK("pink",Color.PINK),
    PURPLE("purple",new Color(8991416)),
    RED("red",Color.RED),
    YELLOW("yellow",Color.YELLOW),
    ORANGE("orange",Color.ORANGE),
    BROWN("brown",new Color(8606770));

    private final String name;
    private final Color color;

    public static ColorsEnum getFromName(String name){
        ColorsEnum[] colors = ColorsEnum.values();

        for (ColorsEnum color : colors) {
            if (color.getSerializedName().equals(name)) {
                return color;
            }
        }
        return null;
    }

    ColorsEnum(String name,Color color){
        this.name = name;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
