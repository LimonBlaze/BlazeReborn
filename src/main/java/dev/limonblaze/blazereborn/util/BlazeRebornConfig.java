package dev.limonblaze.blazereborn.util;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BlazeRebornConfig {
    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        Pair<Server, ForgeConfigSpec> server = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = server.getLeft();
        SERVER_SPEC = server.getRight();
    }

    public static class Server {

        public final Brewing brewing;
        public final EntityGeneration entityGeneration;

        public Server(ForgeConfigSpec.Builder builder) {
            builder
                .comment("Brewing Settings")
                .push("brewing");
            this.brewing = new Brewing(builder);
            builder.pop();
            builder
                .comment("Entity Generation Settings")
                .push("entity_generation");
            this.entityGeneration = new EntityGeneration(builder);
            builder.pop();
        }

        public static class EntityGeneration {

            public final ForgeConfigSpec.BooleanValue convertNaturallySpawnedBlazes;
            public final ForgeConfigSpec.BooleanValue convertBlazeSpawners;
            public final ForgeConfigSpec.BooleanValue generateSoulMagmaCubes;

            public EntityGeneration(ForgeConfigSpec.Builder builder) {
                convertNaturallySpawnedBlazes = builder
                    .translation(MiscUtils.createTranslation("config", "entity_generation", "convert_naturally_spawned_blazes"))
                    .comment("If true, Blazes who natually spawned in #blaze_reborn:spawns_soul_variant_mobs(Default: [minecraft:soul_sand_valley]) will be converted into Soul Blazes")
                    .define("convert_naturally_spawned_blazes", true);
                convertBlazeSpawners = builder
                    .translation(MiscUtils.createTranslation("config", "entity_generation", "convert_blaze_spawners"))
                    .comment("If true, Blaze spawners in the nether fortress who natually generated in #blaze_reborn:spawns_soul_variant_mobs(Default: [minecraft:soul_sand_valley]) will be converted into Soul Blaze spawners")
                    .define("convert_blaze_spawners", true);
                generateSoulMagmaCubes = builder
                    .translation(MiscUtils.createTranslation("config", "entity_generation", "generate_soul_magma_cubes"))
                    .comment("If true, Soul Magma Cubes will spawn in #blaze_reborn:spawns_soul_variant_mobs(Default: [minecraft:soul_sand_valley])")
                    .define("generate_soul_magma_cubes", true);
            }

        }

        public static class Brewing {

            public final ForgeConfigSpec.IntValue soulBrewingStandBrewTime;

            public Brewing(ForgeConfigSpec.Builder builder) {
                soulBrewingStandBrewTime = builder
                    .translation(MiscUtils.createTranslation("config", "brewing", "soul_brewing_stand_brew_time"))
                    .comment("Defines how long a soul brewing stand should spend to finish a brewing task.")
                    .defineInRange("soul_brewing_stand_brew_time", 200, 0, Integer.MAX_VALUE);
            }
        }

    }

}
