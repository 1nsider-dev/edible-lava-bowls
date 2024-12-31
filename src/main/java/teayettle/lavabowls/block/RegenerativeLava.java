package teayettle.lavabowls.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import teayettle.lavabowls.Register;

public class RegenerativeLava extends Block {
    public static DirectionProperty FACING;

    public RegenerativeLava(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item = stack.getItem();
        if (item == Items.BUCKET) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            exchangeStack(stack, player, new ItemStack(Items.LAVA_BUCKET));
            return ItemActionResult.success(true);
        } else if (item == Items.BOWL) {
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            exchangeStack(stack, player, new ItemStack(Register.LAVA_BOWL));
            return ItemActionResult.success(true);
        }

        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static void exchangeStack(ItemStack inputStack, PlayerEntity player, ItemStack outputStack) {
        inputStack.decrementUnlessCreative(1, player);
        if (!player.getInventory().insertStack(outputStack)) {
            player.dropItem(outputStack, false);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double g = 0.8f;
        double b = 1f - g;
        double d = pos.getX() + b;
        double e = pos.getY();
        double f = pos.getZ() + b;
        if (random.nextDouble() < 0.1) {
            world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }

        world.addParticle(ParticleTypes.SMOKE, d + random.nextDouble() * g, e + 1, f + random.nextDouble() * g, 0.0F, random.nextDouble() * 0.1f, 0.0F);
        world.addParticle(ParticleTypes.FLAME, d + random.nextDouble() * g, e + 1, f + random.nextDouble() * g, 0.0F, random.nextDouble() * 0.01f, 0.0F);
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
    }
}
