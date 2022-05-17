package limonblaze.blazereborn.common.registry;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.common.menu.SoulBrewingStandMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BrMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, BlazeRebornAPI.MODID);

    public static final RegistryObject<MenuType<SoulBrewingStandMenu>> SOUL_BREWING_STAND = MENU_TYPES.register("soul_brewing_stand", () ->
        new MenuType<>(SoulBrewingStandMenu::new));

}
