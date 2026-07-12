package dev.randomdev.warclaims;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CAN_BREAK_ON_ENEMY = BUILDER
            .comment("Whether or not you can break blocks on enemy territory")
            .define("canBreakOnEnemyTerritory", true);
    public static final ModConfigSpec.BooleanValue CAN_HURT_ON_ENEMY = BUILDER
            .comment("Whether or not you can hurt mobs on enemy territory")
            .define("canHurtOnEnemyTerritory", true);

    public static final ModConfigSpec.IntValue ENERGY_DRAIN = BUILDER
            .comment("How much energy is drained while a claimer or capital is on")
            .defineInRange("energyDrain", 2, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue CAPITAL_MAX = BUILDER
            .comment("How much energy capitals can have")
            .defineInRange("capitalMax", 500, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue CLAIMER_MAX = BUILDER
            .comment("How much energy claimers can have")
            .defineInRange("claimerMax", 250, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
