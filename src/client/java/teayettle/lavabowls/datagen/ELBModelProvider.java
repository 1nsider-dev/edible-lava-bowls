package teayettle.lavabowls.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
import teayettle.lavabowls.EdibleLavaBowls;
import teayettle.lavabowls.Register;

public class ELBModelProvider extends FabricModelProvider {
    public ELBModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {
        gen.registerNorthDefaultHorizontalRotation(Register.REGEN_LAVA);
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(Register.LAVA_BOWL, Models.GENERATED);
        gen.register(Register.HOT_LAVA_BOWL, Models.GENERATED);
        gen.register(Register.BRICKWART_STEW, Models.GENERATED);
        gen.register(Register.BUTTERY_CRIMSON, Models.GENERATED);
        gen.register(Register.MAGMA_BUTTER_SLICE, Models.GENERATED);
        gen.register(Register.FRESH_ROOT_SOUP, Models.GENERATED);
        gen.register(Register.OBSIDIAN_BOWL, Models.GENERATED);
        gen.register(Register.WART_MACARONI, Models.GENERATED);
        gen.register(Register.SNIFFER_SOUS_VIDE, Models.GENERATED);
        gen.register(Register.BUTTERED_DUMPLINGS, Models.GENERATED);
    }
}
