package mod.omoflop.omonals.util;

import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import javax.sound.midi.ShortMessage;

public interface EventBooleanSupplier {

    boolean get(AnimationEvent event);
    
    static AnimationController.IAnimationPredicate predicateOf(EventBooleanSupplier condition, String name, boolean shouldLoop) {
        return animationEvent -> {
            boolean b = condition.get(animationEvent);
            if (b) animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation(name, shouldLoop));
            return b ? PlayState.CONTINUE : PlayState.STOP;
        };
    }
}
