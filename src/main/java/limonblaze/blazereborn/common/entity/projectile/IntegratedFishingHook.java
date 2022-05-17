package limonblaze.blazereborn.common.entity.projectile;

import limonblaze.blazereborn.api.BlazeRebornAPI;
import limonblaze.blazereborn.api.extension.fire.FireVariant;
import limonblaze.blazereborn.api.extension.fire.FireVariantHoldingEntity;
import limonblaze.blazereborn.common.entity.ProtectedItemEntity;
import limonblaze.blazereborn.common.registry.BrEntityTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemFishedEvent;

import java.util.Collections;
import java.util.List;

public abstract class IntegratedFishingHook extends FishingHook implements FireVariantHoldingEntity {
    public static final ResourceLocation LOOT_TABLE_FISHING_LAVA = BlazeRebornAPI.id("gameplay/fishing_lava");
    public boolean inLava;

    public IntegratedFishingHook(EntityType<? extends IntegratedFishingHook> type, Level level, int luck, int lureSpeed) {
        super(type, level, luck, lureSpeed);
    }

    public IntegratedFishingHook(EntityType<? extends IntegratedFishingHook> type, Level level) {
        this(type, level, 0, 0);
    }

    /**
     * A copy of {@link FishingHook#FishingHook(Player, Level, int, int)}
     */
    public IntegratedFishingHook(EntityType<? extends IntegratedFishingHook> type, Player player, Level level, int luck, int lureSpeed) {
        this(type, level, luck, lureSpeed);
        this.setOwner(player);
        float PI = (float) Math.PI;
        float D2R = PI / 180F;
        float playerXRot = player.getXRot();
        float playerYRot = player.getYRot();
        float vecY = Mth.sin(-playerXRot * D2R) / Mth.cos(-playerXRot * D2R);
        float vecX = - Mth.sin(-playerYRot * D2R - PI);
        float vecZ = - Mth.cos(-playerYRot * D2R - PI);
        double x = player.getX() + vecX * 0.3D;
        double y = player.getEyeY();
        double z = player.getZ() + vecZ * 0.3D;
        this.moveTo(x, y, z, playerYRot, playerXRot);
        Vec3 vec = new Vec3(vecX, Mth.clamp(vecY, -5.0F, 5.0F), vecZ);
        double length = vec.length();
        vec = vec.multiply(0.6D / length + 0.5D + this.random.nextGaussian() * 0.0045D, 0.6D / length + 0.5D + this.random.nextGaussian() * 0.0045D, 0.6D / length + 0.5D + this.random.nextGaussian() * 0.0045D);
        this.setDeltaMovement(vec);
        this.setYRot((float)(Mth.atan2(vec.x, vec.z) / D2R));
        this.setXRot((float)(Mth.atan2(vec.y, vec.horizontalDistance()) / D2R));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    public void tick() {
        this.syncronizedRandom.setSeed(this.getUUID().getLeastSignificantBits() ^ this.level.getGameTime());

        super.tick();

        Player player = this.getPlayerOwner();

        if(player == null) {
            this.discard();
        } else if(this.level.isClientSide || !this.shouldStopFishing(player)) {

            if(this.onGround) {
                ++this.life;
                if(this.life >= 1200) {
                    this.discard();
                    return;
                }
            } else {
                this.life = 0;
            }

            //Fluid check
            BlockPos pos = this.blockPosition();
            FluidState fluidstate = this.level.getFluidState(pos);
            this.inLava = fluidstate.is(FluidTags.LAVA);
            float fluidHeight = fluidstate.getHeight(this.level, pos);
            boolean flag = fluidHeight > 0.0F;
            
            if(this.currentState == FishingHook.FishHookState.FLYING) {
                if(this.hookedIn != null) {
                    this.setDeltaMovement(Vec3.ZERO);
                    this.currentState = FishingHook.FishHookState.HOOKED_IN_ENTITY;
                    return;
                }

                if(flag) {
                    double speedModifier = this.inLava ? 0.5D : 1.0D;
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.3D, 0.2D, 0.3D).scale(speedModifier));
                    this.currentState = FishingHook.FishHookState.BOBBING;
                    return;
                }

                this.checkCollision();
            } else {
                if(this.currentState == FishingHook.FishHookState.HOOKED_IN_ENTITY) {
                    if(this.hookedIn != null) {
                        if(!this.hookedIn.isRemoved() && this.hookedIn.level.dimension() == this.level.dimension()) {
                            this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8D), this.hookedIn.getZ());
                        } else {
                            this.setHookedEntity(null);
                            this.currentState = FishingHook.FishHookState.FLYING;
                        }
                    }
                    
                    return;
                }

