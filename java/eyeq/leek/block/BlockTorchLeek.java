package eyeq.leek.block;

import eyeq.leek.Leek;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockTorchLeek extends BlockTorch {
    private final boolean isOn;

    public BlockTorchLeek(boolean isOn) {
        super();
        this.isOn = isOn;
        if(isOn) {
            this.setLightLevel(2.0F);
        }
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if(!world.isRemote) {
            if(isOn != world.isBlockPowered(pos)) {
                world.setBlockState(pos, (isOn ? Leek.leeksTorch : Leek.leeksTorchLight).getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
            }
        }
        super.onBlockAdded(world, pos, state);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if(!world.isRemote) {
            if(isOn != world.isBlockPowered(pos)) {
                if(isOn) {
                    world.scheduleUpdate(pos, this, 4);
                } else {
                    world.setBlockState(pos, Leek.leeksTorchLight.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
                }
            }
        }
        super.neighborChanged(state, world, pos, block, fromPos);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        if(world.isRemote) {
            return;
        }
        if(this.isOn && !world.isBlockPowered(pos)) {
            world.setBlockState(pos, Leek.leeksTorch.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState world, World pos, BlockPos state, Random rand) {}

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Leek.leek;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(Leek.leek);
    }
}
