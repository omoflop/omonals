package mod.omoflop.omonals.entity;

import mod.omoflop.omonals.Omonals;
import mod.omoflop.omonals.util.EventBooleanSupplier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HamterEntity extends TameableEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public HamterEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        setVariant(world.getRandom().nextInt(6));
    }

    @Override public AnimationFactory getFactory() { return this.factory; }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(
          new AnimationController(this, "walk_controller", 8,
                  EventBooleanSupplier.predicateOf((e) -> e.isMoving(), "animation.hamter.walk", true)
              )
        );
    }

    public static DefaultAttributeContainer.Builder createEntityAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.1f);
    }

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(HamterEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public int getVariant() { return this.dataTracker.get(VARIANT); }
    public void setVariant(int variant) { this.dataTracker.set(VARIANT, variant); }



    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        HamterEntity hamter = Omonals.HAMTER.create(world);
        if (entity instanceof HamterEntity) {
            if (this.isTamed()) {
                hamter.setOwnerUuid(this.getOwnerUuid());
                hamter.setTamed(true);
            }
        }
        return hamter;
    }


}
