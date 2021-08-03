package mod.omoflop.omonals.lib.omob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public abstract class TamableOmobEntity extends TameableEntity {
    private static HashMap<String, TrackedData> ENTITY_DATA = new HashMap<>();

    protected TamableOmobEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    private static Class getStaticClass() {
        return new Object() { }.getClass().getEnclosingClass();
    }
    protected static <T> TrackedData<T> trackData(String nbtID, TrackedDataHandler<T> type) {
        if (!ENTITY_DATA.containsKey(nbtID))
            ENTITY_DATA.put(nbtID, DataTracker.registerData(getStaticClass(), type));
        return ENTITY_DATA.get(nbtID);
    }
    public <T> T getData(String nbtID) { return (T) this.dataTracker.get(ENTITY_DATA.get(nbtID)); }
    public <T> void setData(String nbtID, T value) { this.dataTracker.set(ENTITY_DATA.get(nbtID), value); }
}
