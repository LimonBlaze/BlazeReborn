package limonblaze.blazereborn.common.loot.function;

import com.google.gson.*;
import limonblaze.blazereborn.common.registry.BrLoots;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConditionedSmeltingItemFunction extends LootItemConditionalFunction {
    private final List<ItemPredicate> itemPredicates;

    ConditionedSmeltingItemFunction(LootItemCondition[] predicates, List<ItemPredicate> itemPredicates) {
        super(predicates);
        this.itemPredicates = itemPredicates;
    }

    @Override
    public LootItemFunctionType getType() {
        return BrLoots.CONDITIONED_SMELTING;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        if(stack.isEmpty()) {
            return stack;
        }
        boolean matches = false;
        for(ItemPredicate itemPredicate : itemPredicates) {
            if(itemPredicate.matches(stack)) {
                matches = true;
                break;
            }
        }
        if(matches) {
            Optional<SmeltingRecipe> optional = context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), context.getLevel());
            if (optional.isPresent()) {
                ItemStack itemstack = optional.get().getResultItem();
                if (!itemstack.isEmpty()) {
                    ItemStack smelted = itemstack.copy();
                    smelted.setCount(stack.getCount() * itemstack.getCount());
                    return smelted;
                }
            }
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder(List<ItemPredicate> itemPredicates) {
        return simpleBuilder(lootItemConditions -> new ConditionedSmeltingItemFunction(lootItemConditions, itemPredicates));
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<ConditionedSmeltingItemFunction> {

        public void serialize(JsonObject json, ConditionedSmeltingItemFunction function, JsonSerializationContext context) {
            super.serialize(json, function, context);
            JsonArray itemPredicates = new JsonArray();
            function.itemPredicates.forEach(itemPredicate -> itemPredicates.add(itemPredicate.serializeToJson()));
            json.add("item", itemPredicates);
        }

        public ConditionedSmeltingItemFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] lootItemConditions) {
            JsonArray itemPredicateJsons = GsonHelper.getAsJsonArray(json, "item");
            List<ItemPredicate> predicates = new ArrayList<>();
            for(JsonElement itemPrediacteJson : itemPredicateJsons) {
                ItemPredicate predicate = ItemPredicate.fromJson(itemPrediacteJson);
                if(predicate != ItemPredicate.ANY) predicates.add(predicate);
            }
            return new ConditionedSmeltingItemFunction(lootItemConditions, predicates);
        }

    }

}
