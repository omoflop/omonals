package mod.omoflop.omonals;

import mod.omoflop.omonals.entity.HamterEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import software.bernie.geckolib3.GeckoLib;

public class Omonals implements ModInitializer {

	public static final EntityType<HamterEntity> HAMTER = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("omonals:hamter"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE,
					HamterEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.3f)).build()
	);

	public static final Item HAMTER_SPAWN_EGG = new SpawnEggItem(HAMTER, 0xb29476, 0xf1ebdb, new Item.Settings().group(ItemGroup.MISC));


	@Override
	public void onInitialize() {
		GeckoLib.initialize();

		BiomeModifications.addSpawn(
				selection -> selection.getBiome().getCategory() == Biome.Category.DESERT,
				SpawnGroup.CREATURE,
				HAMTER,
				3, 1, 3
		);

		FabricDefaultAttributeRegistry.register(HAMTER, HamterEntity.createEntityAttributes());
		Registry.register(Registry.ITEM, new Identifier("omonals:hamter_spawn_egg"), HAMTER_SPAWN_EGG);
	}
}
