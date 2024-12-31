package teayettle.lavabowls.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import teayettle.lavabowls.Register;

import java.util.List;

public class HotLavaBowl extends Item {

    public HotLavaBowl(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        MutableText t = Text.translatable("tooltip.edible-lava-bowls.hot_lava_bowl");
        t.setStyle(t.getStyle().withColor(Formatting.RED));
        tooltip.add(t);
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (world instanceof ServerWorld server) {
            server.createExplosion(user, Register.of(server, Register.HOT_LAVA_DMGS), null, user.getPos(), 5f, true, World.ExplosionSourceType.TNT);
            user.damage(Register.of(server, Register.HOT_LAVA_DMGS), 20);
        }
        return itemStack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}