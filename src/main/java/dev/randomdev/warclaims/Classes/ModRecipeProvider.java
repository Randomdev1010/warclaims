package dev.randomdev.warclaims.Classes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,Blocks.CLAIMER)
                .define('L', Items.REDSTONE_LAMP)
                .define('O',Items.OBSIDIAN)
                .define('D',Items.DIAMOND)
                .pattern("LOL")
                .pattern("ODO")
                .pattern("LOL")
                .unlockedBy("has_diamond",has(Items.DIAMOND))
                .unlockedBy("has_lamp",has(Items.REDSTONE_LAMP))
                .save(recipeOutput);
    }
}
