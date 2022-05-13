package limonblaze.blazereborn.common.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;

public abstract class AbstractBlazeAttackGoal<B extends Blaze> extends Blaze.BlazeAttackGoal {
    protected final B blaze;

    public AbstractBlazeAttackGoal(B blaze) {
        super(blaze);
        this.blaze = blaze;
    }

    public void tick() {
        --this.attackTime;
        LivingEntity livingentity = this.blaze.getTarget();
        if (livingentity != null) {
            boolean flag = this.blaze.getSensing().hasLineOfSight(livingentity);
            if (flag) {
                this.lastSeen = 0;
            } else {
                ++this.lastSeen;
            }

            double d0 = this.blaze.distanceToSqr(livingentity);
            if (d0 < 4.0D) {
                if (!flag) {
                    return;
                }

                if (this.attackTime <= 0) {
                    this.attackTime = 20;
                    this.blaze.doHurtTarget(livingentity);
                }

                this.blaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
            } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                double d1 = livingentity.getX() - this.blaze.getX();
                double d2 = livingentity.getY(0.5D) - this.blaze.getY(0.5D);
                double d3 = livingentity.getZ() - this.blaze.getZ();
                if (this.attackTime <= 0) {
                    ++this.attackStep;
                    if (this.attackStep == 1) {
                        this.attackTime = 60;
                        this.blaze.setCharged(true);
                    } else if (this.attackStep <= 4) {
                        this.attackTime = 6;
                    } else {
                        this.attackTime = 100;
                        this.attackStep = 0;
                        this.blaze.setCharged(false);
                    }

                    if (this.attackStep > 1) {
                        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
                        if (!this.blaze.isSilent()) {
                            this.blaze.level.levelEvent(null, 1018, this.blaze.blockPosition(), 0);
                        }
                        this.launchAttack(this.blaze.level, this.blaze, d1 + this.blaze.getRandom().nextGaussian() * d4, d2, d3 + this.blaze.getRandom().nextGaussian() * d4);
                    }
                }

                this.blaze.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
            } else if (this.lastSeen < 5) {
                this.blaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
            }
            super.tick();
        }
    }

    protected abstract void launchAttack(Level level, B blaze, double offsetX, double offsetY, double offsetZ);

}
