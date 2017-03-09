package eyeq.leek.event;

import eyeq.leek.Leek;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LeekEventHandler {
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(!(entity instanceof EntityZombie)) {
            return;
        }
        World world = entity.getEntityWorld();
        if(world.rand.nextInt(100) == 0) {
            event.getDrops().add(new EntityItem(world, entity.posX, entity.posY, entity.posZ, new ItemStack(Leek.leek)));
        }
    }
}
