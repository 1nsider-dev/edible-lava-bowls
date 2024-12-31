package teayettle.lavabowls.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.BlastingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import teayettle.lavabowls.Register;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        FabricRecipeProvider.offerBlasting(exporter, List.of(Register.LAVA_BOWL), RecipeCategory.FOOD, Register.HOT_LAVA_BOWL, 0.7f, 100, "lava_bowl_to_hot");
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Register.REGEN_LAVA, 1)
                .pattern("bjb")
                .pattern("brb")
                .pattern("bib")
                .input('b', Items.BRICK)
                .input('r', Register.HOT_LAVA_BOWL)
                .input('i', Items.IRON_BLOCK)
                .input('j', Items.IRON_BARS)
                .group("regen_lava")
                .criterion(FabricRecipeProvider.hasItem(Register.HOT_LAVA_BOWL), FabricRecipeProvider.conditionsFromItem(Register.HOT_LAVA_BOWL))
                .offerTo(exporter);
    }
}
