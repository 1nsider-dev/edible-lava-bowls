package teayettle.lavabowls.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teayettle.lavabowls.Register;

@Mixin(Item.class)
public class ItemMixin {
	@SuppressWarnings("unchecked")
    @Inject(at = @At("HEAD"), method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", cancellable = true)
	private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable info) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (itemStack.getItem() == Items.BOWL) {
			BlockHitResult blockHitResult = _raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
			if (blockHitResult.getType() == HitResult.Type.MISS) {
				info.setReturnValue(TypedActionResult.pass(itemStack));
			} else {
				if (blockHitResult.getType() == HitResult.Type.BLOCK) {
					BlockPos blockPos = blockHitResult.getBlockPos();
					if (!world.canPlayerModifyAt(user, blockPos)) {
						info.setReturnValue(TypedActionResult.pass(itemStack));
					}

					if (world.getFluidState(blockPos).isIn(FluidTags.LAVA)) {
						world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
						world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
						info.setReturnValue(TypedActionResult.success(_exchangeStack(itemStack, user, new ItemStack(Register.LAVA_BOWL))));
					}
				}

				info.setReturnValue(TypedActionResult.pass(itemStack));
			}
		}
    }

	private static ItemStack _exchangeStack(ItemStack inputStack, PlayerEntity player, ItemStack outputStack) {
		inputStack.decrementUnlessCreative(1, player);
		if (!player.getInventory().insertStack(outputStack)) {
			player.dropItem(outputStack, false);
		}
		if (inputStack.isEmpty()) {
			return outputStack;
		} else {
			return inputStack;
		}
	}
	private static BlockHitResult _raycast(World world, PlayerEntity player, RaycastContext.FluidHandling fluidHandling) {
		Vec3d vec3d = player.getEyePos();
		Vec3d vec3d2 = vec3d.add(player.getRotationVector(player.getPitch(), player.getYaw()).multiply(player.getBlockInteractionRange()));
		return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, fluidHandling, player));
	}
}