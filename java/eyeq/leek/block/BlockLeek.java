package eyeq.leek.block;

import eyeq.leek.Leek;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLeek extends BlockCrops {
    @Override
    protected Item getSeed() {
        return Leek.leek;
    }

    @Override
    protected Item getCrop() {
        return Leek.leek;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        for(int i = 0; i < 2; i++) {
            super.updateTick(world, pos, state, rand);
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack itemStack) {
        super.harvestBlock(world, player, pos, state, tileEntity, itemStack);
        if(itemStack.getItem() instanceof ItemSword && state.getValue(AGE) == 7) {
            world.setBlockState(pos, Leek.leeks.getDefaultState());
            itemStack.damageItem(1, player);
        }
    }
}
