package teayettle.lavabowls.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import teayettle.lavabowls.Register;

public class HotLavaConsumeEffect implements ConsumeEffect {
    @Override
    public Type<? extends ConsumeEffect> getType() {
        return null;
    }

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        if (world instanceof ServerWorld server) {
            server.createExplosion(user, Register.of(server, Register.HOT_LAVA_DMGS), null, user.getPos(), 5f, true, World.ExplosionSourceType.TNT);
            user.damage(server, Register.of(server, Register.HOT_LAVA_DMGS), 20);
            return true;
        }
        return false;
    }
}
