package teayettle.lavabowls.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import teayettle.lavabowls.Register;

public class LavaConsumeEffect implements ConsumeEffect {
    @Override
    public Type<? extends ConsumeEffect> getType() {
        return null;
    }

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        if (world instanceof ServerWorld server) {
            user.damage(server, Register.of(server, Register.LAVA_DMGS), 8);
            user.setOnFireFor(12f);
            return true;
        }
        return false;
    }
}
