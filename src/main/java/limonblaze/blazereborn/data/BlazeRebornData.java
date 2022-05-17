package limonblaze.blazereborn.data;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BlazeRebornAPI.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlazeRebornData {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if(event.includeServer()) {

        }
        if(event.includeClient()) {

        }
    }

}
