package limonblaze.blazereborn.data.server;

import limonblaze.blazereborn.common.registry.BrBlocks;
import limonblaze.blazereborn.common.registry.BrItems;
import limonblaze.blazereborn.common.tag.ForgeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BrTagGens {

    public static class BlockProvider extends BlockTagsProvider {

        public BlockProvider(DataGenerator dataGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(dataGenerator, modId, existingFileHelper);
        }

        @Override
        public void addTags() {
            tag(ForgeTags.Blocks.BREWING_STANDS).add(Blocks.BREWING_STAND, BrBlocks.SOUL_BREWING_STAND.get());
        }

    }

    public static class ItemProvider extends ItemTagsProvider {

        public ItemProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(dataGenerator, blockTagsProvider, modId, existingFileHelper);
        }

        @Override
        public void addTags() {
            copy(ForgeTags.Blocks.BREWING_STANDS, ForgeTags.Items.BREWING_STANDS);

            tag(ForgeTags.Items.FIRE_CHARGES).add(
                Items.FIRE_CHARGE,
                BrItems.SOUL_FIRE_CHARGE.get()
            );
            tag(ForgeTags.Items.FISHING_RODS).add(
                Items.FISHING_ROD,
                BrItems.BLAZE_FISHING_ROD.get(),
                BrItems.SOUL_BLAZE_FISHING_ROD.get()
            );
        }

    }

}
