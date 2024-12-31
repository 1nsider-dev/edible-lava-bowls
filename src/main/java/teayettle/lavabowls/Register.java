package teayettle.lavabowls;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.stat.Stat;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import teayettle.lavabowls.block.RegenerativeLava;
import teayettle.lavabowls.item.HotLavaBowl;
import teayettle.lavabowls.item.LavaBowl;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class Register {

    public static final FoodComponent lavaComponent = new FoodComponent.Builder().alwaysEdible().nutrition(4).saturationModifier(0).usingConvertsTo(Items.BOWL).build();

    public static final Item LAVA_BOWL = generateItem("lava_bowl", new LavaBowl(new Item.Settings().maxCount(16).food(lavaComponent)));
    public static final Item HOT_LAVA_BOWL = generateItem("hot_lava_bowl", new HotLavaBowl(new Item.Settings().maxCount(16).food(lavaComponent)));
    public static final RegistryKey<DamageType> LAVA_DMGS = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(EdibleLavaBowls.MODID, "lavabowl"));
    public static final RegistryKey<DamageType> HOT_LAVA_DMGS = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(EdibleLavaBowls.MODID, "hotlavabowl"));

    public static final Item OBSIDIAN_BOWL = generateItem("obsidian_bowl", new Item(new Item.Settings().maxCount(16).food(
            new FoodComponent.Builder().alwaysEdible().nutrition(10).saturationModifier(0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60*20, 3), 1)
                    .usingConvertsTo(Items.BOWL).build()
    )));
    public static final Item BRICKWART_STEW = generateItem("brickwart_stew", new Item(new Item.Settings().maxCount(16).food(
            new FoodComponent.Builder().alwaysEdible().nutrition(10).saturationModifier(0.4f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30*20), 1)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 30*20), 1)
                    .usingConvertsTo(Items.BOWL).build()
    )));
    public static final Item FRESH_ROOT_SOUP = generateItem("fresh_root_soup", new Item(new Item.Settings().maxCount(16).food(
            new FoodComponent.Builder().nutrition(9).saturationModifier(0.7f)
                    .statusEffect(new StatusEffectInstance(ModEffects.COMFORT, 30*20), 1)
                    .usingConvertsTo(Items.BOWL).build()
    )));
    public static final Item BUTTERY_CRIMSON = generateItem("buttery_crimson", new Item(new Item.Settings().maxCount(16).food(
            new FoodComponent.Builder().nutrition(13).saturationModifier(0.6f)
                    .statusEffect(new StatusEffectInstance(ModEffects.COMFORT, 60*20), 1)
                    .usingConvertsTo(Items.BOWL).build()
    )));
    public static final Item BUTTERED_DUMPLINGS = generateItem("buttered_dumplings", new Item(new Item.Settings().maxCount(16).food(
            new FoodComponent.Builder().nutrition(11).saturationModifier(0.7f)
                    .statusEffect(new StatusEffectInstance(ModEffects.COMFORT, 60*20), 1)
                    .usingConvertsTo(Items.BOWL).build()
    )));
    public static final Item WART_MACARONI = generateItem("wart_macaroni", new Item(new Item.Settings().maxCount(16).food(
            new FoodComponent.Builder().nutrition(13).saturationModifier(0.7f)
                    .statusEffect(new StatusEffectInstance(ModEffects.COMFORT, 60*20), 1)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 30*20), 0.5f)
                    .usingConvertsTo(Items.BOWL).build()
    )));
    public static final Item SNIFFER_SOUS_VIDE = generateItem("sniffer_sous_vide", new Item(new Item.Settings().maxCount(16).food(
            new FoodComponent.Builder().nutrition(16).saturationModifier(0.8f)
                    .statusEffect(new StatusEffectInstance(ModEffects.NOURISHMENT, 60*5*20), 1)
                    .usingConvertsTo(Items.BOWL).build()
    )));
    public static final Item MAGMA_BUTTER_SLICE = generateItem("magma_butter_slice", new Item(new Item.Settings().maxCount(64).food(
            new FoodComponent.Builder().alwaysEdible().nutrition(1).saturationModifier(0f).snack().build()
    )));

    public static final Block REGEN_LAVA = generateBlock("regenerative_lava", new RegenerativeLava(AbstractBlock.Settings.create()
            .hardness(2f)
            .nonOpaque()
            .sounds(BlockSoundGroup.METAL)
            .luminance(state -> 6)
            .requiresTool()));

    // entities
    // handle regs

    public static Item generateItem(String path, Item item) {
        Identifier itemID = Identifier.of(EdibleLavaBowls.MODID, path);
        return Registry.register(Registries.ITEM, itemID, item);
    }
    private static EntityType<? extends Entity> generateEntity(String id, EntityType<?> ent) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(EdibleLavaBowls.MODID, id), ent);
    }
    private static Block generateBlock(String id, Block block) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        generateItem(id, blockItem);
        return Registry.register(Registries.BLOCK, Identifier.of(EdibleLavaBowls.MODID, id), block);
    }
    public static void registerItems(){
        Registry.register(Registries.ITEM_GROUP, Identifier.of(EdibleLavaBowls.MODID, "itemgroup"), ITEM_GROUP);
    }
    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
    private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(LAVA_BOWL))
            .displayName(Text.translatable("itemGroup."+EdibleLavaBowls.MODID))
            .entries((context, entries) -> {
                entries.add(REGEN_LAVA);
                entries.add(LAVA_BOWL);
                entries.add(OBSIDIAN_BOWL);
                entries.add(HOT_LAVA_BOWL);
                entries.add(BRICKWART_STEW);
                entries.add(FRESH_ROOT_SOUP);
                entries.add(WART_MACARONI);
                entries.add(MAGMA_BUTTER_SLICE);
                entries.add(BUTTERED_DUMPLINGS);
                entries.add(SNIFFER_SOUS_VIDE);
                entries.add(BUTTERY_CRIMSON);
            })
            .build();
}
