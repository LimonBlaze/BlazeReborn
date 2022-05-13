package limonblaze.blazereborn.common;

import limonblaze.blazereborn.api.extension.FireVariantHoldingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onFireDamage(LivingHurtEvent event) {
        if(event.getSource() == DamageSource.ON_FIRE) {
            event.setAmount(event.getAmount() * ((FireVariantHoldingEntity)event.getEntity()).getFireVariant().getPriority());
        }
    }

}
