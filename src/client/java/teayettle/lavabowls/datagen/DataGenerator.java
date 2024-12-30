package teayettle.lavabowls.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import teayettle.lavabowls.EdibleLavaBowls;
import teayettle.lavabowls.Register;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack p = gen.createPack();
        p.addProvider(ELBModelProvider::new);
        p.addProvider(AdvancementsProvider::new);
    }

    static class AdvancementsProvider extends FabricAdvancementProvider {

        protected AdvancementsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
            AdvancementEntry rootAdv = Advancement.Builder.create()
                    .display(
                            Register.LAVA_BOWL,
                            Text.translatable(EdibleLavaBowls.MODID + ".adv.name.root"),
                            Text.translatable(EdibleLavaBowls.MODID + ".adv.desc.root"),
                            Identifier.of(EdibleLavaBowls.MODID, "textures/item/still_lava.png"),
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("drank_lava_bowl", ConsumeItemCriterion.Conditions.item(null, Register.LAVA_BOWL))
                    .build(consumer, EdibleLavaBowls.MODID + "/root");
            AdvancementEntry hotAdv = Advancement.Builder.create().parent(rootAdv)
                    .display(
                            Register.HOT_LAVA_BOWL,
                            Text.translatable(EdibleLavaBowls.MODID + ".adv.name.hotlava"),
                            Text.translatable(EdibleLavaBowls.MODID + ".adv.desc.hotlava"),
                            null,
                            AdvancementFrame.CHALLENGE,
                            true,
                            true,
                            true
                    )
                    .criterion("drank_hot_lava_bowl", ConsumeItemCriterion.Conditions.item(null, Register.HOT_LAVA_BOWL))
                    .build(consumer, EdibleLavaBowls.MODID + "/drank_hot_lava_bowl");
        }
    }
}
