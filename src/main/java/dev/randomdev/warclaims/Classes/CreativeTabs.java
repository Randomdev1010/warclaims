package dev.randomdev.warclaims.Classes;

import dev.randomdev.warclaims.Classes.CustomBlocks.Capital;
import dev.randomdev.warclaims.Classes.CustomBlocks.Claimer;
import dev.randomdev.warclaims.WarClaims;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabs  {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = WarClaims.CREATIVE_MODE_TABS;

    public static final Supplier<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab",()-> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + WarClaims.MODID + ".main_tab"))
            .icon(()->new ItemStack(Items.CAPITAL.get()))
            .displayItems((params, output) -> {
                output.accept(Items.CAPITAL.get());
                // Accepts an ItemLike. This assumes that MY_BLOCK has a corresponding item.
                output.accept(Items.CLAIMER.get());
            })
            .build()
    );

    public static void register(){
    }
}
