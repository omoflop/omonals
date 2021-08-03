package mod.omoflop.omonals;

import mod.omoflop.omonals.render.HamterRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class OmonalsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Omonals.HAMTER, ctx -> new HamterRenderer(ctx));
    }
}
