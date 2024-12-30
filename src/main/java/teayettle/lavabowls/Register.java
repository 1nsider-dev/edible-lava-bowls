package teayettle.lavabowls;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.TeleportRandomlyConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import teayettle.lavabowls.item.HotLavaConsumeEffect;
import teayettle.lavabowls.item.LavaBowl;
import teayettle.lavabowls.item.LavaConsumeEffect;

import java.util.function.Function;

public class Register {
    public static ConsumableComponent.Builder drink() {
        return ConsumableComponent.builder().consumeSeconds(1.6F).useAction(UseAction.DRINK).sound(SoundEvents.ENTITY_GENERIC_DRINK).consumeParticles(true);
    }

    public static final FoodComponent lavaComponent = new FoodComponent.Builder().alwaysEdible().nutrition(4).saturationModifier(0).build();
    public static final ConsumableComponent lavaConsume = drink().consumeEffect(new LavaConsumeEffect()).build();
    public static final ConsumableComponent hotLavaConsume = drink().consumeEffect(new HotLavaConsumeEffect()).build();
    public static final Item LAVA_BOWL = generateItem("lava_bowl", Item::new, new Item.Settings().maxCount(1).food(lavaComponent, lavaConsume).useRemainder(Items.BOWL));
    public static final Item HOT_LAVA_BOWL = generateItem("hot_lava_bowl", LavaBowl::new, new Item.Settings().maxCount(1).food(lavaComponent, hotLavaConsume).useRemainder(Items.BOWL));
    public static final RegistryKey<DamageType> LAVA_DMGS = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(EdibleLavaBowls.MODID, "lavabowl"));
    public static final RegistryKey<DamageType> HOT_LAVA_DMGS = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(EdibleLavaBowls.MODID, "hotlavabowl"));

    // entities
    // handle regs

    public static Item generateItem(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EdibleLavaBowls.MODID, path));
        return Items.register(registryKey, factory, settings);
    }
    private static EntityType<? extends Entity> generateEntity(String id, EntityType<?> ent) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(EdibleLavaBowls.MODID, id), ent);
    }
    private static Block generateBlock(String id, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(EdibleLavaBowls.MODID, id), block);
    }
    public static void registerItems(){
        Registry.register(Registries.ITEM_GROUP, Identifier.of(EdibleLavaBowls.MODID, "itemgroup"), ITEM_GROUP);
    }
    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(key));
    }
    private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(LAVA_BOWL))
            .displayName(Text.translatable("itemGroup."+EdibleLavaBowls.MODID))
            .entries((context, entries) -> {
                entries.add(LAVA_BOWL);
                entries.add(HOT_LAVA_BOWL);
            })
            .build();
}
