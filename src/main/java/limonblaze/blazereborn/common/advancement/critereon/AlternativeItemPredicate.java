package limonblaze.blazereborn.common.advancement.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import limonblaze.blazereborn.api.BlazeRebornAPI;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A simple extension of {@link ItemPredicate} which works as a "OR" condition
 */
public class AlternativeItemPredicate extends ItemPredicate {
    public static final ResourceLocation ID = BlazeRebornAPI.id("alternative");
    private final List<ItemPredicate> alternatives;

    public AlternativeItemPredicate(List<ItemPredicate> alternatives) {
        super();
        this.alternatives = alternatives;
    }

    public AlternativeItemPredicate(List<ItemPredicate> alternatives,
                                    @Nullable TagKey<Item> tag,
                                    @Nullable Set<Item> items,
                                    MinMaxBounds.Ints count,
                                    MinMaxBounds.Ints durability,
                                    EnchantmentPredicate[] enchantments,
                                    EnchantmentPredicate[] storedEnchantments,
                                    @Nullable Potion potion,
                                    NbtPredicate nbt) {
        super(tag, items, count, durability, enchantments, storedEnchantments, potion, nbt);
        this.alternatives = alternatives;
    }

    public boolean matches(ItemStack stack) {
        if(super.matches(stack)) {
            return true;
        } else {
            for(ItemPredicate predicate : this.alternatives) {
                if(predicate.matches(stack)) return true;
            }
        }
        return false;
    }

    public static ItemPredicate fromJson(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject itemJson = GsonHelper.convertToJsonObject(jsonElement, "item");
            MinMaxBounds.Ints count = MinMaxBounds.Ints.fromJson(itemJson.get("count"));
            MinMaxBounds.Ints durability = MinMaxBounds.Ints.fromJson(itemJson.get("durability"));
            if (itemJson.has("data")) {
                throw new JsonParseException("Disallowed data tag found");
            } else {
                NbtPredicate nbt = NbtPredicate.fromJson(itemJson.get("nbt"));
                Set<Item> items = null;
                JsonArray jsonarray = GsonHelper.getAsJsonArray(itemJson, "items", null);
                if (jsonarray != null) {
                    ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

                    for(JsonElement jsonelement : jsonarray) {
                        ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "item"));
                        builder.add(Optional.ofNullable(ForgeRegistries.ITEMS.getValue(resourcelocation)).orElseThrow(() -> new JsonSyntaxException("Unknown item id '" + resourcelocation + "'")));
                    }

                    items = builder.build();
                }

                TagKey<Item> tagkey = null;
                if (itemJson.has("tag")) {
                    ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.getAsString(itemJson, "tag"));
                    tagkey = TagKey.create(Registry.ITEM_REGISTRY, resourcelocation1);
                }

                Potion potion = null;
                if (itemJson.has("potion")) {
                    ResourceLocation resourcelocation2 = new ResourceLocation(GsonHelper.getAsString(itemJson, "potion"));
                    potion = Optional.ofNullable(ForgeRegistries.POTIONS.getValue(resourcelocation2)).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + resourcelocation2 + "'"));
                }

                EnchantmentPredicate[] enchantments = EnchantmentPredicate.fromJsonArray(itemJson.get("enchantments"));
                EnchantmentPredicate[] storedEnchantments = EnchantmentPredicate.fromJsonArray(itemJson.get("stored_enchantments"));

                if(itemJson.has("alternatives")) {
                    JsonArray jsonArray = GsonHelper.getAsJsonArray(itemJson, "alternatives");
                    List<ItemPredicate> alternatives = new ArrayList<>();
                    for(JsonElement entry : jsonArray) {
                        ItemPredicate predicate = ItemPredicate.fromJson(entry);
                        if(predicate != ANY) {
                            alternatives.add(predicate);
                        }
                    }
                    if(!alternatives.isEmpty()) {
                        return new AlternativeItemPredicate(alternatives, tagkey, items, count, durability, enchantments, storedEnchantments, potion, nbt);
                    }
                }

                return new ItemPredicate(tagkey, items, count, durability, enchantments, storedEnchantments, potion, nbt);
            }
        } else {
            return ANY;
        }
    }

    public JsonElement serializeToJson() {
        JsonElement raw = super.serializeToJson();
        if(this.alternatives.isEmpty()) {
            return raw;
        } else {
            JsonObject self = raw == JsonNull.INSTANCE ? new JsonObject() : raw.getAsJsonObject();
            self.addProperty("type", ID.toString());
            JsonArray alternatives = new JsonArray();
            this.alternatives.forEach(predicate -> alternatives.add(predicate.serializeToJson()));
            self.add("alternatives", alternatives);
            return self;
        }
    }

}