package mod.omoflop.omonals.lib.omob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

public abstract class OmobEntity extends MobEntity {
    public OmobEntity(EntityType<? extends MobEntity> entityType, World world) { super(entityType, world); }
    private OmobEntity(World world) { super(null, world); } // <- why

    private static HashMap<String,DataEntry> ENTITY_DATA = new HashMap<>();
    private static Class getStaticClass() {
        return new Object() { }.getClass().getEnclosingClass();
    }
    protected static <T> TrackedData<T> trackData(String nbtID, TrackedDataHandler<T> type, T defaultValue) {
        if (!ENTITY_DATA.containsKey(nbtID))
            ENTITY_DATA.put(nbtID, new DataEntry(DataTracker.registerData(getStaticClass(), type), defaultValue));
        return ENTITY_DATA.get(nbtID).data();
    }
    public <T> T getData(String nbtID) { return (T) this.dataTracker.get(ENTITY_DATA.get(nbtID).data()); }
    public <T> void setData(String nbtID, T value) { this.dataTracker.set(ENTITY_DATA.get(nbtID).data(), value); }
    public <T> T getDefaultData(String nbtID) {
        DataEntry<T> entry = ENTITY_DATA.get(nbtID);
        return entry.defaultValue();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        ENTITY_DATA.forEach((nbtID, entry) -> {
            this.dataTracker.startTracking(entry.data(), entry.defaultValue());
        });
    }

    @Override public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        ENTITY_DATA.forEach((nbtID, entry) -> {
            BiConsumer<String, Number> c = nbt::putInt;

            if (entry.defaultValue() instanceof Integer) {
                nbt.putInt(nbtID, getData(nbtID));
            }
        });

    }
    @Override public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Variant"))
            setVariant(nbt.getInt("Variant"));
    }

    private record DataEntry<T>(TrackedData data, T defaultValue) {}
    private interface NBTPutFunction<T> {
        void put(String nbtID, T value);
    }
}
