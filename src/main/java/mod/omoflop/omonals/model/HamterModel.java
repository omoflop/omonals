package mod.omoflop.omonals.model;

import mod.omoflop.omonals.entity.HamterEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class HamterModel extends AnimatedGeoModel<HamterEntity> {

    public static final Identifier MODEL_LOCATION = new Identifier("omonals:geo/hamter.geo.json");

    @Override
    public Identifier getModelLocation(HamterEntity fennecEntity) {
        return MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureLocation(HamterEntity hamterEntity) {
        switch(hamterEntity.getVariant()) {
            case 1:  return new Identifier("omonals:textures/entity/hamter/albino.png");
            case 2:  return new Identifier("omonals:textures/entity/hamter/blue.png");
            case 3:  return new Identifier("omonals:textures/entity/hamter/blue_fawn.png");
            case 4:  return new Identifier("omonals:textures/entity/hamter/panda.png");
            case 5:  return new Identifier("omonals:textures/entity/hamter/coki.png");
            default: return new Identifier("omonals:textures/entity/hamter/hamter.png");
        }
    }

    @Override
    public Identifier getAnimationFileLocation(HamterEntity fennecEntity) {
        return new Identifier("omonals:animations/hamter.animation.json");
    }

    @Override
    public void setLivingAnimations(HamterEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("Head");
        EntityModelData extra = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX((float) Math.toRadians(extra.headPitch + (entity.isInSittingPose() ? -27.5f : 0)));
            head.setRotationY((float) Math.toRadians(extra.netHeadYaw));
        }

        if (extra.isChild) {
            head.setScaleX(2.0f);
            head.setScaleY(2.0f);
            head.setScaleZ(2.0f);
        } else {
            head.setScaleX(1.0f);
            head.setScaleY(1.0f);
            head.setScaleZ(1.0f);
        }
    }
}
