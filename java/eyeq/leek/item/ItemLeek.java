package eyeq.leek.item;

import eyeq.leek.Leek;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLeek extends ItemSeedFood {
    public ItemLeek(int healAmount, float saturation, Block crops, Block soil) {
        super(healAmount, saturation, crops, soil);
        this.setFull3D();
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer player, Entity entity) {
        ItemStack sword = new ItemStack(Leek.leekSword);
        player.setHeldItem(EnumHand.MAIN_HAND, sword);
        itemStack.shrink(1);
        if(itemStack.getCount() > 0) {
            if(!player.inventory.addItemStackToInventory(itemStack)) {
                player.dropItem(itemStack, false);
            }
        }
        return sword.getItem().onLeftClickEntity(itemStack, player, entity);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS) {
            return EnumActionResult.SUCCESS;
        }
        ItemStack torch = new ItemStack(Leek.leeksTorch);
        if(torch.getItem().onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS) {
            itemStack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
}
