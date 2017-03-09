package eyeq.leek.item;

import eyeq.util.item.UItemSwordFood;
import net.minecraft.item.ItemStack;

public class ItemSwordLeek extends UItemSwordFood {
    public ItemSwordLeek(ToolMaterial material, int amount, float saturation) {
        super(material, amount, saturation);
    }

    @Override
    public int getHealAmount(ItemStack itemStack) {
        return super.getHealAmount(itemStack) * itemStack.getItemDamage() / itemStack.getMaxDamage();
    }

    @Override
    public float getSaturationModifier(ItemStack itemStack) {
        return super.getSaturationModifier(itemStack) * itemStack.getItemDamage() / itemStack.getMaxDamage();
    }
}
