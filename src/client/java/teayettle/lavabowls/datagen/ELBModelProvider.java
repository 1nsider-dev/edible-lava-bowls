package teayettle.lavabowls.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.registry.RegistryWrapper;
import teayettle.lavabowls.Register;

public class ELBModelProvider extends FabricModelProvider {
    public ELBModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(Register.LAVA_BOWL, Models.GENERATED);
        gen.register(Register.HOT_LAVA_BOWL, Models.GENERATED);
    }
}
