package eyeq.leek.world.gen;

import eyeq.leek.Leek;
import eyeq.util.world.gen.WorldGenDecorations;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenLeek extends WorldGenDecorations {
    public WorldGenLeek() {
        super(0.05F);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        for(int i = 0; i < 64; i++) {
            BlockPos blockPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            Block block = world.getBlockState(blockPos.down()).getBlock();
            if(world.isAirBlock(blockPos) && (block == Blocks.GRASS || block == Blocks.DIRT)) {
                world.setBlockState(blockPos.down(), Blocks.FARMLAND.getDefaultState(), 2);
                world.setBlockState(blockPos, Leek.leeks.getDefaultState(), 2);
            }
        }
        return true;
    }
}
