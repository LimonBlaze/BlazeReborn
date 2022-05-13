package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.BlazeReborn;
import limonblaze.blazereborn.common.menu.SoulBrewingStandMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlazeRebornMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, BlazeReborn.MODID);

    public static final RegistryObject<MenuType<SoulBrewingStandMenu>> SOUL_BREWING_STAND = MENU_TYPES.register("soul_brewing_stand", () ->
        new MenuType<>(SoulBrewingStandMenu::new));

}