                if(this.currentState == FishingHook.FishHookState.BOBBING) {
                    Vec3 velocity = this.getDeltaMovement();
                    double dY = this.getY() + velocity.y - pos.getY() - fluidHeight;
                    if(Math.abs(dY) < 0.01D) {
                        dY += Math.signum(dY) * 0.1D;
                    }

                    this.setDeltaMovement(velocity.x * 0.9D, velocity.y - dY * this.random.nextFloat() * 0.2D, velocity.z * 0.9D);
                    if(this.nibble <= 0 && this.timeUntilHooked <= 0) {
                        this.openWater = true;
                    } else {
                        this.openWater = this.openWater && this.outOfWaterTime < 10 && this.calculateOpenWater(pos);
                    }

                    if(flag) {
                        this.outOfWaterTime = Math.max(0, this.outOfWaterTime - 1);
                        if(this.biting) {
                            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.1D * this.syncronizedRandom.nextFloat() * this.syncronizedRandom.nextFloat(), 0.0D));
                        }

                        if(!this.level.isClientSide) {
                            this.catchingFish(pos);
                        }
                    } else {
                        this.outOfWaterTime = Math.min(10, this.outOfWaterTime + 1);
                    }
                }
            }

            if(!fluidstate.isEmpty()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            this.updateRotation();
            if(this.currentState == FishingHook.FishHookState.FLYING && (this.onGround || this.horizontalCollision)) {
                this.setDeltaMovement(Vec3.ZERO);
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.92D));
            this.reapplyPosition();
        }
    }

    @Override
    public void catchingFish(BlockPos pos) {
        ServerLevel serverlevel = (ServerLevel)this.level;
        //Define lava related variables
        Material material;
        SimpleParticleType bubbleParticle;
        SimpleParticleType fishingParticle;
        SimpleParticleType splashParticle;
        if(this.inLava) {
            material = Material.LAVA;
            bubbleParticle = ParticleTypes.LAVA;
            fishingParticle = ParticleTypes.SMOKE;
            splashParticle = ParticleTypes.FLAME;
        } else {
            material = Material.WATER;
            bubbleParticle = ParticleTypes.BUBBLE;
            fishingParticle = ParticleTypes.FISHING;
            splashParticle = ParticleTypes.SPLASH;
        }
        //Calculate environment bonus
        int fishingSpeed = 1;
        BlockPos abovePos = pos.above();
        //For lava fishing: bonus in ultra warm dimensions (The Nether)
        //For water fishing: bonus in rain
        if(this.random.nextFloat() < 0.25F) {

            if(this.inLava ? this.level.dimensionType().ultraWarm() : this.level.isRainingAt(abovePos)) {
                ++fishingSpeed;
            }
        }
        //For lava fishing: drawback when can see sky
        //For water fishing:drawback when can't see sky
        if(this.random.nextFloat() < 0.5F) {
            boolean canSeeSky = this.level.canSeeSky(abovePos);
            if(this.inLava == canSeeSky) {
                --fishingSpeed;
            }
        }
        //Main logic
        if(this.nibble > 0) {
            --this.nibble;

            if(this.nibble <= 0) {
                this.timeUntilLured = 0;
                this.timeUntilHooked = 0;
                this.getEntityData().set(DATA_BITING, false);
            }
        } else if(this.timeUntilHooked > 0) {
            this.timeUntilHooked -= fishingSpeed;

            if(this.timeUntilHooked > 0) {
                this.fishAngle += (float)(this.random.nextGaussian() * 4.0D);
                float fishAngle = this.fishAngle * ((float)Math.PI / 180F);
                float fishOffsetX = Mth.sin(fishAngle);
                float fishOffsetZ = Mth.cos(fishAngle);
                double fishX = this.getX() + fishOffsetX * this.timeUntilHooked * 0.1F;
                double fishY = Mth.floor(this.getY()) + 1.0F;
                double fishZ = this.getZ() + fishOffsetZ * this.timeUntilHooked * 0.1F;
                //Check block state by own status
                if(serverlevel.getBlockState(new BlockPos(fishX, fishY - 1, fishZ)).getMaterial() == material) {
                    //Add bubble particles
                    if(this.random.nextFloat() < 0.15F) {
                        serverlevel.sendParticles(bubbleParticle, fishX, fishY - 0.1F, fishZ, 1, fishOffsetX, 0.1D, fishOffsetZ, 0.0D);
                    }
                    //Add fishing particles
                    float fishParticleOffsetX = fishOffsetX * 0.04F;
                    float fishParticleOffsetZ = fishOffsetZ * 0.04F;
                    serverlevel.sendParticles(fishingParticle, fishX, fishY, fishZ, 0, fishParticleOffsetZ, 0.01D, -fishParticleOffsetX, 1.0D);
                    serverlevel.sendParticles(fishingParticle, fishX, fishY, fishZ, 0, -fishParticleOffsetZ, 0.01D, fishParticleOffsetX, 1.0D);
                }
            } else {
                this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                double particleY = this.getY() + 0.5D;
                //Add bubble particles
                serverlevel.sendParticles(bubbleParticle, this.getX(), particleY, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
                //Add fishing particles
                serverlevel.sendParticles(fishingParticle, this.getX(), particleY, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
                this.nibble = Mth.nextInt(this.random, 20, 40);
                this.getEntityData().set(DATA_BITING, true);
            }
        } else if(this.timeUntilLured > 0) {
            this.timeUntilLured -= fishingSpeed;
            float lureChance = 0.15F;

            if(this.timeUntilLured < 20) {
                lureChance += 20 - this.timeUntilLured * 0.05F;
            } else if(this.timeUntilLured < 40) {
                lureChance += 40 - this.timeUntilLured * 0.02F;
            } else if(this.timeUntilLured < 60) {
                lureChance += 60 - this.timeUntilLured * 0.01F;
            }

            if(this.random.nextFloat() < lureChance) {
                float fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F) * ((float)Math.PI / 180F);
                float fishDistance = Mth.nextFloat(this.random, 25.0F, 60.0F);
                double fishX = this.getX() + (Mth.sin(fishAngle) * fishDistance) * 0.1D;
                double fishY = Mth.floor(this.getY()) + 1.0F;
                double fishZ = this.getZ() + (Mth.cos(fishAngle) * fishDistance) * 0.1D;
                if(serverlevel.getBlockState(new BlockPos(fishX, fishY - 1.0D, fishZ)).getMaterial() == material) {
                    serverlevel.sendParticles(splashParticle, fishX, fishY, fishZ, 2 + this.random.nextInt(2), 0.1F, 0.0D, 0.1F, 0.0D);
                }
            }

            if(this.timeUntilLured <= 0) {
                this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
                this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
            }
        } else {
            this.timeUntilLured = Mth.nextInt(this.random, 100, 600);
            this.timeUntilLured -= this.lureSpeed * 20 * 5;
        }
    }

    @Override
    public FishingHook.OpenWaterType getOpenWaterTypeForBlock(BlockPos pos) {
        BlockState blockstate = this.level.getBlockState(pos);
        if(!blockstate.isAir() && !blockstate.is(Blocks.LILY_PAD)) {
            FluidState fluidstate = blockstate.getFluidState();
            return fluidstate.isSource() && blockstate.getCollisionShape(this.level, pos).isEmpty() ? FishingHook.OpenWaterType.INSIDE_WATER : FishingHook.OpenWaterType.INVALID;
        } else {
            return FishingHook.OpenWaterType.ABOVE_WATER;
        }
    }

    @Override
    public int retrieve(ItemStack fishingRod) {
        Player player = this.getPlayerOwner();
        if (!this.level.isClientSide && player != null && !this.shouldStopFishing(player)) {
            int rodDamage = 0;
            ItemFishedEvent event = null;

            if (this.hookedIn != null) {
                this.pullEntity(this.hookedIn);
                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, fishingRod, this, Collections.emptyList());
                this.level.broadcastEntityEvent(this, (byte)31);
                rodDamage = this.hookedIn instanceof ItemEntity ? 3 : 5;
            } else if (this.nibble > 0) {
                LootContext.Builder builder = (new LootContext.Builder((ServerLevel)this.level)).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.TOOL, fishingRod).withParameter(LootContextParams.THIS_ENTITY, this).withRandom(this.random).withLuck((float)this.luck + player.getLuck());
                builder.withParameter(LootContextParams.KILLER_ENTITY, player).withParameter(LootContextParams.THIS_ENTITY, this);
                LootTable lootTable = this.level.getServer().getLootTables().get(this.inLava ? BuiltInLootTables.FISHING : LOOT_TABLE_FISHING_LAVA);
                List<ItemStack> loots = lootTable.getRandomItems(builder.create(LootContextParamSets.FISHING));
                event = new ItemFishedEvent(loots, this.onGround ? 2 : 1, this);
                MinecraftForge.EVENT_BUS.post(event);

                if (event.isCanceled()) {
                    this.discard();
                    return event.getRodDamage();
                }

                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, fishingRod, this, loots);

                for(ItemStack itemstack : loots) {
                    //In case of lava damage, make a protected item entity for common item entities
                    ItemEntity itementity = (this.inLava && !itemstack.getItem().hasCustomEntity(itemstack)) ?
                        new ProtectedItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemstack) :
                        new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemstack);
                    double disX = player.getX() - this.getX();
                    double disY = player.getY() - this.getY();
                    double disZ = player.getZ() - this.getZ();
                    double vScale = 0.1D;
                    itementity.setDeltaMovement(disX * vScale, disY * vScale + Math.sqrt(Math.sqrt(disX * disX + disY * disY + disZ * disZ)) * 0.08D, disZ * vScale);
                    this.level.addFreshEntity(itementity);
                    player.level.addFreshEntity(new ExperienceOrb(player.level, player.getX(), player.getY() + 0.5D, player.getZ() + 0.5D, this.random.nextInt(this.inLava ? 9 : 6) + 1));
                    if (itemstack.is(ItemTags.FISHES)) {
                        player.awardStat(Stats.FISH_CAUGHT, 1);
                    }
                }

                rodDamage = 1;
            }

            if (this.onGround) {
                rodDamage = 2;
            }

            this.discard();
            return event == null ? rodDamage : event.getRodDamage();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isOnFire() {
        return !this.getFireVariant().isAir() && !this.isInWaterRainOrBubble();
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);
        Entity entity = hit.getEntity();
        if(!entity.fireImmune()) {
            ((FireVariantHoldingEntity)entity).setFireVariant(this.getFireVariant(), false);
            entity.setSecondsOnFire(8);
        }
    }

    public static class Blaze extends IntegratedFishingHook{

        public Blaze(EntityType<? extends Blaze> type, Level level, int luck, int lureSpeed) {
            super(type, level, luck, lureSpeed);
        }

        public Blaze(EntityType<? extends Blaze> type, Level level) {
            super(type, level);
        }

        public Blaze(Player player, Level level, int luck, int lureSpeed) {
            super(BrEntityTypes.BLAZE_FISHING_HOOK.get(), player, level, luck, lureSpeed);
        }

        @Override
        public FireVariant getFireVariant() {
            return FireVariant.FIRE.get();
        }

        @Override
        public void setFireVariant(FireVariant fireVariant, boolean force) {
            //NO-OP
        }

    }

    public static class SoulBlaze extends IntegratedFishingHook{

        public SoulBlaze(EntityType<? extends SoulBlaze> type, Level level, int luck, int lureSpeed) {
            super(type, level, luck, lureSpeed);
        }

        public SoulBlaze(EntityType<? extends SoulBlaze> type, Level level) {
            super(type, level);
        }

        public SoulBlaze(Player player, Level level, int luck, int lureSpeed) {
            super(BrEntityTypes.SOUL_BLAZE_FISHING_HOOK.get(), player, level, luck, lureSpeed);
        }

        @Override
        public FireVariant getFireVariant() {
            return FireVariant.SOUL_FIRE.get();
        }

        @Override
        public void setFireVariant(FireVariant fireVariant, boolean force) {
            //NO-OP
        }

    }

}