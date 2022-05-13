package limonblaze.blazereborn.util;

import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

import java.util.function.Predicate;

public class EntityUtils {

    public static void removeGoalFromSelector(GoalSelector goalSelector, Predicate<WrappedGoal> predicate) {
        goalSelector.availableGoals.stream().filter(predicate).filter(WrappedGoal::isRunning).forEach(WrappedGoal::stop);
        goalSelector.availableGoals.removeIf(predicate);
    }

}
